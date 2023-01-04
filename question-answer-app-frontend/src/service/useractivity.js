export const getActivity = async (userId) => {
    console.log('userId', userId);
    try{
    const response = await fetch("/api/v0/users/activity/" + userId);
    console.log('activityResp', response);
    return response.json();
}catch(e){
    console.log('error', e);
    return [];
}

}