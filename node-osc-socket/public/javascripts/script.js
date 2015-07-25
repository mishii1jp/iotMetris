$(function(){

	var host = $('.host input').val();

	$('.role input[name=radio]').on('change',function(){
		var role = $(this).val();
		if(role === 'server'){
			$('body').attr('class','server');
			var port = $('.port input').val();
			$.ajax({
				url: '/api/set/server/',
				type: 'POST',
				data: { host:host, port:port }
			})
			.done(function(res) {
				console.log(res);
			});
		} else {
			$('body').attr('class','client');
			var port = $('.port input').val();
			$.ajax({
				url: '/api/set/client/',
				type: 'POST',
				data: { host:host, port:port }
			})
			.done(function(res) {
				console.log(res);
			});
		}
	});

	$('.client .submit input').on('click',function(){
		var address = $('.client .address input').val();
		var value = $('.client .value input').val();
		$.ajax({
			url: '/api/client/send',
			type: 'POST',
			data: { address:address, value:value }
		})
		.done(function(res) {
			console.log(res);
		});
	});

	var webSocket = null;
	var sio = io.connect('http://'+host+':3000');
	sio.on("connected", function(ip) {
		console.log("connected");
		$(window).mousemove(function(e){
			sio.emit("mouseMove", { x:e.clientX, y:e.clientY } );
		});
	});
	sio.on("receive", function (message) {
		mes = JSON.stringify(message);
		$('.server .message .value').html(mes);
	});
	sio.on("mouseMove", function (message) {
		mes = JSON.stringify(message);
		$('.server .message .value').html(mes);
	});



}) ;
