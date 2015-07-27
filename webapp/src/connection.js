/* URL */
var uri = 'ws://10.10.10.141:3000';
/** ゲーム開始か否か判定 */
var initFlg = false;
/* 以前の操作時間 */
var previousTime;
/* bgm */
var startBgm = new Audio();
var readyBgm = new Audio();
var gameBgm = new Audio();
var gameOverBgm = new Audio();
/* 回転判定 */
var rotateFlg = false;
/* gameoveFlg */
var gameOverFlg = false;

//初期のBGM再生
startBgm.loop = true;
startBgm.src = "./audio/sht_a05.mp3";
startBgm.play();
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
    if (game._board.gameover && !gameOverFlg) {
        gameOverFlg = true;
        gameBgm.pause();
        gameBgm.src='';
        gameOverBgm.loop = false;
        gameOverBgm.src = './audio/failed.mp3';
        gameOverBgm.play();
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
        /* スタートBGMをクリア */
        startBgm.pause();
        startBgm.src='';
        /* Ready ... GO!!!! */
        readyBgm.src="./audio/go.mp3";
        readyBgm.play();
//        readyBgm.addEventListener('ended', function() {
//            startGame();
//            console.log('ended');
//        }, false);
//        readyBgm.addEventListener('canplaythrough', function() {
//            readyBgm.play();
//            console.log('canplaythrough');
//        }, false);
//        readyBgm.onended = function(){
//            startGame();
//        }

        /* ３秒後にスタート */
        setTimeout("startGame()", 3000);

    } else {
        /* ゲーム開始直後 */
        if (previousTime == null) {
            /* 初回の場合はsetTimeoutちっくに。 */
            prevousTime = Date.parse(new Date());
            controllKey(msg);
        } else {
            /* 以前の操作から100ms経過している場合 */
            if (Date.parse(new Date()) - previousTime > 500) {
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
        startBgm.pause();
        startBgm.src='';
        gameBgm.src = "./audio/sht_a02.mp3";
        gameBgm.loop = true;
        gameBgm.play();
        setViewPlay();
        $('#tetris-play').blockrain('start');

}

function controllKey(msg) {
    var keycode = '',
        accX = '',  //左右方向
        accY = '',  //前後方向
        accZ = '',  //上下方向
        roll = '',  //ロール要素
        pitch = '', //ピッチ要素
        yaw = '',   //ヨー要素
        eyeMoveUp = '',
        eyeMoveDown = '',
        eyeMoveLeft = '',
        eyeMoveRight = '',
        blinkSpeed = '',
        blinkStrength = '',
        walking = '';
    /* 送られてきたデータを読み取る */
    accX = msg.accX;
    accY = msg.accY;
    accZ = msg.accZ;
    roll = msg.roll;
    pitch = msg.pitch;
    yaw = msg.yaw;
    eyeMoveUp = msg.eyeMoveUp;
    eyeMoveDown = msg.eyeMoveDown;
    eyeMoveLeft = msg.eyeMoveLeft;
    eyeMoveRight = msg.eyeMoveRight;
    blinkSpeed = msg.blinkSpeed;
    blinkStrength = msg.blinkStrength;
    walking = msg.walking;
    /* 値の出力 */
    console.log(accX + ',' + accY + ',' + accZ + ',' + roll + ',' + pitch + ',' + yaw + ',' +
               eyeMoveUp + ',');
    if (walking) {  //回転
        if (rotateFlg) {
            //回転して初期化
            game._board.cur.rotate(true);
            rotateFlg = false;
        } else {
            //次の動きで回転
            rotateFlg = true;
        }
    }
    if (pitch > 600 && roll < 100) {  //左
        keycode = '39';
        game._board.cur.moveLeft();
        return;
    } else if ((pitch <100 && roll > 600) || (accY <30 && accY > 0)) {    //右
        keycode = '37';
        game._board.cur.moveRight();
        return;
    } else if (pitch < 10 && roll < 10) { //下
        game._board.dropCount = game._board.dropDelay;
        return;
    }
    if (game._board.gameover && eyeMoveUp > 0) {
        startGame();
        readyBgm.src="./audio/go.mp3";
        readyBgm.play();
        gameOverFlg = false;
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
