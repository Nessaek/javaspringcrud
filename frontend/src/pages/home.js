import React, { Component } from 'react';
import { bindActionCreators } from 'redux';

import { connect } from "react-redux";

import * as thunks from "../actions/index";

var theStream;
var theRecorder;
var recordedChunks = [];


const constraints = { "video": { width: { max: 320 } }, "audio": true };

function mapStateToProps(state) {
    return {
        video: state.video
    }
}

function mapDispatchToProps(dispatch) {
    return {
        actions: bindActionCreators(thunks, dispatch)
    };
}


class ConnectedHome extends Component {
    constructor() {
        super();
        this.state = {
            video: "1"
        }
        this.startFunction = this.startFunction.bind(this);
        this.download = this.download.bind(this);
    }


    startFunction() {
        navigator.mediaDevices.getUserMedia(constraints)
            .then(this.gotMedia)
            .catch(e => { console.error('getUserMedia() failed: ' + e); });
    }

    gotMedia(stream) {
        var recorder;
        theStream = stream;
        var video = document.querySelector('video');
        video.src = URL.createObjectURL(stream);
        try {
            recorder = new MediaRecorder(stream, { mimeType: "video/webm" });
        } catch (e) {
            console.error('Exception while creating MediaRecorder: ' + e);
            return;
        }

        theRecorder = recorder;
        recorder.ondataavailable =
            (event) => { recordedChunks.push(event.data); };
        recorder.start(100);
    }



    download() {
        // this.state.theRecorder.stop();
        // this.state.theStream.getTracks().forEach(track => { track.stop(); });
        var blob = new Blob(recordedChunks, { type: "video/webm" });
        var url = URL.createObjectURL(blob);

        const { actions } = this.props
        var reader = new FileReader();
        var base64data;
        reader.readAsDataURL(blob);
        reader.onloadend = function() {
            base64data = reader.result;
            actions.downloadBlob(base64data);
        }



        // var a = document.createElement("a");
        // document.body.appendChild(a);
        // a.style = "display: none";
        // a.href = url;
        // a.download = 'test.webm';
        // a.click();
        // setTimeout() here is needed for Firefox.
        // setTimeout(function () { URL.revokeObjectURL(url); }, 100);
    }



    render() {
        return (
            <div>
                <p><video id="video" autoPlay width="320" /></p>
                <p><button onClick={() => this.startFunction()}>Grab video & start recording</button></p>
                <p><button onClick={() => this.download()}>Download! (and stop video)</button></p>
            </div>
        );
    }
}

const Home = connect(mapStateToProps, mapDispatchToProps)(ConnectedHome);

export default Home