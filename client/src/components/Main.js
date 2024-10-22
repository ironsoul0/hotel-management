import React, { useEffect } from "react";

import { makeStyles } from "@material-ui/core/styles";
import AppBar from "@material-ui/core/AppBar";
import Toolbar from "@material-ui/core/Toolbar";
import Typography from "@material-ui/core/Typography";
import Button from "@material-ui/core/Button";

import { useDispatch, useSelector } from "react-redux";
import { useHistory, useLocation } from "react-router-dom";

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
    marginBottom: theme.spacing(7),
  },
}));

function Main({ children }) {
  const classes = useStyles();
  const dispatch = useDispatch();
  const auth = useSelector((state) => state.auth);

  const history = useHistory();
  const location = useLocation();

  const isAdminPanel = location.pathname.includes("vpn-access");

  const capitalize = (string) => {
    return string.substr(0, 1).toUpperCase() + string.substr(1);
  };

  console.log(auth);

  return (
    <div className={classes.root}>
      <AppBar
        position="static"
        className={classes.appBar}
        color={isAdminPanel ? "secondary" : "primary"}
      >
        <Toolbar>
          <Typography
            onClick={() => history.push("/")}
            className={classes.title}
            variant="h6"
          >
            Hotel Management System
            {auth.role && auth.role !== "user" && ` - ${capitalize(auth.role)}`}
          </Typography>
          <Button
            className={classes.menuButton}
            color="inherit"
            onClick={() => history.push("/about")}
          >
            About
          </Button>
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
          {!auth.role && !isAdminPanel && (
            <Button
              className={classes.menuButton}
              color="inherit"
              onClick={() => history.push("/authorize")}
            >
              Authorize
            </Button>
          )}
          {auth.role && (
            <Button
              className={classes.menuButton}
              color="inherit"
              onClick={() => dispatch(logout())}
            >
              Logout
            </Button>
          )}
        </Toolbar>
      </AppBar>
      {children}
    </div>
  );
}

export default Main;
