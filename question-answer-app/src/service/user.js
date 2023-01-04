export const getActivityByUserId = async(userId) => {
    try{
        const response = await fetch('/api/v0/users/activity/' + userId);
        return response.json();
    }catch(e){
        console.log('error', e.message);
        return[];
    }
}

export const getUserById = async(userId) => {
    try{
        console.log('userıd', userId);
        const response = await fetch('/api/v0/users/' + userId);
        return response.json();
    }catch(e){
        console.log('error', e.message);
        return[];
    }
}