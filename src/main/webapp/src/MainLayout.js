import React from "react";

import Menu from "./components/Menu.js";

import MuiThemeProvider from "material-ui/styles/MuiThemeProvider";
import {AppBar, FlatButton} from "material-ui";
import getMuiTheme from "material-ui/styles/getMuiTheme";
import baseTheme from "material-ui/styles/baseThemes/lightBaseTheme";

function handleTouchTap() {
    // alert('onTouchTap triggered on the title component');
}

const Header = React.createClass({
    render(){
        console.log("Header");
        console.log(this);
        return (
            <MuiThemeProvider muiTheme={getMuiTheme(baseTheme)}>

                <div>
                    <AppBar
                        title={<span>ScrumBoard</span>}
                        iconElementLeft={<Menu/>}
                        iconElementRight={<FlatButton label="Fuck5" />}
                    />
                    <div>{this.props.children}</div>
                </div>
            </MuiThemeProvider>
        )
    }
});

module.exports = Header;

