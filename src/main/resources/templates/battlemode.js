var webSocket = new WebSocket("ws://" + location.hostname + ":" + location.port + "/battlesocket");
webSocket.onmessage = function (msg) { answer(msg); update(msg); };
webSocket.onclose = function () { alert("WebSocket connection closed") };

function clean(){
	id("question").innerHtml="";
	id("options").innerHtml="";
}

function wait(){
	clean();
	id.("question").innerHtml="Cargando...";
}

function update(msg){
	wait();
}

//Helper function for selecting element by id
function id(id) {
    return document.getElementById(id);
}