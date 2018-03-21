import React, {PropTypes} from "react";
import Snackbar from 'material-ui/Snackbar';

const DEFAULT_ERROR = 'Произошла непредвиденная ошибка!';

export default class Error extends React.Component {

    constructor(props) {
        super(props);
    }

    render() {

        let message;

        if (this.props.text &&
            this.props.text.trim().length > 0) {
            message = this.props.text;
        } else {
            message = DEFAULT_ERROR;
        }

        return (
            <Snackbar
                open={this.props.text && true}
                message={this.props.text}
                autoHideDuration={this.props.autoHideDuration}
                onRequestClose={this.props.handleClose}
            />);
    }
}

Error.propTypes = {
    handleClose: React.PropTypes.func.isRequired
};
Error.defaultProps = {
    text: DEFAULT_ERROR,
    autoHideDuration: 4000
};