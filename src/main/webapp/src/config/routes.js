import React from "react";
import {hashHistory, IndexRoute, Route, Router} from "react-router";
import {applyMiddleware, createStore} from "redux";
import {Provider} from "react-redux";

import MainLayout from "../MainLayout";
import Home from "../Home";
import {Projects} from "../components/Projects";
import About from "../components/About";
import Users from "../components/Users";
import store from "../config/store"

const routes = (
    <Provider store={store}>
        <Router history={hashHistory}>
            <Route path="/" component={MainLayout}>
                <IndexRoute component={Home}/>
                <Route path="projects">
                    <IndexRoute component={Projects}/>
                    <Route path="project/:id" component={Projects}/>
                </Route>
                <Route path="users" component={Users}/>
                <Route path="about" component={About}/>
            </Route>
        </Router>
    </Provider>
);
console.log("Initial store");
console.log(store);
module.exports = routes;
