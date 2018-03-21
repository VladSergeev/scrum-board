"use strict";

import React from "react";
import axios from "axios";
import {Table, TableBody, TableFooter, TableHeader, TableHeaderColumn, TableRow, TableRowColumn} from "material-ui/Table";
import {IconButton, IconMenu, MenuItem} from "material-ui";
import MoreVertIcon from "material-ui/svg-icons/navigation/more-vert";
import FloatingActionButton from "material-ui/FloatingActionButton";
import ContentAdd from "material-ui/svg-icons/content/add";
import axiosCancel from "axios-cancel";
import PreLoader from "./PreLoader";
import SubmitDialog from "./SubmitDialog";
import Divider from "material-ui/Divider";
import TextField from "material-ui/TextField";
import SelectField from "material-ui/SelectField";

axiosCancel(axios, {
    debug: false // default
});

export class Projects extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            projects: [],
            page: 0,
            size: 10,
            isFetching:false
        };
        this.closeCreateProjectDialog = this.closeCreateProjectDialog.bind(this);
        this.saveProject = this.saveProject.bind(this);
    }

    fetchState() {
        if (this.state.isFetching) {
            axios.cancel(this.props.requestId);
            console.log("Break!");
        }
        this.setState({isFetching: true});
        axios.get('/api/v1/project', {
            params: {
                page: this.state.page,
                size: this.state.size
            },
            requestId: this.props.requestId
        }).then((response) => {
            this.setState({projects: response.data.content,isFetching: false});
        }).catch((error) => {
            console.log(error);
            if (!axios.isCancel(error)) {
                this.setState({
                    isFetching: false
                });
            }
        });
    }


    deleteProject(projectId) {
        console.log("deleteProject");
        axios.delete('/api/v1/project/' + projectId, {headers: {'Content-Type': 'application/json'}}).then(() => {
            this.setState({
                projects: this.state.projects.filter((project) => {
                    return project.id !== projectId;
                })
            });
        }).catch((error) => {
            console.log(error);
        });
    }

    editProject(project) {
        console.log("editProject");
        console.log(project);
        this.setState({showCreateDialog: true, currentProject: Object.assign({}, project), currentProjectAction: "update"});
    }

    createNewProject() {
        this.setState({showCreateDialog: true, currentProject: {}, currentProjectAction: "create"});
        console.log("createNewProject");
    }

    closeCreateProjectDialog(newProject) {
        this.setState({showCreateDialog: false});
        if (newProject === undefined) {
            return;
        }

        if (this.state.currentProjectAction === "create") {

            let projects = this.state.projects.slice();
            projects.push(newProject);
            this.setState({
                projects: projects
            });
        } else {
            let projects = this.state.projects.slice();
            let oldProject = projects.filter((project) => {
                return project.id === newProject.id;
            })[0];
            Object.assign(oldProject, newProject);

            this.setState({
                projects: projects
            });
        }
    }

    onChange() {
        let project = this.currentProject;
        let isDisabledSubmit;
        if (project.description === undefined || project.description === "" || project.status === undefined || project.status === "" || project.name === undefined || project.name === "") {
            isDisabledSubmit = true;
        } else {
            isDisabledSubmit = false;
        }

        if (this.isDisabledSubmit !== isDisabledSubmit) {
            this.isDisabledSubmit = isDisabledSubmit;
            this.forceUpdate();
        }
    };

    saveProject() {
        let project = this.currentProject;
        if (this.state.currentProjectAction === "create") {
            axios.post('/api/v1/project', project).then((response) => {
                this.closeCreateProjectDialog(response.data);
            }).catch((error) => {
                console.log(error);
            });
        } else {
            project.id = this.currentProject.id;
            axios.put('/api/v1/project', project).then((response) => {
                this.closeCreateProjectDialog(response.data);
            }).catch((error) => {
                console.log(error);
            });
        }

    }

    render() {
        this.currentProject = this.state.currentProject ? this.state.currentProject : {};
        let dialogSettings = {
            isOpenDialog: this.state.showCreateDialog,
            handleClose: this.closeCreateProjectDialog,
            handleSubmit: this.saveProject,
            project: this.state.currentProject,
            labelCloseButton: "Закрыть",
            labelSubmitButton: "Сохранить",
            titleDialog: "Проект"
        };
        return (

            <Table selectable={false} multiSelectable={false} fixedHeader={true}>
                <TableHeader adjustForCheckbox={false} displaySelectAll={false}>
                    <TableRow>
                        <TableHeaderColumn colSpan="3">
                            <h1>Projects</h1>
                        </TableHeaderColumn>
                        <TableHeaderColumn>
                            {this.state.isFetching && <PreLoader/>}
                        </TableHeaderColumn>
                    </TableRow>
                    <TableRow>
                        <TableHeaderColumn>ID</TableHeaderColumn>
                        <TableHeaderColumn>Name</TableHeaderColumn>
                        <TableHeaderColumn>Status</TableHeaderColumn>
                        <TableHeaderColumn>Action</TableHeaderColumn>
                    </TableRow>
                </TableHeader>
                <TableBody displayRowCheckbox={false}>
                    {this.state.projects.map(project => (
                        <TableRow key={project.id}>
                            <TableRowColumn>{project.id}</TableRowColumn>
                            <TableRowColumn>{project.name}</TableRowColumn>
                            <TableRowColumn>{project.status}</TableRowColumn>
                            <TableRowColumn>
                                <IconMenu iconButtonElement={<IconButton><MoreVertIcon /></IconButton>}
                                          anchorOrigin={{horizontal: 'left', vertical: 'top'}}
                                          targetOrigin={{horizontal: 'left', vertical: 'top'}}
                                          useLayerForClickAway={true}>
                                    <MenuItem primaryText="Edit" onTouchTap={() => this.editProject(project)}/>
                                    <MenuItem primaryText="Delete" onTouchTap={() => {
                                        this.deleteProject(project.id);
                                    }}/>
                                </IconMenu>
                            </TableRowColumn>
                        </TableRow>
                    ))}
                </TableBody>
                <TableFooter>
                    <TableRow>
                        <TableRowColumn colSpan="4" style={{textAlign: 'right'}}>
                            <FloatingActionButton secondary={true} onMouseDown={() => this.createNewProject()}>
                                    <ContentAdd />
                            </FloatingActionButton>
                            <SubmitDialog {...dialogSettings}>
                                <TextField hintText="Name" defaultValue={this.currentProject.name} style={{marginLeft: 20}} underlineShow={false} onChange={(e) => {
                                    let value = e.target.value;
                                    this.currentProject.name = value;
                                    this.onChange();
                                }}/>
                                <Divider />
                                <TextField hintText="Description" defaultValue={this.currentProject.description} style={{marginLeft: 20}} underlineShow={false} onChange={(e) => {
                                    let value = e.target.value;
                                    this.currentProject.description = value;
                                    this.onChange();
                                }}/>
                                <Divider />
                                <SelectField
                                    floatingLabelText="Status"
                                    value={this.currentProject.status}
                                    fullWidth={true}
                                    style={{marginLeft: 20, width: '85%'}}
                                    onChange={(event, index, value) => {
                                        this.currentProject.status = value;
                                        this.setState({projectStatus: value});
                                        this.onChange();
                                    }}

                                >
                                    <MenuItem value={"NEW"} primaryText="NEW"/>
                                    <MenuItem value={"CLOSED"} primaryText="CLOSED"/>
                                    <MenuItem value={"RND"} primaryText="RND"/>
                                </SelectField>
                                <Divider />
                            </SubmitDialog>
                        </TableRowColumn>
                    </TableRow>
                </TableFooter>
            </Table>
        );
    }

    componentDidMount() {
        this.fetchState()
    }
}

Projects.defaultProps = {
    requestId: 'ajax-projects'
};