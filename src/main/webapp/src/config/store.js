import { applyMiddleware, createStore } from "redux"
import appReducers from "../reducers/reducers";
import logger from "redux-logger"
import thunk from "redux-thunk"


const middleware = applyMiddleware(thunk, logger())

export default createStore(appReducers, middleware)