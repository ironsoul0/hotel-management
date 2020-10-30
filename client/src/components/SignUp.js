import React, { useState } from "react";
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

function Copyright() {
  return (
    <Typography variant="body2" color="textSecondary" align="center">
      {"Copyright © "}
      <Link color="inherit" href="https://ironsoul.me/">
        LoveDogs
      </Link>{" "}
      {new Date().getFullYear()}
      {"."}
    </Typography>
  );
}

function Alert(props) {
  return <MuiAlert elevation={6} variant="filled" {...props} />;
}

const idTypes = ["Passport", "National ID", "Drived license"];

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

  const [fields, setFields] = useState({});
  const [success, setSuccess] = useState("");
  const [fail, setFail] = useState("");

  const handleClose = (event, reason) => {
    if (reason === "clickaway") {
      return;
    }

    setSuccess("");
  };

  const fieldChange = (e) => {
    setFields({ ...fields, [e.target.name]: e.target.value });
  };

  const mobilePhoneChange = (e) => {
    setFields({ ...fields, mobilePhone: e });
  };

  const homePhoneChange = (e) => {
    setFields({ ...fields, homePhone: e });
  };

  const isValid = () => {
    const requiredFields = [
      "username",
      "email",
      "password",
      "mobilePhone",
      "homePhone",
      "identificationType",
      "identificationNumber",
    ];

    const invalidEntry = requiredFields.find((field) => {
      return !fields[field];
    });

    return !invalidEntry;
  };

  const submitForm = (e) => {
    e.preventDefault();
    api
      .post("/auth/signup", fields)
      .then(() => {
        setSuccess("Sexfully registered!");
        setTimeout(() => {
          toggle();
        }, 2000);
      })
      .catch((err) => {
        const errorMessage = err.response.data.message;
        setFail(errorMessage);
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
            name="password"
            label="Password"
            type="password"
            id="password"
            value={fields["password"]}
            onChange={fieldChange}
          />
          <MuiPhoneNumber
            variant="outlined"
            margin="normal"
            required
            name="mobilePhone"
            label="Mobile Phone"
            disableDropdown
            fullWidth
            required
            value={fields["mobilePhone"]}
            onChange={mobilePhoneChange}
          />
          <MuiPhoneNumber
            variant="outlined"
            margin="normal"
            required
            name="homePhone"
            label="Home Phone"
            disableDropdown
            fullWidth
            required
            value={fields["homePhone"]}
            onChange={homePhoneChange}
          />
          <TextField
            variant="outlined"
            margin="normal"
            required
            fullWidth
            id="identificationType"
            select
            name="identificationType"
            label="Identification type"
            value={fields["identificationType"]}
            onChange={fieldChange}
          >
            {idTypes.map((option) => (
              <MenuItem key={option} value={option}>
                {option}
              </MenuItem>
            ))}
          </TextField>
          <TextField
            variant="outlined"
            margin="normal"
            required
            fullWidth
            name="identificationNumber"
            label="Identification number"
            type="text"
            id="identificationNumber"
            value={fields["identificationNumber"]}
            onChange={fieldChange}
          />
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
      <Snackbar open={success} autoHideDuration={3000} onClose={handleClose}>
        <Alert onClose={handleClose} severity="success">
          {success}
        </Alert>
      </Snackbar>
      <Snackbar open={fail} autoHideDuration={3000} onClose={handleClose}>
        <Alert onClose={handleClose} severity="error">
          {fail}
        </Alert>
      </Snackbar>
    </Container>
  );
}
