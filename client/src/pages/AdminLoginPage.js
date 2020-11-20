import React, { useState } from "react";

import AdminSignIn from "../components/AdminSignIn";
import AdminSignUp from "../components/AdminSignUp";

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
      <AppBar position="static" color="secondary">
        <Toolbar>
          <Typography variant="h6">Secret admin panel</Typography>
        </Toolbar>
      </AppBar>
      {login ? (
        <AdminSignIn toggle={() => setLogin(false)} />
      ) : (
        <AdminSignUp toggle={() => setLogin(true)} />
      )}
    </>
  );
}

export default LoginPage;
