import React, {useState, useRef, useEffect} from "react";
import { styled } from '@mui/material/styles';
import Card from '@mui/material/Card';
import CardHeader from '@mui/material/CardHeader';
import CardMedia from '@mui/material/CardMedia';
import CardContent from '@mui/material/CardContent';
import CardActions from '@mui/material/CardActions';
import Collapse from '@mui/material/Collapse';
import Avatar from '@mui/material/Avatar';
import IconButton, { IconButtonProps } from '@mui/material/IconButton';
import Typography from '@mui/material/Typography';
import { red } from '@mui/material/colors';
import FavoriteIcon from '@mui/icons-material/Favorite';
import ShareIcon from '@mui/icons-material/Share';
import ExpandMoreIcon from '@mui/icons-material/ExpandMore';
import MoreVertIcon from '@mui/icons-material/MoreVert';
import { makeStyles } from '@material-ui/core/styles';
import CommentIcon from '@mui/icons-material/Comment';
//import Comment from "@mui/icons-material/Comment";
import {Link} from "react-router-dom";
import { getCommentsByPostId } from "../../service/comment";
import Container from '@material-ui/core/Container';
import clsx from 'clsx';
import Comment from "../Comment/Comment";
import CommentForm from "../Comment/CommentForm";
import { saveLike, deleteLike } from "../../service/like";

const useStyles = makeStyles((theme) => ({
    root: {
      width: 800,
      textAlign : "left",
      margin : 20
    },
    media: {
      height: 0,
      paddingTop: '56.25%', // 16:9
    },
    expand: {
      transform: 'rotate(0deg)',
      marginLeft: 'auto',
      transition: theme.transitions.create('transform', {
        duration: theme.transitions.duration.shortest,
      }),
    },
    avatar: {
      background: 'linear-gradient(45deg, #2196F3 30%, #21CBF3 90%)',
    },
    link: {
        textDecoration : "none",
        boxShadow : "none",
        color : "white"
    }
  }));

interface ExpandMoreProps extends IconButtonProps {
    expand: boolean;
  }
  
  const ExpandMore = styled((props: ExpandMoreProps) => {
    const { expand, ...other } = props;
    return <IconButton {...other} />;
  })(({ theme, expand }) => ({
    transform: !expand ? 'rotate(0deg)' : 'rotate(180deg)',
    marginLeft: 'auto',
    transition: theme.transitions.create('transform', {
      duration: theme.transitions.duration.shortest,
    }),
  }));

  const Post = (props) => {

    //const {post : {title, text, userId, userName}} = props;
    const {title, text, userId, userName, postId, likes} = props;
    const [expanded, setExpanded] = React.useState(false);
    const [isLiked, setIsLiked] = useState(false);
    const [commentList, setCommentList] = useState([]);
    const [isLoaded, setIsLoaded] = useState(false);
    const isInitialMount = useRef(true);
    const [error, setError] = useState(null);
    const [likeCount, setLikeCount] = useState(likes.length);
    const [likeId, setLikeId] = useState(1);
    const [refresh, setRefresh] = useState(false);
    let disabled = localStorage.getItem("currentUser") == null ? true : false;

    const commentText = {
      userId: 1,
      username: 'boskefm',
      text:'commentText!!',
      postId: postId
    }
    const classes = useStyles();

    const setCommentRefresh = () => {
      setRefresh(true)
    }

    const handleExpandClick = () => {
      setExpanded(!expanded);
      console.log('clicked expanded!');
      refreshComments();
      console.log('commentlist', commentList);
    };

    const handleLike = () => {
        setIsLiked(!isLiked);
        if(!isLiked){
         saveLike(postId, localStorage.getItem("currentUser"));
         setLikeCount(likeCount + 1);
        }
        else{
         deleteLike(likeId); 
         setLikeCount(likeCount - 1);
        }
    }

    const refreshComments = async () => {
        setCommentList(await getCommentsByPostId(postId));
        setIsLoaded(true);
        setRefresh(false);
    }

    useEffect(() => {
        if(isInitialMount.current)
            isInitialMount.current = false;
        else{
            refreshComments();
        }
 },[refresh])

 const checkLikes = () => {
   var likeControl = likes.find((like => like.userId === localStorage.getItem("currentUser")));
   if(likeControl != null){
     setLikeId(likeControl.id);
     setIsLiked(true);
   }
 }

 useEffect(() => {
  checkLikes();
 }, []);

 console.log('disabledInfo', disabled);
 console.log('localStorageCurrentUser', localStorage.getItem("currentUser"));
   return(
    <Card className={classes.root} >
    <CardHeader
      avatar={
        <Link className={classes.link} to={{pathname : '/users/' + userId}}>
        <Avatar sx={{ bgcolor: red[500] }} aria-label="recipe">
          {localStorage.getItem("currentUser") != null ? 
          userName: ''}
        </Avatar>
        </Link>
      }
      title={title}
    />
   
    <CardContent>
      <Typography variant="body2" color="text.secondary">
        {text}
      </Typography>
    </CardContent>
    <CardActions disableSpacing>
      { disabled ? 
      <IconButton aria-label="add to favorites"
      disabled
      onClick={handleLike}>
        <FavoriteIcon style={isLiked ? {color : 'red'} : null} />
      </IconButton>
      : 
      <IconButton aria-label="add to favorites"
      onClick={handleLike}>
        <FavoriteIcon style={isLiked ? {color : 'red'} : null} />
      </IconButton>
      }
      {likeCount}
      <IconButton
                    className={clsx(classes.expand, {
                        [classes.expandOpen]: expanded,
                    })}
                    onClick={handleExpandClick}
                    aria-expanded={expanded}
                    aria-label="show more"
                    >
                      {expanded}
                    <CommentIcon />
                    </IconButton>
    </CardActions>
    <Collapse in={expanded} timeout="auto" unmountOnExit>
                    <Container fixed className = {classes.container}>
                    {error? "error" :
                    isLoaded? commentList.map(comment => (
                      <Comment comment = {comment} id={comment.userId} username = {comment.username} />
                     
                    )) : "Loading"}
                    {
                    disabled ? "":
                  <CommentForm username={localStorage.getItem("username")} comment = {commentText} setCommentRefresh={setCommentRefresh}></CommentForm>
                   }
                    </Container>
                </Collapse>
  </Card>
   )
}

export default Post;