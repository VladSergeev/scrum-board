const general = (state = {
                   isFetching: false,
                   error: null
               },
               action) => {
    console.log("What reducer get!");
    console.log(state);
    console.log(action);

    switch (action.type) {
        case "DISPLAY_SPINNER":
            return {...state, isFetching: true};
        case 'REQUEST_FAILED':
            return {...state, isFetching: false, error: action.error};
        default:
            return state
    }
};