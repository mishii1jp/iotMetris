/** ゲーム開始か否か判定 */
var initFlg = false;
/* 以前の操作時間 */
var previousTime;

//TODO Websocket用URL
var connection = io.connect('ws://10.10.10.71:3000');//new WebSocket('ws://10.10.10.71:3000');
connection.on("connected", alert('connection!!!'));
//connection.onopen = function(event) {
//    alert('hogehoge_Open!');
//}

Message受信
connection.onmessage = function(event) {
    /* 受信データ */
    var msg = json.parase(event.data);
    /* ゲーム開始かどうか */
    if (!initFlg) {
        /* ゲーム開始 */
        initFlg = true;
        /* ３秒後にスタート */
        setInterval(startGame(), 3000);
    } else {
        /* ゲーム開始直後 */
        if (previousTime == null) {
            /* 初回の場合はsetTimeoutちっくに。 */
            prevousTime = Date.parse(new Date());
            controllKey(msg);
        } else {
            /* 以前の操作から100ms経過している場合 */
            if (Date.parse(new Date()) - previousTime > 100) {
                controllKey(msg);
                previousTime = Date.parse(new Date());
            } else {
                //何もしない
            }
        }
    }
}
/** keydownイベント発火 */
function memeToKeydown(keycode) {
    var evt = $.Event('keydown');
    evt.keyCode = keycode;
    $('document').trigger(evt);
}

/** ゲームスタート */
function startGame() {
    $('#tetris-play').blockrain('start');
}

function controllKey(msg) {
    var keycode = '',
        accX = '',  //左右方向
        accY = '',  //前後方向
        accZ = '';  //上下方向
    /* 送られてきたデータを読み取る */
    accX = msg.accX;
    accY = msg.accY;
    accZ = msg.accZ;
    if (accX > 0) {         //右移動（１移動）
        keycode = '39';
    } else if (accX < 0) {  //左移動（１移動）
        keycode = '37';
    } else if (accY > 0) {  //下移動（１移動）
        keycode = '40';
    } else if (accY < 0) {  //回転（右回り？）
        keycode = '38';
    } else {
        keycode = '';
    }
    //keydownイベント発火
    memeToKeydown(keycode);
}
