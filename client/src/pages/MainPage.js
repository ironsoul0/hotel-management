import React, { useEffect } from "react";

import { makeStyles } from "@material-ui/core/styles";
import AppBar from "@material-ui/core/AppBar";
import Toolbar from "@material-ui/core/Toolbar";
import Typography from "@material-ui/core/Typography";
import Button from "@material-ui/core/Button";

import { useDispatch } from "react-redux";

import { logout } from "../store/reducers/authSlice";

const useStyles = makeStyles((theme) => ({
  root: {
    flexGrow: 1,
  },
  menuButton: {
    marginRight: theme.spacing(2),
  },
  title: {
    flexGrow: 1,
  },
  content: {
    textAlign: "center",
    marginTop: theme.spacing(10),
  },
}));

function MainPage() {
  const classes = useStyles();
  const dispatch = useDispatch();

  useEffect(() => {
    setTimeout(() => {
      window.location.replace("http://localhost:8080");
    }, 2000);
  }, []);

  return (
    <div className={classes.root}>
      <AppBar position="static">
        <Toolbar>
          <Typography className={classes.title} variant="h6">
            HMS Advanced 2.0
          </Typography>
          <Button
            className={classes.menuButton}
            color="inherit"
            onClick={() => dispatch(logout())}
          >
            Logout
          </Button>
        </Toolbar>
      </AppBar>
      <Typography className={classes.content} variant="h6">
        Awesome content is coming soon!
      </Typography>
    </div>
  );
}

export default MainPage;
