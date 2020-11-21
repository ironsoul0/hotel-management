import React, { useEffect } from "react";

import { makeStyles } from "@material-ui/core/styles";
import AppBar from "@material-ui/core/AppBar";
import Toolbar from "@material-ui/core/Toolbar";
import Typography from "@material-ui/core/Typography";
import Button from "@material-ui/core/Button";

import { useDispatch, useSelector } from "react-redux";
import { useHistory } from "react-router-dom";

import { logout } from "../store/reducers/authSlice";

const useStyles = makeStyles((theme) => ({
  root: {
    flexGrow: 1,
    paddingBottom: theme.spacing(5),
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
  const auth = useSelector((state) => state.auth);

  const history = useHistory();

  const capitalize = (string) => {
    return string.substr(0, 1).toUpperCase() + string.substr(1);
  };

  return (
    <div className={classes.root}>
      <AppBar position="static" className={classes.appBar}>
        <Toolbar>
          <Typography
            onClick={() => history.push("/")}
            className={classes.title}
            variant="h6"
          >
            HMS 2.0 {auth.role !== "user" ? `- ${capitalize(auth.role)}` : ""}
          </Typography>
          {auth.role === "clerk" && (
            <Button
              className={classes.menuButton}
              color="inherit"
              onClick={() => history.push("/create")}
            >
              Create reservation
            </Button>
          )}
          {auth.role === "manager" && (
            <Button
              className={classes.menuButton}
              color="inherit"
              onClick={() => history.push("/seasons")}
            >
              Seasons
            </Button>
          )}
          {auth.role === "user" && (
            <Button
              className={classes.menuButton}
              color="inherit"
              onClick={() => history.push("/profile")}
            >
              Profile
            </Button>
          )}
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
