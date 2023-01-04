import { FormControl, InputLabel, Input, Button, FormHelperText } from "@material-ui/core";
import React, { useState} from "react";
import { useHistory } from "react-router-dom";
import { sendRequest } from "../../service/auth";


const Auth = () => {

    const[username, setUsername] = useState("");
    const[password, setPassword] = useState("");
    const history = useHistory();

    const handleUsername = (value) => {
        setUsername(value);
    }

    const handlePassword = (value) => {
        setPassword(value);
    }

    const handleRegister = async () => {
        const process = 'register';
        const userInfo = {
            username: username,
            password: password
        };

        const result = await sendRequest(process, userInfo);
        localStorage.setItem("tokenKey", result.accessToken);
        localStorage.setItem("refreshKey", result.refreshToken);
        localStorage.setItem("currentUser", result.userId);
        localStorage.setItem("username", username);

        console.log('currentUser', result.userId);

        setUsername("");
        setPassword("");

        //it will redirect to auth page again.
        history.go("/auth");
    }

    const handleLogin = async () => {
        const process = 'login';
        const userInfo = {
            username: username,
            password: password
        };

        console.log('processInfo', process);
        const result = await sendRequest(process, userInfo);

        localStorage.setItem("tokenKey", result.accessToken);
        localStorage.setItem("refreshKey", result.refreshToken);
        localStorage.setItem("currentUser", result.userId);
        localStorage.setItem("username", username);

        setUsername("");
        setPassword("");

        history.go("/auth");

       
    }

   return( 
   <FormControl>
        <InputLabel>Username:</InputLabel>
        <Input
        onChange={(e) => handleUsername(e.target.value)}/>
        <InputLabel style={{top: 80}}>Password:</InputLabel>
        <Input style={{top:40}}
        onChange={(e) => handlePassword(e.target.value)}/>

        <Button variant="contained"
        style={{marginTop:60,
        background: 'linear-gradient(45deg, #2196F3 30%, #21CBF3 90%)',
        color: 'white'}}
        onClick={handleRegister}>Register</Button>
        <FormHelperText style={{margin: 20}}>Are you already registered?</FormHelperText>
        <Button variant="contained"
        style={{
        background: 'linear-gradient(45deg, #2196F3 30%, #21CBF3 90%)',
        color: 'white'}}
        onClick={handleLogin}
        >Login</Button>
    </FormControl>);
}

export default Auth;