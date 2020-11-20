import React, { useState } from "react";

import { useDispatch } from "react-redux";

import MenuItem from "@material-ui/core/MenuItem";
import Avatar from "@material-ui/core/Avatar";
import Button from "@material-ui/core/Button";
import CssBaseline from "@material-ui/core/CssBaseline";
import TextField from "@material-ui/core/TextField";
import FormControlLabel from "@material-ui/core/FormControlLabel";
import Checkbox from "@material-ui/core/Checkbox";
import Link from "@material-ui/core/Link";
import Grid from "@material-ui/core/Grid";
import Box from "@material-ui/core/Box";
import LockOutlinedIcon from "@material-ui/icons/LockOutlined";
import Typography from "@material-ui/core/Typography";
import { makeStyles } from "@material-ui/core/styles";
import Container from "@material-ui/core/Container";
import Snackbar from "@material-ui/core/Snackbar";
import MuiAlert from "@material-ui/lab/Alert";
import MuiPhoneNumber from "material-ui-phone-number";

import api from "../config/api";
import { updateAlert } from "../store/reducers/alertSlice";

function Copyright() {
  return (
    <Typography
      variant="body2"
      color="textSecondary"
      align="center"
      style={{ marginBottom: "20px" }}
    >
      {"Copyright © "}
      <Link color="inherit" href="https://ironsoul.me/">
        HMS
      </Link>{" "}
      {new Date().getFullYear()}
      {"."}
    </Typography>
  );
}

const roleTypes = [
  {
    key: "Manager",
    value: "admin",
  },
  {
    key: "Desk clerk",
    value: "mod",
  },
];

const useStyles = makeStyles((theme) => ({
  paper: {
    marginTop: theme.spacing(8),
    display: "flex",
    flexDirection: "column",
    alignItems: "center",
  },
  avatar: {
    margin: theme.spacing(1),
    backgroundColor: theme.palette.secondary.main,
  },
  form: {
    width: "100%", // Fix IE 11 issue.
    marginTop: theme.spacing(1),
  },
  submit: {
    margin: theme.spacing(3, 0, 2),
  },
}));

export default function SignUp({ toggle }) {
  const classes = useStyles();
  const dispatch = useDispatch();

  const [fields, setFields] = useState({});
  const fieldChange = (e) => {
    setFields({ ...fields, [e.target.name]: e.target.value });
  };

  const isValid = () => {
    const requiredFields = [
      "username",
      "email",
      "name",
      "surname",
      "password",
      "roleType",
    ];

    const invalidEntry = requiredFields.find((field) => {
      return !fields[field];
    });

    return !invalidEntry;
  };

  const submitForm = (e) => {
    e.preventDefault();
    api
      .post("/auth/signup", { ...fields, role: [fields.roleType] })
      .then(() => {
        dispatch(
          updateAlert({
            target: "success",
            newValue: "Successfully registered!",
          })
        );
        setTimeout(() => {
          toggle();
        }, 2000);
      })
      .catch((err) => {
        const errorMessage = err.response.data.message;
        dispatch(updateAlert({ target: "error", newValue: errorMessage }));
      });
  };

  return (
    <Container component="main" maxWidth="xs">
      <CssBaseline />
      <div className={classes.paper}>
        <Avatar className={classes.avatar}>
          <LockOutlinedIcon />
        </Avatar>
        <Typography component="h1" variant="h5">
          Sign up
        </Typography>
        <form className={classes.form} noValidate>
          <TextField
            variant="outlined"
            margin="normal"
            required
            fullWidth
            id="username"
            label="Username"
            name="username"
            autoComplete="username"
            autoFocus
            value={fields["username"]}
            onChange={fieldChange}
          />
          <TextField
            variant="outlined"
            margin="normal"
            required
            fullWidth
            name="email"
            label="Email"
            type="email"
            id="email"
            value={fields["email"]}
            onChange={fieldChange}
          />
          <TextField
            variant="outlined"
            margin="normal"
            required
            fullWidth
            id="name"
            label="First name"
            name="name"
            autoComplete="name"
            autoFocus
            value={fields["name"]}
            onChange={fieldChange}
          />
          <TextField
            variant="outlined"
            margin="normal"
            required
            fullWidth
            name="surname"
            label="Last name"
            id="surname"
            value={fields["surname"]}
            onChange={fieldChange}
          />
          <TextField
            variant="outlined"
            margin="normal"
            required
            fullWidth
            name="password"
            label="Password"
            type="password"
            id="password"
            value={fields["password"]}
            onChange={fieldChange}
          />
          <TextField
            variant="outlined"
            margin="normal"
            required
            fullWidth
            id="roleType"
            select
            name="roleType"
            label="Role type"
            value={fields["roleType"]}
            onChange={fieldChange}
          >
            {roleTypes.map(({ key, value }) => (
              <MenuItem key={value} value={value}>
                {key}
              </MenuItem>
            ))}
          </TextField>
          <Button
            type="submit"
            fullWidth
            variant="contained"
            color="primary"
            className={classes.submit}
            disabled={!isValid()}
            onClick={submitForm}
          >
            Sign Up
          </Button>
          <Grid container>
            <Grid item>
              <Link href="#" variant="body2" onClick={toggle}>
                {"Already have an account? Sign In"}
              </Link>
            </Grid>
          </Grid>
        </form>
      </div>
      <Box mt={8}>
        <Copyright />
      </Box>
    </Container>
  );
}
