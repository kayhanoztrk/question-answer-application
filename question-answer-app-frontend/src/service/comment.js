import { refreshToken } from "./auth";

export const getCommentsByPostId = async (postId) => {
    try{
        const response = await fetch("/api/v0/comments?postId=" + postId,{
            method:'GET',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json',
                "Authorization": localStorage.getItem("tokenKey")
            },
        });
        return response.json();
     }catch(e){
         console.log('error', e.message);
         return [];
     }
}

export const saveComment = async(comment) => {
    try{
        
        const response = await fetch('/api/v0/comments',{
            method: 'POST',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json',
                "Authorization": localStorage.getItem("tokenKey")
            },
            body: JSON.stringify({
                postId: comment.postId,
                userId: comment.userId,
                text: comment.text
            })
        });

        console.log('responseDenem', response);
    }catch(e){
        console.log('error.message', e.message);
        if(e=="Unauthorized"){
        const result = await refreshToken();
        localStorage.setItem("tokenKey", result.accessToken);
        localStorage.setItem("refreshKey", result.refreshToken);
        }
        return [];
    }
}

