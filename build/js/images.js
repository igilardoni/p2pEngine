/**
 * 
 */
function previewFile() {
	var preview = document.querySelector("#itemForm #image");
	var file    = document.querySelector("#fileDiv input[type=file]").files[0];
	var reader  = new FileReader();

	reader.onloadend = function () {
		preview.src = reader.result;
	}

	if (file) {
		reader.readAsDataURL(file);
	} else {
		preview.src = "";
	}
}

function typeImageChanged(){
	if($("#paraImage input[type=radio]:checked").val()=="File"){
		$("#fileDiv").show();
		$("#webcamDiv").hide();
		previewFile();
	} else if($("#paraImage input[type=radio]:checked").val()=="Webcam") {
		$("#fileDiv").hide();
		$("#webcamDiv").show();
		$("#paraImage img").attr("src", "");
		startWebcam();
	}
}

function startWebcam() {

	var streaming = false,
	video        = document.querySelector('#itemForm #video'),
	cover        = document.querySelector('#itemForm #cover'),
	canvas       = document.querySelector('#itemForm #canvas'),
	photo        = document.querySelector('#itemForm #image'),
	startbutton  = document.querySelector('#itemForm #startbutton'),
	stopbutton	 = document.querySelector('#itemForm #stopbutton'),
	width = 320,
	height = 0;

	navigator.getMedia = ( navigator.getUserMedia ||
			navigator.webkitGetUserMedia ||
			navigator.mozGetUserMedia ||
			navigator.msGetUserMedia);

	navigator.getMedia(
			{
				video: true,
				audio: false
			},
			function(stream) {
				if (navigator.mozGetUserMedia) {
					video.mozSrcObject = stream;
				} else {
					var vendorURL = window.URL || window.webkitURL;
					video.src = vendorURL.createObjectURL(stream);
				}
				video.play();
			},
			function(err) {
				console.log("An error occured! " + err);
			}
	);

	video.addEventListener('canplay', function(ev){
		if (!streaming) {
			height = video.videoHeight / (video.videoWidth/width);
			video.setAttribute('width', width);
			video.setAttribute('height', height);
			canvas.setAttribute('width', width);
			canvas.setAttribute('height', height);
			streaming = true;
		}
	}, false);

	function takepicture() {
		canvas.width = width;
		canvas.height = height;
		canvas.getContext('2d').drawImage(video, 0, 0, width, height);
		var data = canvas.toDataURL('image/png');
		photo.setAttribute('src', data);
		$(video).addClass("hidden");
		$(startbutton).addClass("hidden");
		$(stopbutton).removeClass("hidden");
	}
	
	function resetpicture() {
		$(video).removeClass("hidden");
		$(startbutton).removeClass("hidden");
		$(stopbutton).addClass("hidden");
		$(photo).attr('src', "");
	}

	startbutton.addEventListener('click', function(ev){
		takepicture();
		ev.preventDefault();
	}, false);
	stopbutton.addEventListener('click', function(ev){
		resetpicture();
	})

}