export const deleteLike = async(likeId) => {

    try{
    fetch("/api/v0/likes/" + likeId,{
        method: "DELETE",
        headers:{
            "Content-type": "application/json",
            "Authorization": localStorage.getItem("tokenKey")
        },
    });
}catch(e){
    console.log('error.message', e.message);
    return [];
}
}

export const saveLike = async(postId, userId) => {
    console.log('tokenKey', localStorage.getItem("tokenKey"));
    try{
        fetch("/api/v0/likes", {
            method: "POST",
            headers:{
                "Content-type": "application/json",
                "Authorization": localStorage.getItem("tokenKey")
            },
            body: JSON.stringify({
                postId: postId,
                userId: userId
            })
        });
    }catch(e){
        console.log('error.message', e.message);
    }
}