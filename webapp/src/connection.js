/* URL */
var uri = 'ws://10.10.10.71:3000';
/** ゲーム開始か否か判定 */
var initFlg = false;
/* 以前の操作時間 */
var previousTime;
/* bgm */
var bgm = new Audio();

//初期のBGM再生
bgm.loop = true;
bgm.src = "./audio/sht_a05.mp3";
bgm.play();

//Websocket用URL
var connection = io.connect(uri);
/* 接続時 */
connection.on("connected", function(msg) {
    console.log('connected!:' + JSON.stringify(msg));
});
/* Message受信 */
connection.on("receive", function (msg) {
    var parseMsg = JSON.parse(JSON.stringify(msg));
    console.log('message:' + parseMsg);
    receivedData(parseMsg);
    if (game._board.gameover) {
        disconnectSocket();
    }
});
/** 切断 */
function disconnectSocket() {
    console.log('切断');
    connection.disconnect();
}


/* 稼働 */
function receivedData(msg) {
    /* ゲーム開始かどうか */
    if (!initFlg) {
        /* ゲーム開始 */
        initFlg = true;
        bgm.pause();
        bgm.src="./audio/go.mp3";
        bgm.play();
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
            if (Date.parse(new Date()) - previousTime > 1000) {
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
    console.log(evt);
    evt.keyCode = keycode;

}

/** ゲームスタート */
function startGame() {
    bgm.pause();
    bgm.src = "./audio/sht_a02.mp3";
    bgm.play();
    $('#tetris-play').blockrain('start');
}

function controllKey(msg) {
    var keycode = '',
        accX = '',  //左右方向
        accY = '',  //前後方向
        accZ = '',  //上下方向
        roll = '',  //ロール要素
        pitch = '', //ピッチ要素
        yaw = '';   //ヨー要素
    /* 送られてきたデータを読み取る */
    accX = msg.accX;
    accY = msg.accY;
    accZ = msg.accZ;
    roll = msg.roll;
    pitch = msg.pitch;
    yaw = msg.yaw;
    /* 値の出力 */
    console.log(accX + ',' + accY + ',' + accZ + ',' + roll + ',' + pitch + ',' + yaw);
    if (accY > 0) {
        keycode = '39';
        game._board.cur.moveRight();
    } else {
        keycode = '37';
        game._board.cur.moveLeft();
    }
    //TODO 修正！！！
//    accX = msg.accX;
//    accY = msg.accY;
//    accZ = msg.accZ;
//    if (accX > 0) {         //右移動（１移動）
//        keycode = '39';
//    } else if (accX < 0) {  //左移動（１移動）
//        keycode = '37';
//    } else if (accY > 0) {  //下移動（１移動）
//        keycode = '40';
//    } else if (accY < 0) {  //回転（右回り？）
//        keycode = '38';
//    } else {
//        keycode = '';
//    }
    //keydownイベント発火
//    memeToKeydown(keycode);
}
