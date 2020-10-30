import React from "react";
import { useSelector } from "react-redux";
import {
  Button,
  Container,
  AppBar,
  Toolbar,
  IconButton,
  MenuIcon,
  Typography,
} from "@material-ui/core";
import { makeStyles } from "@material-ui/core/styles";

import LoginPage from "./pages/LoginPage";

function App() {
  const token = useSelector((state) => state.auth.token);

  return token ? <div>I LOVE FUCKING DOGS!</div> : <LoginPage />;
}

export default App;
