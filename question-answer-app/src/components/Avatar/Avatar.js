import React, {useState, useRef, useEffect} from 'react';
import Card from '@mui/material/Card';
import CardActions from '@mui/material/CardActions';
import CardContent from '@mui/material/CardContent';
import CardMedia from '@mui/material/CardMedia';
import Button from '@mui/material/Button';
import Typography from '@mui/material/Typography';
import { makeStyles } from '@material-ui/core';
import Modal from '@mui/material/Modal';
import {ListItem, List, ListItemSecondaryAction, Radio} from '@material-ui/core';
import { flexbox } from '@mui/system';
import { saveAvatar } from '../../service/avatar';

const useStyles = makeStyles({
  root:{
    maxWidth: 345,
    margin: 50
  },
  modal: {
    display:"flex",
    maxWidth: 200
  }
});
const Avatar = (props) => {
  console.log(props);
  const {avatarId, userId, userName} = props;
  const [selectedValue, setSelectedValue] = useState(avatarId);

  const classes = useStyles();

  const [open, setOpen] = React.useState(false)
  const handleOpen = () => setOpen(true);
  const handleClose = () => {
    setOpen(false);
    saveAvatar(selectedValue);
  }
  const handleChange = (event) => {
    setSelectedValue(event.target.value);
  };


  return (
    <div>
    <Card className={classes.root}>
        <CardMedia
          component="img"
          alt="User Avatar"
          image={`/avatars/avatar${selectedValue}.png`}
          title="User Avatar"
        />
        <CardContent>
          <Typography gutterBottom variant="h5" component="h2">
            {userName}
          </Typography>
          <Typography variant="body2" color="textSecondary" component="p">
            User info
          </Typography>
        </CardContent>
      <CardActions>
        {localStorage.getItem("currentUser") == userId ? <Button size="small" color="primary"  onClick={handleOpen}>
          Change Avatar
        </Button> : ""}
      </CardActions>
    </Card>
    <Modal
    className={classes.modal}
    open={open}
    onClose={handleClose}
    aria-labelledby="simple-modal-title"
    aria-describedby="simple-modal-description"
    >
    <List dense>
      {[1, 2, 3, 4, 5, 6].map((key) => {
        const labelId = `checkbox-list-secondary-label-${key}`;
        console.log('key:', key);
        return (
          <ListItem key={key} button>
              <CardMedia
              style = {{maxWidth: 100}}
              component="img"
              alt={`Avatar n°${key}`}
              image={`/avatars/avatar${key}.png`}
              title="User Avatar"
              />
            <ListItemSecondaryAction>
              <Radio
                edge="end"
                value= {key}
                onChange={handleChange}
                checked={""+selectedValue === ""+key}
                inputProps={{ 'aria-labelledby': labelId }}
              />
            </ListItemSecondaryAction>
          </ListItem>
        );
      })}
    </List>
    </Modal>
    </div>
  );
}

export default Avatar;