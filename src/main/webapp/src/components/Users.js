"use strict";

import React, {PropTypes} from "react";

import {List, ListItem} from 'material-ui/List';
import {
    Table, TableBody, TableFooter, TableHeader, TableHeaderColumn, TableRow, TableRowColumn
}    from 'material-ui/Table';
import MoreVertIcon from 'material-ui/svg-icons/navigation/more-vert';
import IconMenu from 'material-ui/IconMenu';
import MenuItem from 'material-ui/MenuItem';
import IconButton from 'material-ui/IconButton';
import {grey400} from 'material-ui/styles/colors';
import axios from 'axios';
import axiosCancel from 'axios-cancel';
import FloatingActionButton from 'material-ui/FloatingActionButton';
import ContentAdd from 'material-ui/svg-icons/content/add';
import FilterInput from "./FilterInput";
import PreLoader from "./PreLoader";
import Error from "./Error";
import SubmitDialog from "./SubmitDialog";
import {fetchUsers, editUser, displayError, addUser, deleteUser, closeErrorMessage} from "../actions/actions";
import TextField from 'material-ui/TextField';
import Divider from "material-ui/Divider";

axiosCancel(axios, {
    debug: false // default
});

export default class Users extends React.Component {

    constructor() {
        super();
        this.state = {
            user: {
                login: "",
                password: "",
                confirmPassword: "",
                email: "",
                firstName: "",
                middleName: "",
                lastName: ""
            },
            showSubmitDialog: false,
            dialogAction: ""
        };
        this.fetchState = this.fetchState.bind(this);
        this.handleError = this.handleError.bind(this);
        this.onChangeUserState = this.onChangeUserState.bind(this);
        this.updateUser = this.updateUser.bind(this);
        this.closeDialog = this.closeDialog.bind(this);
    }

    componentDidMount() {
        const {store} = this.context;
        this.unsubscribe = store.subscribe(() => this.forceUpdate());
        this.fetchState()
    }

    componentWillUnmount() {
        this.unsubscribe()
    }

    fetchState(filter = "") {
        const {store} = this.context;
        store.dispatch(fetchUsers(filter))
    }

    handleError() {
        const {store} = this.context;
        store.dispatch(closeErrorMessage());
    }

    create() {
        this.setState({showSubmitDialog: true, dialogAction: 'create'});
    }

    edit(user) {
        this.setState({
            showSubmitDialog: true,
            dialogAction: 'edit',
            user: Object.assign({}, user)
        });
    }

    updateUser() {
        const {store} = this.context;
        const user = this.state.user;
        if (user.password === user.confirmPassword) {
            if (this.state.dialogAction === 'create') {
                store.dispatch(addUser(user, this.closeDialog));
            } else if (this.state.dialogAction === 'edit') {
                store.dispatch(editUser(user, this.closeDialog));
            } else {
                store.dispatch(displayError("Произошел запрос на невалидное действие!"))
            }
        } else {
            store.dispatch(displayError("Пароли не совпадают!"))
        }
    }

    deleteUser(userId) {
        const {store} = this.context;
        store.dispatch(deleteUser(userId));
    }

    onChangeUserState(event) {
        const field = event.target.name;
        const user = this.state.user;
        user[field] = event.target.value;
        return this.setState({user: user});
    }

    closeDialog() {
        this.setState({
            showSubmitDialog: false, dialogAction: '', user: {
                login: "",
                password: "",
                confirmPassword: "",
                email: "",
                firstName: "",
                middleName: "",
                lastName: ""
            }
        });
    }

