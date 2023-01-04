import React from "react";
//import {makeStyles} from "@mui/material";
import {Link} from "react-router-dom";
import { CardContent, InputAdornment, OutlinedInput, Avatar} from "@material-ui/core";
import { makeStyles } from '@material-ui/core/styles';

const useStyles = makeStyles((theme) => ({
    comment : {
        display: "flex",
        flexWrap: "wrap",
        justifyContent : "flex-start",
        alignItems : "center",
      },
      small: {
        width: theme.spacing(4),
        height: theme.spacing(4),
      },
      link: {
          textDecoration : "none",
          boxShadow : "none",
          color : "white"
      }
  }));

const Comment = (props) => {
    console.log('props', props);
    const {comment,username} = props;
    const classes = useStyles();
    console.log('propertiesInfos', props);

    return(
        <CardContent className = {classes.comment}>

        <OutlinedInput
        disabled
        id="outlined-adornment-amount"
        multiline
        inputProps = {{maxLength : 25}}
        fullWidth
        value={comment.text}
        startAdornment = {
            <InputAdornment position="start">
                <Link  className={classes.link} to={{pathname : '/users/' + comment.userId}}>
                    <Avatar aria-label="recipe" className={classes.small}>
                        {username.charAt(0).toUpperCase()}
                    </Avatar>
                </Link>
            </InputAdornment>
        }
        style = {{ color : "black",backgroundColor: 'white'}}
        ></OutlinedInput>
        </CardContent>

    )
}

export default Comment;