export const getAllPosts = async () => {

    try{
       const response = await fetch("/api/v0/post/getAll");
       return response.json();
    }catch(e){
        console.log('error', e.message);
        return [];
    }
}

export const getPostById = async(postId) => {
    try{
        const response = await fetch("/api/v0/post/" + postId,{
            method: 'GET',
            headers: {
                "Content-type":"application/json",
                "Authorization":localStorage.getItem("tokenKey")
            }
        });
        return response.json();
    }catch(e){
        console.log('error', e.message);
        return [];
    }
}

export const savePost = async (post) => {
    try{
        await fetch("/api/v0/post", {
            method: 'POST',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json',
                "Authorization": localStorage.getItem("tokenKey")
            },
            body: JSON.stringify({
                title: post.title,
                userId: post.userId,
                text: post.text
            })
        });
    }catch(e){
        console.log('error', e);
    }
}