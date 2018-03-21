"use strict";

import React from "react";
import {render} from "react-dom";

import routes from "./config/routes.js"

require("./styles/style.less");
const injectTapEventPlugin = require("react-tap-event-plugin");
injectTapEventPlugin();


render(routes, document.getElementById('content'));
