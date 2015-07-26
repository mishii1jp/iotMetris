function setViewStart(){
    $("body").attr("class","hero");
}
function setViewPlay(){
    $("body").attr("class","play");
}
$(function(){
    $(".welcome a").on("click",function(){
        setViewPlay();
    });
});
