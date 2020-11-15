import React, { useEffect, useState } from "react";
import "date-fns";

import { makeStyles } from "@material-ui/core/styles";
import TextField from "@material-ui/core/TextField";
import Typography from "@material-ui/core/Typography";
import Button from "@material-ui/core/Button";
import Grid from "@material-ui/core/Grid";

import { useDispatch } from "react-redux";

import api from "../config/api";
import { updateAlert } from "../store/reducers/alertSlice";

const useStyles = makeStyles((theme) => ({
  heading: {
    marginBottom: theme.spacing(3),
  },
  loader: {
    textAlign: "center",
  },
  content: {
    textAlign: "center",
    marginTop: theme.spacing(10),
  },
  textField: {
    marginRight: theme.spacing(5),
  },
  container: {
    marginBottom: theme.spacing(8),
  },
}));

function SearchHotels({ setHotels }) {
  const dispatch = useDispatch();
  const classes = useStyles();
  const [fields, setFields] = useState({});

  const fieldChange = (e) => {
    setFields({ ...fields, [e.target.name]: e.target.value });
  };

  const isValid = () => {
    const requiredFields = ["check-in", "check-out", "location", "people"];

    const invalidEntry = requiredFields.find((field) => {
      return !fields[field];
    });

    return !invalidEntry;
  };

  const submitForm = (e) => {
    e.preventDefault();
    const { location } = fields;

    api.get(`/hotels/${location}`).then((result) => {
      setHotels(result.data);
      dispatch(
        updateAlert({
          target: "info",
          newValue: `${result.data.length} hotel(s) found`,
        })
      );
    });
  };

  return (
    <div className={classes.container}>
      <Typography variant="h3" className={classes.heading}>
        Search
      </Typography>
      <Grid container>
        <TextField
          name="check-in"
          id="check-in"
          label="Check in"
          type="date"
          defaultValue=""
          className={classes.textField}
          InputLabelProps={{
            shrink: true,
          }}
          onChange={fieldChange}
        />
        <TextField
          name="check-out"
          id="check-out"
          label="Check out"
          type="date"
          defaultValue=""
          className={classes.textField}
          InputLabelProps={{
            shrink: true,
          }}
          onChange={fieldChange}
        />
        <TextField
          className={classes.textField}
          name="location"
          label="Location"
          type="text"
          id="location"
          onChange={fieldChange}
        />
        <TextField
          className={classes.textField}
          name="people"
          label="Number of people"
          type="number"
          id="people"
          onChange={fieldChange}
        />
        <Button
          type="submit"
          variant="outlined"
          color="primary"
          className={classes.search}
          disabled={!isValid()}
          onClick={submitForm}
        >
          Search
        </Button>
      </Grid>
    </div>
  );
}

export default SearchHotels;
