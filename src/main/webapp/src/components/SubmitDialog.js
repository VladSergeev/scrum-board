"use strict";

import React from "react";
import Dialog from "material-ui/Dialog";
import FlatButton from "material-ui/FlatButton";
import Paper from "material-ui/Paper";

export default class SubmitDialog extends React.Component {
    constructor(props) {
        super(props);

        this.state = {isDisabledSubmit: true, open: !this.props.isOpenDialog};
        this.handleOpen = () => {
            this.setState({open: true});
        };

        this.handleClose = this.props.handleClose;
        this.handleSubmit = this.props.handleSubmit;
    }

    initDefaults() {
        this.isOpenDialog = this.props.isOpenDialog ? this.props.isOpenDialog : false;
    }

    render() {
        this.initDefaults();

        const actions = [
            <FlatButton
                label={this.props.labelCloseButton}
                primary={true}
                onTouchTap={() => {
                    this.handleClose();
                }}
            />,
            <FlatButton
                label={this.props.labelSubmitButton}
                primary={true}
                disabled={this.isDisabledSubmit}
                onTouchTap={this.handleSubmit}
            />,
        ];

        return (
            <div>
                <Dialog
                    title={this.props.titleDialog}
                    actions={actions}
                    modal={true}
                    open={this.isOpenDialog}
                >

                    <Paper zDepth={2}>
                        {this.props.children}
                    </Paper>
                </Dialog>
            </div>
        );
    }

}