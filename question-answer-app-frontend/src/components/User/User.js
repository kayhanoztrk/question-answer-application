import { makeStyles } from "@material-ui/core";
import React,{useEffect, useState} from "react";
import { useParams } from "react-router-dom";
import Avatar from "../Avatar/Avatar";
import UserActivity from "../UserActivity/UserActivity";
import {getUserById} from "../../service/user"

const useStyles = makeStyles({
    root:{
    display:'flex'
}
});

const User = () => {
    const {userId} = useParams();
    const classes = useStyles();
    const [user, setUser] = useState();

    const getUser = async () => {
        const userInfo = await getUserById(userId);
        console.log('USERINFO', userInfo);
        setUser(userInfo);
    }

    useEffect(()=> {
        getUser();
    },[]);

    return(
        <div className={classes.root}>
            {user? <Avatar avatarId={user.avatar} userId={userId} userName={user.username}/> : "" }
            {localStorage.getItem("currentUser") == userId ?<UserActivity userId={userId} /> : ""}
        </div>
    )
}

export default User;