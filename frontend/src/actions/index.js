//action creator
import { downloadVideo } from '../api/index'
import * as types from './actionTypes'


// export const getQuote = () =>
//     (
//         { type: "GET_QUOTE", loading: true })


export function dowloadVideoSuccess(videoSuccess) {
    return { type: types.DOWNLOAD_VIDEO_SUCCESS, videoSuccess };
}


export function downloadBlob(video) {
    // make async call to api, handle promise, dispatch action when promise is resolved
    return function (dispatch) {
        return downloadVideo(video).then(videoSuccess => {
            dispatch(dowloadVideoSuccess(videoSuccess));
        }).catch(error => {
            throw (error);
        });
    };
}

