import React, {PropTypes} from "react";
import TextField from 'material-ui/TextField';




export default class FilterInput extends React.Component{
    render(){
        let text=this.props.searchText;
        let search =this.props.filter;

        const update=(event, newValue)=>{
          //  console.log("FilterInput event:");
          //  console.log(event);
            search(newValue);
        };

        return <TextField onChange={update} hintText={text}/>
    }
}

FilterInput.propTypes = {
    filter: React.PropTypes.func.isRequired
};
FilterInput.defaultProps = {
    searchText: 'Поиск..'
};