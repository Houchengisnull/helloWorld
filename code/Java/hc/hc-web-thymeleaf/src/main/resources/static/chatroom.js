var stompClient = null;

//加载完浏览器后  调用connect（），打开双通道
$(function(){
    //打开双通道
    connect()
})

//强制关闭浏览器  调用websocket.close（）,进行正常关闭
window.onunload = function() {
    disconnect()
}

//打开双通道
function connect(){
    var socket = new SockJS('/endpointMark'); //连接SockJS的endpoint名称为"endpointAric"
    stompClient = Stomp.over(socket);//使用STMOP子协议的WebSocket客户端
    stompClient.connect({},function(frame){//连接WebSocket服务端

        console.log('Connected:' + frame);
        //广播接收信息
        stompTopic();

    });
}

//关闭双通道
function disconnect(){
    if(stompClient != null) {
        stompClient.disconnect();
    }
    console.log("Disconnected");
}

//一对多
function stompTopic(){
    //通过stompClient.subscribe订阅目标(destination)发送的消息（广播接收信息）
    stompClient.subscribe('/mass/getResponse',function(response){
        var message=JSON.parse(response.body);
        //展示广播的接收的内容接收
        var response = $("#chatRecord");
        response.append("<p><span>"+message.name+":</span><span>"
            +message.chatValue+"</span></p>");
    });
}

//群发
function sendMassMessage(){
    var postValue={};
    var chatValue=$("#sendChatValue");
    var userName=$("#selectName").val();
    postValue.name=userName;
    postValue.chatValue=chatValue.val();
    //postValue.userId="0";
    if(userName==1||userName==null){
        alert("请选择你是谁！");
        return;
    }
    if(chatValue==""||userName==null){
        alert("不能发送空消息！");
        return;
    }
    stompClient.send("/massRequest",{},JSON.stringify(postValue));
    chatValue.val("");
}

//单独发
function sendAloneMessage(){
    var postValue={};
    var chatValue=$("#sendChatValue2");
    var userName=$("#selectName").val();
    var sendToId=$("#selectName2").val();
    var response = $("#chatRecord2");
    postValue.name=userName;//发送者姓名
    postValue.chatValue=chatValue.val();//聊天内容
    postValue.userId=sendToId;//发送给谁
    if(userName==1||userName==null){
        alert("请选择你是谁！");
        return;
    }
    if(sendToId==1||sendToId==null){
        alert("请选择你要发给谁！");
        return;
    }
    if(chatValue==""||userName==null){
        alert("不能发送空消息！");
        return;
    }
    stompClient.send("/aloneRequest",{},JSON.stringify(postValue));
    response.append("<p><span>"+userName+":</span><span>"
        +chatValue.val()+"</span></p>");
    chatValue.val("");
}

//一对一
function stompQueue(){

    var userId=$("#selectName").val();
    alert("监听:"+userId)
    //通过stompClient.subscribe订阅目标(destination)发送的消息（队列接收信息）
    stompClient.subscribe('/queue/' + userId + '/alone',
        function(response){
        var message=JSON.parse(response.body);
        //展示一对一的接收的内容接收
        var response = $("#chatRecord2");
        response.append("<p><span>"+message.name+":</span><span>"
            +message.chatValue+"</span></p>");
    });
}