    render() {
        const {store} = this.context;
        const state = store.getState();
        let dialogSettings = {
            isOpenDialog: this.state.showSubmitDialog,
            handleClose: this.closeDialog,
            handleSubmit: this.updateUser,
            labelCloseButton: "Закрыть",
            labelSubmitButton: "Сохранить",
            titleDialog: "Пользователь"
        };
        return (
            <div>
                <Table
                    fixedHeader={true}
                    fixedFooter={true}
                    selectable={false}
                    multiSelectable={false}>
                    <TableHeader
                        displaySelectAll={false}
                        adjustForCheckbox={false}
                    >
                        <TableRow>
                            <TableHeaderColumn/>
                            <TableHeaderColumn/>
                            <TableHeaderColumn/>
                            <TableHeaderColumn/>
                            <TableHeaderColumn>
                                {state.user.isFetching && <PreLoader/>}
                            </TableHeaderColumn>
                            <TableHeaderColumn>
                                <FilterInput filter={this.fetchState}/>
                            </TableHeaderColumn>
                        </TableRow>
                        <TableRow>
                            <TableHeaderColumn>Логин</TableHeaderColumn>
                            <TableHeaderColumn>Фамилия</TableHeaderColumn>
                            <TableHeaderColumn>Имя</TableHeaderColumn>
                            <TableHeaderColumn>Отчество</TableHeaderColumn>
                            <TableHeaderColumn>Почта</TableHeaderColumn>
                            <TableHeaderColumn>Изменить/Удалить</TableHeaderColumn>
                        </TableRow>
                    </TableHeader>
                    <TableBody
                        displayRowCheckbox={false}
                    >
                        {state.user.list.map(user =>
                            <TableRow key={user.login}>
                                <TableRowColumn>{user.login}</TableRowColumn>
                                <TableRowColumn>{user.lastName}</TableRowColumn>
                                <TableRowColumn>{user.firstName}</TableRowColumn>
                                <TableRowColumn>{user.middleName}</TableRowColumn>
                                <TableRowColumn>{user.email}</TableRowColumn>
                                <TableRowColumn>
                                    <IconMenu iconButtonElement={
                                        <IconButton
                                            touch={true}
                                            tooltip="more"
                                            tooltipPosition="bottom-left">
                                            <MoreVertIcon color={grey400}
                                            />
                                        </IconButton>}>
                                        <MenuItem primaryText="Изменение" onTouchTap={
                                            () => this.edit(user)
                                        }/>

                                        <MenuItem primaryText="Удаление" onTouchTap={
                                            () => {
                                                this.deleteUser(user.login);
                                            }}/>
                                    </IconMenu>
                                </TableRowColumn>
                            </TableRow>
                        )
                        }
                    </TableBody>
                    <TableFooter>
                        <TableRow>
                            <TableRowColumn colSpan="4" style={{textAlign: 'right'}}>
                                <FloatingActionButton secondary={true} onMouseDown={() => this.create()}>
                                    <ContentAdd />
                                </FloatingActionButton>

                                <SubmitDialog
                                    {...dialogSettings}
                                >
                                    <TextField name="login"
                                               disabled={this.state.dialogAction === 'edit'}
                                               hintText="Логин"
                                               defaultValue={this.state.user.login}
                                               style={{marginLeft: 20}}
                                               underlineShow={false}
                                               onChange={this.onChangeUserState}
                                    />
                                    <Divider />
                                    <TextField name="password"
                                               type="password"
                                               hintText="Пароль"
                                               defaultValue={this.state.user.password}
                                               style={{marginLeft: 20}}
                                               underlineShow={false}
                                               onChange={this.onChangeUserState}/>
                                    <Divider />
                                    <TextField name="confirmPassword"
                                               type="password"
                                               hintText="Пароль для подтверждения"
                                               defaultValue={this.state.user.confirmPassword}
                                               style={{marginLeft: 20}}
                                               underlineShow={false}
                                               onChange={this.onChangeUserState}/>
                                    <Divider />
                                    <TextField name="firstName"
                                               hintText="Имя"
                                               defaultValue={this.state.user.firstName}
                                               style={{marginLeft: 20}}
                                               underlineShow={false}
                                               onChange={this.onChangeUserState}/>
                                    <Divider />
                                    <TextField name="lastName"
                                               hintText="Фамилия"
                                               defaultValue={this.state.user.lastName}
                                               style={{marginLeft: 20}}
                                               underlineShow={false}
                                               onChange={this.onChangeUserState}/>
                                    <Divider />
                                    <TextField name="middleName"
                                               hintText="Отчество"
                                               defaultValue={this.state.user.middleName}
                                               style={{marginLeft: 20}}
                                               underlineShow={false}
                                               onChange={this.onChangeUserState}/>
                                    <Divider />
                                    <TextField name="email"
                                               hintText="Email"
                                               defaultValue={this.state.user.email}
                                               style={{marginLeft: 20}}
                                               underlineShow={false}
                                               onChange={this.onChangeUserState}/>
                                </SubmitDialog>
                            </TableRowColumn>
                        </TableRow>
                    </TableFooter>

                </Table>
                {
                    state.user.error &&
                    <Error
                        handleClose={this.handleError}
                        text={state.user.error}/>}
            </div>


        );
    }
}

Users.contextTypes = {
    store: React.PropTypes.object
};