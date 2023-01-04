import React, {useState, useEffect} from "react";
import Post from "../Post/Post";
import { makeStyles } from '@material-ui/core/styles';
import { getAllPosts } from "../../service/post";
import PostForm from "../Post/PostForm";

const useStyles = makeStyles((theme) => ({
    container:{
        display :"flex",
        flexWrap :"wrap",
        justifyContent : "center",
        alignItems: "center",
        backgroundColor: "#f0f5ff"

    }
}));
const Home = () => {
    const [error, setError] = useState(null);
    const [isLoaded, setIsLoaded] = useState(false);
    const [postList, setPostList] = useState([]);
    const classes = useStyles();


    const refreshPosts = async () => {
        setPostList(await getAllPosts());
        setIsLoaded(true);
    }

    const getPosts = async () =>  {
        setPostList(await getAllPosts());
        setIsLoaded(true);
    }
    useEffect(() => {
           getPosts();
    },[]);

    useEffect(() => {
        refreshPosts();
 },[]);

    if(error){
        return <div>Error !!!</div>
    }else if(!isLoaded){
        return <div>Loading...</div>
    }else{
        console.log('currentUser', localStorage.getItem("currentUser"));
        return(
            <div className={classes.container}>
             {localStorage.getItem("currentUser") == null ? "" : <PostForm 
             userId={localStorage.getItem("currentUser")} 
             userName={localStorage.getItem("username")} 
              refreshPosts={refreshPosts}/>}
                {
                    postList.map(post => (<Post postId = {post.id} title={post.title} text={post.text}
                      userId={post.userId} userName={post.userName} likes={post.postLikes}/>
                    ))
                }
            </div>
        )
    }
}

export default Home;