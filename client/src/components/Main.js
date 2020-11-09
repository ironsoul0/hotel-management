import React, { useEffect } from "react";

import { makeStyles } from "@material-ui/core/styles";
import AppBar from "@material-ui/core/AppBar";
import Toolbar from "@material-ui/core/Toolbar";
import Typography from "@material-ui/core/Typography";
import Button from "@material-ui/core/Button";

import { useDispatch } from "react-redux";
import { useHistory } from "react-router-dom";

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
    "&:hover": {
      cursor: "pointer",
    },
  },
  appBar: {
    marginBottom: theme.spacing(10),
  },
}));

function Main({ children }) {
  const classes = useStyles();
  const dispatch = useDispatch();

  const history = useHistory();

  return (
    <div className={classes.root}>
      <AppBar position="static" className={classes.appBar}>
        <Toolbar>
          <Typography
            onClick={() => history.push("/")}
            className={classes.title}
            variant="h6"
          >
            HMS Advanced 2.0
          </Typography>
          <Button
            className={classes.menuButton}
            color="inherit"
            onClick={() => history.push("/profile")}
          >
            Profile
          </Button>
          <Button
            className={classes.menuButton}
            color="inherit"
            onClick={() => dispatch(logout())}
          >
            Logout
          </Button>
        </Toolbar>
      </AppBar>
      {children}
    </div>
  );
}

export default Main;
