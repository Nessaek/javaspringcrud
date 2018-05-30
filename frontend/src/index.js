import React from 'react';
import ReactDOM from 'react-dom';
import store from "./store/index";
import { getQuote } from "./actions/index";
import './index.css';
import App from './App';
import Home from './pages/home'
import registerServiceWorker from './registerServiceWorker';
import { Provider } from "react-redux";

ReactDOM.render(
    <Provider store={store}>
        <Home />
    </Provider>
    , document.getElementById('root'));
registerServiceWorker();