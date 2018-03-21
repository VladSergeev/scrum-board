import React from "react";
import {IconButton, IconMenu, MenuItem} from "material-ui";
import {Link} from "react-router";
import NavigationMenu from "material-ui/svg-icons/navigation/menu";

const Menu = (props) => (
    <IconMenu
        {...props}
        iconButtonElement={
            <IconButton><NavigationMenu /></IconButton>
        }
        targetOrigin={{horizontal: 'left', vertical: 'top'}}
        anchorOrigin={{horizontal: 'left', vertical: 'top'}}
    >
        <MenuItem primaryText="Home" containerElement={<Link to="/"/>}/>
        <MenuItem primaryText="Users" containerElement={<Link to="/users"/>}/>
        <MenuItem primaryText="Projects" containerElement={<Link to="/projects"/>}/>
        <MenuItem primaryText="About" containerElement={<Link to="/about"/>}/>
    </IconMenu>
);

module.exports = Menu;
