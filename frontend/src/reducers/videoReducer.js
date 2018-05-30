import * as types from './../actions/actionTypes'

export function videoReducer(state = {}, action) {
    switch (action.type) {

        case types.DOWNLOAD_VIDEO_SUCCESS:
            return [...state, action.video];
        default:
            return state;
    }
};
