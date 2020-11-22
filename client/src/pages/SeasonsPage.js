import React, { useEffect, useState } from "react";
import { useDispatch } from "react-redux";

import { useHistory } from "react-router-dom";

import { makeStyles } from "@material-ui/core/styles";
import Typography from "@material-ui/core/Typography";
import Container from "@material-ui/core/Container";
import CircularProgress from "@material-ui/core/CircularProgress";
import Card from "@material-ui/core/Card";
import CardActions from "@material-ui/core/CardActions";
import Button from "@material-ui/core/Button";
import CardContent from "@material-ui/core/CardContent";
import TextField from "@material-ui/core/TextField";
import Grid from "@material-ui/core/Grid";

import FormLabel from "@material-ui/core/FormLabel";
import FormControl from "@material-ui/core/FormControl";
import FormGroup from "@material-ui/core/FormGroup";
import FormControlLabel from "@material-ui/core/FormControlLabel";
import FormHelperText from "@material-ui/core/FormHelperText";
import Checkbox from "@material-ui/core/Checkbox";

import api from "../config/api";
import { updateAlert } from "../store/reducers/alertSlice";

const useStyles = makeStyles((theme) => ({
  root: {
    minWidth: 275,
    marginTop: theme.spacing(3),
  },
  bullet: {
    display: "inline-block",
    margin: "0 2px",
    transform: "scale(0.8)",
  },
  title: {
    fontSize: 14,
  },
  loader: {
    textAlign: "center",
  },
  content: {
    textAlign: "center",
    marginTop: theme.spacing(10),
  },
  formControl: {
    marginTop: "35px",
    "&:nth-child(1)": {
      marginTop: "10px",
    },
  },
  button: {
    margin: "25px 0px 0px 0",
    width: "100px",
    display: "block",
  },
  deleteButton: {
    marginBottom: "10px",
  },
  top: {
    marginTop: "-20px",
  },
  grid: {
    marginTop: "20px",
  },
  textField: {
    marginRight: "30px",
    maxWidth: "140px",
  },
  textDayField: {
    marginRight: "30px",
    maxWidth: "120px",
  },
}));

