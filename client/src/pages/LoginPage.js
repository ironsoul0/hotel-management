import React, { useState } from "react";

import SignIn from "../components/SignIn";
import SignUp from "../components/SignUp";

import { makeStyles } from "@material-ui/core/styles";
import AppBar from "@material-ui/core/AppBar";
import Toolbar from "@material-ui/core/Toolbar";
import Typography from "@material-ui/core/Typography";
import Button from "@material-ui/core/Button";
import IconButton from "@material-ui/core/IconButton";
import MenuIcon from "@material-ui/icons/Menu";

function LoginPage() {
  const [login, setLogin] = useState(true);

  return (
    <>
      <AppBar position="static">
        <Toolbar>
          <Typography variant="h6">Welcome to HMS</Typography>
        </Toolbar>
      </AppBar>
      {login ? (
        <SignIn toggle={() => setLogin(false)} />
      ) : (
        <SignUp toggle={() => setLogin(true)} />
      )}
    </>
  );
}

export default LoginPage;
