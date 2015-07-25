var express = require('express');
var router = express.Router();

var app = require('../app');
var osc = require('node-osc');
var oscclient;
var oscserver;

/* GET home page. */
router.get('/', function(req, res, next) {
    res.contentType('application/json');
    res.send(JSON.stringify({}));
});

router.post('/set/client', function(req, res, next) {
    res.contentType('application/json');
    var host = req.body.host;
    var port = req.body.port;
    if(!host || !port) {
        res.send(JSON.stringify('query error'));
        return false;
    }
    if(!oscclient){
        oscclient = new osc.Client(host,port);
        res.send(JSON.stringify('oscserver will send at :' + port));
    } else {
        res.send(JSON.stringify('oscclient is already exist.'));
    }
});

router.post('/set/server', function(req, res, next) {
    res.contentType('application/json');
    var host = req.body.host;
    var port = req.body.port;
    if(!host || !port) {
        res.send(JSON.stringify('query error'));
        return false;
    }
    if(!oscserver){
        oscserver = new osc.Server(port, '0.0.0.0');
        oscserver.on("message", function (msg, rinfo) {
            var io = getio(); // app.js exports functin
            io.sockets.emit('receive', { address:msg[0], value:msg[1] });
        });
        res.send(JSON.stringify('oscserver is listen at :' + port));
    } else {
        res.send(JSON.stringify('oscserver is already exist.'));
    }
});

router.post('/client/send', function(req, res, next) {
    res.contentType('application/json');
    var address = req.body.address;
    var value = req.body.value;
    oscclient.send(address,value);
    res.send(JSON.stringify({ addresss:address, value:value }));
});

module.exports = router;
