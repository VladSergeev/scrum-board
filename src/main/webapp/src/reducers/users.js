const users = (state = {
                   list: [],
                   isFetching: false,
                   error: null,
                   criteria: {
                       page: 0,
                       size: 10,
                       login: ""
                   }
               },
               action) => {
    console.log("What reducer get!");
    console.log(state);
    console.log(action);

    switch (action.type) {
        case "FETCH_USERS_DELETE_SUCCESS":
            return {
                ...state,
                isFetching: false,
                list: state.list.filter(x => x.login !== action.userId)
            };
        case "DISPLAY_SPINNER":
            return {...state, isFetching: true};
        case 'FETCH_USERS_SUCCESS':
            return {
                ...state,
                isFetching: false,
                list: action.data,
                criteria: action.criteria
            };
        case 'SHOW_ERROR':
            return {...state, isFetching: false, error: action.error};
        case 'CLOSE_ERROR_MESSAGE':
            return {...state, error: null};
        case 'UPDATE_USER':
            let exceptUpdated=state.list.filter(x => x.login!==action.user.login);
            if (action.user.login.includes(state.criteria.login)) {
                exceptUpdated = [...exceptUpdated, action.user];
            } else {
                exceptUpdated = [...exceptUpdated];
            }
            return {...state, isFetching: false, list: exceptUpdated};
        case 'ADD_USER':
            let list;
            if (action.user.login.includes(state.criteria.login)) {
                list = [...state.list, action.user];
            } else {
                list = [...state.list];
            }
            return {...state, isFetching: false, list: list};
        default:
            return state
    }
};

export default users