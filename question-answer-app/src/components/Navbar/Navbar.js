import React from "react";
import {Link, useHistory} from "react-router-dom";
import AppBar from '@mui/material/AppBar';
import Box from '@mui/material/Box';
import Toolbar from '@mui/material/Toolbar';
import Typography from '@mui/material/Typography';
import Button from '@mui/material/Button';
import IconButton from '@mui/material/IconButton';
import MenuIcon from '@mui/icons-material/Menu';

import { makeStyles } from '@material-ui/core/styles';
import { LockOpen, NoEncryption } from "@mui/icons-material";

const useStyles = makeStyles((theme) => ({

    root:{
        flexGrow:1,
    },
    menuButton: {
        marginRight: theme.spacing(2),
    },
    title: {
        flexGrow: 1,
        textAlign: "left",
        textDecoration: "none",
        color: "white"
    },
    link: {
        textDecoration: "none",
        boxShadow: "none",
        color: "white"
    }
}));
const Navbar = () => {

    const userId = 15;
    const classes = useStyles();
    const history = useHistory();

    const onClick = () => {
      localStorage.removeItem("tokenKey");
      localStorage.removeItem("currentUser");
      localStorage.removeItem("userName");
      localStorage.removeItem("refreshKey");
      history.go(0);
    }

    return(
    <AppBar position="static">
        <Toolbar>
          <IconButton
            size="large"
            edge="start"
            color="inherit"
            aria-label="menu"
            sx={{ mr: 2 }}
          >
            <MenuIcon />
          </IconButton>
          <Typography variant="h6" component="div" sx={{ flexGrow: 1 }}>
          <Link className={classes.title} to="/">Home</Link>
          </Typography>
          <Typography variant="h6" component="div" sx={{ flexGrow: 1 }}>
            {localStorage.getItem("currentUser") == null ? 
            <Link className={classes.link} to="/auth">Login/Register</Link> :
            <div><IconButton onClick={onClick}><LockOpen>
              </LockOpen></IconButton>
          <Link className={classes.link} to={{pathname : '/users/' + localStorage.getItem("currentUser")}}>Profile</Link>
        </div>}
          </Typography>
        </Toolbar>
      </AppBar>
    )
}

export default Navbar;