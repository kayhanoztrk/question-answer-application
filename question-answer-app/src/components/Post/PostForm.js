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
import Comment from "@mui/icons-material/Comment";
import {Link} from "react-router-dom";
import OutlinedInput from '@mui/material/OutlinedInput';
import { InputAdornment } from "@mui/material";
import Button from '@mui/material/Button';
import { savePost } from "../../service/post";
import Snackbar from '@mui/material/Snackbar';
import MuiAlert, { AlertProps } from '@mui/material/Alert';
import Stack from '@mui/material/Stack';

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

  const PostForm = (props) => {

    //const {post : {title, text, userId, userName}} = props;
    const { userId, userName, refreshPosts} = props;
    const [expanded, setExpanded] = React.useState(false);
    const [liked, setLiked] = useState(false);

    const [title, setTitle] = useState("");
    const [text, setText] = useState("");

    const [isSent, setIsSent] = useState(false);
    const classes = useStyles();

    const [open, setOpen] = React.useState(false);
    const [message, setMessage] = useState("");

  const handleClick = () => {
    setOpen(true);
  };

  const handleClose = (event?: React.SyntheticEvent | Event, reason?: string) => {
    if (reason === 'clickaway') {
      return;
    }

    setOpen(false);
  };

    const handleExpandClick = () => {
      setExpanded(!expanded);
    };

    const handleLike = () => {
        setLiked(!liked);
    }

    const handleSubmit = async () => {
        console.log(title, text);
       
        await savePost({
            userId: userId,
            title: title,
            text: text
        });
        setIsSent(true);
        setTitle("");
        setText("");
        setOpen(true);
        setMessage("Your post is saved!");
        refreshPosts();
    }

    const handleTitle = (value) => {
       setTitle(value);
       setIsSent(false);
    }

    const handleText = (value) => {
        setText(value);
        setIsSent(false);
    }

   return(
       <div>
     <Snackbar
        message={message}
        open={open}
        onRequestClose={() => setOpen(false)}
        autoHideDuration={2000}
      />
    <Card className={classes.root} >
    <CardHeader
      avatar={
        <Link className={classes.link} to={{pathname : '/users/' + userId}}>
        <Avatar sx={{ bgcolor: red[500] }} aria-label="recipe">
         {userName.charAt(0).toUpperCase()}
        </Avatar>
        </Link>
      }
      title={<OutlinedInput 
    id="outlined-adornment-amount"
    multiline
    placeholder = "title"
    inputProps = {{maxLength : 25}}
    fullWidth
    value={title}
    onChange={(e) => handleTitle(e.target.value)}></OutlinedInput>    
}
    />
   
    <CardContent>
      <Typography variant="body2" color="text.secondary">
      <OutlinedInput 
    id="outlined-adornment-amount"
    multiline
    placeholder = "title"
    inputProps = {{maxLength : 250}}
    fullWidth
    value={text}
    onChange={(e) => handleText(e.target.value)}
    endAdornment = {
        <InputAdornment position="end">
        <Button variant="contained"
         style= {{background: 'linear-gradient(45deg, #2196F3 30%, #21CBF3 90%)',
         color: 'white'}}
         onClick = {handleSubmit}  
        >POST</Button>
        </InputAdornment>
    }
    ></OutlinedInput>
      </Typography>
    </CardContent>
  </Card>
  </div>
   )
}

export default PostForm;