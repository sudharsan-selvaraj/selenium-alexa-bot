function init() {
   function () {
     var recognition = new webkitSpeechRecognition();
        // settings
        //recognition.continuous = false; // stop automatically
        recognition.interimResults = false;
        recognition.lang = "en-US";

        window.recognition = recognition;
   }
}

function listen() {
    function (callback) {
                window.recognition.start();
                window.recognition.onresult = function (event) {
                    window.recognition.stop();
                    console.log(event);
                    callback(event.results[0][0].transcript);
                }
    }
}

function speak() {
    function(text) {
        return new Promise(function (resolve, reject) {
                const msg = new SpeechSynthesisUtterance(),
                    defaultVoice = 'Microsoft David Desktop - English (United States)';

                msg.text = text;
                // msg.volume = 1; // 0 to 1
                // msg.rate = 1; // 0.1 to 10
                // msg.pitch = 1; //0 to 2
                // msg.lang = this.DEST_LANG;
                msg.voice = window.speechSynthesis.getVoices().find(voice => voice.name === defaultVoice)
                msg.onend = (e) =>  resolve()
                speechSynthesis.speak(msg);
            })
    }
}