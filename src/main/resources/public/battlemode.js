var webSocket = new WebSocket("ws://" + location.hostname + ":" + location.port + "/battlesocket");
webSocket.onopen = function () { showMessage("Buscando oponentes..."); console.log('conectado'); };
webSocket.onmessage = function (msg) { update(msg); };
webSocket.onclose = function () { alert("WebSocket connection closed") };

var	timeLeft;
var	timerId;
var instance = 1;


function clean(){
	id("question").innerHTML="";
	id("options").innerHTML="";
	id("timer").innerHTML="";
}

function showMessage(msg){
	clean();
	id("question").innerHTML=msg;
}

function makeQuestion(data){
	clearTimeout(timerId);
	id("question").innerHTML=data.question;
	id("options").innerHTML+="<input class=\"button\" type=\"submit\""+
	"value='"+data.option1+"' id=\"1\" onclick=\"getAnswer(this.id)\">"
	id("options").innerHTML+="<input class=\"button\" type=\"submit\""+
	"value='"+data.option2+"' id=\"2\" onclick=\"getAnswer(this.id)\">"
	id("options").innerHTML+="<input class=\"button\" type=\"submit\""+
	"value='"+data.option3+"' id=\"3\" onclick=\"getAnswer(this.id)\">"
	id("options").innerHTML+="<input class=\"button\" type=\"submit\""+
	"value='"+data.option4+"' id=\"4\" onclick=\"getAnswer(this.id)\">"
	timeLeft = 10;
	timerId = setInterval(countdown, 1000);
	instance++;
}

function getAnswer(answer){
	clearTimeout(timerId);
	webSocket.send("ans"+answer);
}

function showResult(data){
	showMessage(data.result);
	console.log(data.result);
}

function showEnd(data){
	showMessage(data.result);
	console.log(data.result);
}



function update(msg){
	showMessage("Cargando...");
	var data = JSON.parse(msg.data);
	if(data.type == "1"){
		makeQuestion(data);
		console.log('-+-+-+ Preguntando -+-+-+');
	}
	else if(data.type == "2"){
		showResult(data);
		setTimeout( function (){webSocket.send("qst0");},1500);
	}else if(data.type == "3"){
		showEnd(data);
		setTimeout( function (){alert("Deberias irte")},3000);
	}else{
		console.log('Tipo no identificado');
	}

}

function countdown() {
  if (timeLeft == 0) {
    getAnswer("5");
  } else {
    id("timer").innerHTML = timeLeft + 's Restantes';
    timeLeft--;
  }
}

//Helper function for selecting element by id
function id(id) {
    return document.getElementById(id);
}