function SeasonPage() {
  const dispatch = useDispatch();
  const classes = useStyles();
  const [seasons, setSeasons] = useState(null);
  const [hotels, setHotels] = useState(null);
  const [fields, setFields] = useState({});

  const days = [
    "Monday",
    "Tuesday",
    "Wednesday",
    "Thursday",
    "Friday",
    "Saturday",
    "Sunday",
  ];

  const getSeasons = () => {
    api.get("/manager/seasons").then((result) => {
      setSeasons(result.data);
    });
  };

  useEffect(() => {
    api.get("/hotels/").then((result) => {
      setHotels(result.data);
    });

    getSeasons();
  }, []);

  const fieldChange = (e) => {
    setFields({ ...fields, [e.target.name]: e.target.value });
  };

  const [checkboxes, setCheckboxes] = useState({});

  const handleChange = (e) => {
    const current = !!checkboxes[e.target.name];
    setCheckboxes({ ...checkboxes, [e.target.name]: !current });
  };

  const pickSelectedIds = () => {
    return Object.keys(checkboxes)
      .filter((current) => checkboxes[current])
      .join(",");
  };

  const getWeekdayPrices = () => {
    const prices = days.map((dayPrice) => fields[dayPrice]).join(",");
    return prices;
  };

  const isValid = () => {
    const requiredFields = [...days, "name", "startDate", "endDate"];

    const invalidEntry = requiredFields.find((field) => {
      return !fields[field];
    });

    return !invalidEntry && pickSelectedIds().length;
  };

  const createSeason = () => {
    const params = new URLSearchParams();
    params.append("hotelIds", pickSelectedIds());
    params.append("name", fields.name);
    params.append("startDate", fields.startDate);
    params.append("endDate", fields.endDate);
    params.append("weekdayPrice", getWeekdayPrices());
    api.post("/manager/seasons/addSeason", params).finally(() => {
      setFields({});
      setCheckboxes({});

      dispatch(
        updateAlert({
          target: "success",
          newValue: `Successfully created!`,
        })
      );

      getSeasons();
    });
  };

  const getTakesPlaceIns = (season) => {
    const { takesPlaceIns } = season;
    return takesPlaceIns.map((place) => place.hotel.name).join(", ");
  };

  const deleteSeason = (seasonId) => {
    api.post(`/manager/seasons/${seasonId}/delete`).then(() => {
      dispatch(
        updateAlert({
          target: "success",
          newValue: `Successfully deleted!`,
        })
      );

      getSeasons();
    });
  };

  return (
    <Container fixed>
      {seasons && hotels ? (
        <>
          <Typography variant="h3" className={classes.bookingName}>
            Seasons
          </Typography>
          <Card className={classes.root}>
            <CardContent>
              <FormControl className={classes.formControl}>
                <FormLabel component="legend">Basic settings</FormLabel>
                <Grid container className={classes.grid}>
                  <TextField
                    name="name"
                    label="Season name"
                    type="text"
                    defaultValue=""
                    placeholder="The Purge"
                    className={classes.textField}
                    InputLabelProps={{
                      shrink: true,
                    }}
                    value={fields.name}
                    onChange={fieldChange}
                  />
                  <TextField
                    name="startDate"
                    label="Start date"
                    type="date"
                    defaultValue=""
                    className={classes.textField}
                    InputLabelProps={{
                      shrink: true,
                    }}
                    value={fields.startDate}
                    onChange={fieldChange}
                  />
                  <TextField
                    name="endDate"
                    label="End date"
                    type="date"
                    defaultValue=""
                    className={classes.textField}
                    InputLabelProps={{
                      shrink: true,
                    }}
                    value={fields.endDate}
                    onChange={fieldChange}
                  />
                </Grid>
              </FormControl>
              <FormControl className={classes.formControl}>
                <FormLabel component="legend">Pick weekday prices</FormLabel>
                <Grid container className={classes.grid}>
                  {days.map((day) => {
                    return (
                      <TextField
                        key={day}
                        name={day}
                        label={day}
                        type="number"
                        defaultValue=""
                        placeholder="0"
                        className={classes.textDayField}
                        InputLabelProps={{
                          shrink: true,
                        }}
                        value={fields.day}
                        onChange={fieldChange}
                      />
                    );
                  })}
                </Grid>
              </FormControl>
              <FormControl component="fieldset" className={classes.formControl}>
                <FormLabel component="legend">Choose hotels</FormLabel>
                <FormGroup>
                  {hotels.map((hotel) => (
                    <FormControlLabel
                      control={
                        <Checkbox
                          checked={checkboxes[hotel.id]}
                          onChange={handleChange}
                          name={hotel.id}
                        />
                      }
                      label={hotel.name}
                    />
                  ))}
                </FormGroup>
              </FormControl>
              <Button
                className={classes.button}
                type="submit"
                variant="contained"
                color="primary"
                disabled={!isValid()}
                onClick={createSeason}
              >
                Create
              </Button>
            </CardContent>
          </Card>
          {seasons.map((season) => (
            <>
              <Card className={classes.root}>
                <CardContent>
                  <Typography
                    className={classes.title}
                    color="textSecondary"
                    gutterBottom
                  >
                    {season.startDate} - {season.endDate}
                  </Typography>
                  <Typography variant="h5" component="h2">
                    {season.seasonName}
                  </Typography>
                  <Typography className={classes.pos} color="textSecondary">
                    {getTakesPlaceIns(season)}
                  </Typography>
                  {/* <Typography variant="body2" component="p"> */}
                  {/*   {getTakesPlaceIns(season)} */}
                  {/* </Typography> */}
                </CardContent>
                <CardActions>
                  <Button
                    variant="outlined"
                    color="secondary"
                    className={classes.deleteButton}
                    onClick={() => deleteSeason(season.id)}
                  >
                    Delete
                  </Button>
                </CardActions>
              </Card>
            </>
          ))}
        </>
      ) : (
        <div className={classes.loader}>
          <CircularProgress />
        </div>
      )}
    </Container>
  );
}

export default SeasonPage;
