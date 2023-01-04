export const saveAvatar = async(avatarId) => {

    try{
        await fetch('/api/v0/users/' + localStorage.getItem("currentUser"),{
            method: 'PUT',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json',
                "Authorization": localStorage.getItem("tokenKey")
            },
            body: JSON.stringify({
                avatar: avatarId
            })
        })
    }catch(e){
        console.log('error.message', e.message);
        return [];
    }
}