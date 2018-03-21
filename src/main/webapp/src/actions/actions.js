import axios from "axios";
import axiosCancel from 'axios-cancel';

axiosCancel(axios, {
    debug: false // default
});



export const updateUserSuccess = (user) => ({
    type: 'UPDATE_USER',
    user
});

export const addUserSuccess = (user) => ({
    type: 'ADD_USER',
    user
});

export const fetchUserDeleteSuccess = (response,userId) => ({
        type: "FETCH_USERS_DELETE_SUCCESS",
        data: response,
        userId:userId
    }
);

export const fetchUsersSuccess = (response, criteria) => ({
        type: "FETCH_USERS_SUCCESS",
        data: response.data.content,
        criteria: criteria
    }
);

export const displaySpinner = () => (
    {
        type: "DISPLAY_SPINNER"
    }
);

export const displayError = (error) => ({
    type: "SHOW_ERROR",
    error: error
});

export const closeErrorMessage = () => ({
    type: "CLOSE_ERROR_MESSAGE"
});

export function deleteUser(userId) {
    return function (dispatch) {
        dispatch(displaySpinner());
        axios.delete('/api/v1/user/'+userId)
            .then((response) => {
                dispatch(fetchUserDeleteSuccess(response,userId))
            })
            .catch((error) => {
                console.log("Error!");
                console.log(error);
                if (!axios.isCancel(error)) {
                    dispatch(displayError(error.response.data.description))
                }
            });

    }
}

export function editUser(user,closeDialog) {
    return function (dispatch) {
        dispatch(displaySpinner());
        axios.put('/api/v1/user',user)
            .then((response) => {
                closeDialog();
                dispatch(updateUserSuccess(response.data))
            })
            .catch((error) => {
                console.log("Error!");
                console.log(error);
                if (!axios.isCancel(error)) {
                    console.log(error);
                    dispatch(displayError(error.response.data.description))
                }
            });

    }
}

export function addUser(user,closeDialog) {
    return function (dispatch) {
        dispatch(displaySpinner());
        axios.post('/api/v1/user',user)
            .then((response) => {
                closeDialog();
                dispatch(addUserSuccess(response.data))
            })
            .catch((error) => {
                console.log("Error!");
                console.log(error);
                if (!axios.isCancel(error)) {
                    console.log(error);
                    dispatch(displayError(error.response.data.description))
                }
            });

    }
}

export function fetchUsers(filter) {

    return function (dispatch, state) {

        console.log("fetchUsers state!");
        const curState = state().user;
        const localFilter = filter ? filter : "";
        if (curState.isFetching) {
            axios.cancel('ajax-users');
            console.log("Break!");
        }
        let criteria = {
            page: curState.criteria.page,
            size: curState.criteria.size,
            login: localFilter
        };
        let config = {
            params: criteria,
            requestId: 'ajax-users'
        };

        dispatch(displaySpinner());
        axios.get('/api/v1/user/list', config)
            .then((response) => {
                dispatch(fetchUsersSuccess(response, criteria))
            })
            .catch((error) => {
                console.log("Error!");
                console.log(error);
                if (!axios.isCancel(error)) {
                    dispatch(displayError(error.response.data.description))
                }
            });

    }
}