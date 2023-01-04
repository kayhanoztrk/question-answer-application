import { useHistory } from "react-router-dom";


export const sendRequest = async (process, userInfo) => {

    //console.log('userInfo', userInfo.username + 'and password: ' + userInfo.password);
    try{
    const response = await fetch('/auth/' + process, {
        method: 'POST',
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({
            username: userInfo.username,
            password: userInfo.password
        })
    });

    //console.log('responseJson', response.json());
    return response.json();
}catch(e){
    console.log('error.message', e);
    return [];
}}


export const refreshToken = async () => {

   
    try{
    const response = await fetch("/auth/refresh", {
        method: "POST",
        header: {
            "Content-type": "application/json"
        },
        body: JSON.stringify({
            userId: localStorage.getItem("currentUser"),
            refreshToken: localStorage.getItem("refreshKey")
        })
    });

    return response.json();
}catch(e){
    console.log('error.message', e);
        localStorage.removeItem("tokenKey");
        localStorage.removeItem("currentUser");
        localStorage.removeItem("userName");
        localStorage.removeItem("refreshKey");
    return [];
}}