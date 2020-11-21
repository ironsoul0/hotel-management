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
import RadioGroup from "@material-ui/core/RadioGroup";
import FormControlLabel from "@material-ui/core/FormControlLabel";
import FormHelperText from "@material-ui/core/FormHelperText";
import Checkbox from "@material-ui/core/Checkbox";
import Radio from "@material-ui/core/Radio";

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

function CreateReservationPage() {
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
    if (e.target.name === "hotelId") {
      setFields({ ...fields, room_typeId: "" });
    }

    setFields({ ...fields, [e.target.name]: e.target.value });
  };

  const getRoomTypes = (hotelId) => {
    const id = parseInt(hotelId);
    const target = hotels.find((hotel) => hotel.id === id);

    return target.room_types.map((room_type) => ({
      id: room_type.room_type_id,
      name: room_type.name,
    }));
  };

  const getWeekdayPrices = () => {
    const prices = days.map((dayPrice) => fields[dayPrice]).join(",");
    return prices;
  };

  const isValid = () => {
    const requiredFields = [
      "userName",
      "hotelId",
      "checkInDate",
      "checkOutDate",
      "prePaidPrice",
      "roomCount",
      "room_typeId",
    ];

    const invalidEntry = requiredFields.find((field) => {
      return !fields[field];
    });

    return !invalidEntry;
  };

  const createReservation = () => {
    const params = new URLSearchParams();

    params.append("userName", fields.userName);
    params.append("hotelId", parseInt(fields.hotelId));
    params.append("checkInDate", fields.checkInDate);
    params.append("checkOutDate", fields.checkOutDate);
    params.append("prePaidPrice", parseInt(fields.prePaidPrice));
    params.append("roomCount", parseInt(fields.roomCount));
    params.append("room_typeId", parseInt(fields.room_typeId));

    api
      .post("/desk/allReserves/create", params)
      .then(() => {
        setFields({});

        dispatch(
          updateAlert({
            target: "success",
            newValue: `Successfully created!`,
          })
        );
      })
      .catch(() => {
        dispatch(
          updateAlert({
            target: "error",
            newValue: `No such user`,
          })
        );
      });
  };

  return (
    <Container fixed>
      {seasons && hotels ? (
        <>
          <Typography variant="h3" className={classes.bookingName}>
            Create reservation
          </Typography>
          <Card className={classes.root}>
            <CardContent>
              <FormControl className={classes.formControl}>
                <FormLabel component="legend">Basic settings</FormLabel>
                <Grid container className={classes.grid}>
                  <TextField
                    name="userName"
                    label="Username"
                    type="text"
                    defaultValue=""
                    placeholder="ironsoul"
                    className={classes.textField}
                    InputLabelProps={{
                      shrink: true,
                    }}
                    value={fields.userName}
                    onChange={fieldChange}
                  />
                  <TextField
                    name="prePaidPrice"
                    label="Money to pay"
                    type="number"
                    defaultValue=""
                    placeholder="5000"
                    className={classes.textField}
                    InputLabelProps={{
                      shrink: true,
                    }}
                    value={fields.prePaidPrice}
                    onChange={fieldChange}
                  />
                  <TextField
                    name="roomCount"
                    label="Number of rooms"
                    type="number"
                    defaultValue=""
                    placeholder="0"
                    className={classes.textField}
                    InputLabelProps={{
                      shrink: true,
                    }}
                    value={fields.roomCount}
                    onChange={fieldChange}
                  />
                  <TextField
                    name="checkInDate"
                    label="Check-in date"
                    type="date"
                    defaultValue=""
                    className={classes.textField}
                    InputLabelProps={{
                      shrink: true,
                    }}
                    value={fields.checkInDate}
                    onChange={fieldChange}
                  />
                  <TextField
                    name="checkOutDate"
                    label="Check-out date"
                    type="date"
                    defaultValue=""
                    className={classes.textField}
                    InputLabelProps={{
                      shrink: true,
                    }}
                    value={fields.checkOutDate}
                    onChange={fieldChange}
                  />
                </Grid>
              </FormControl>
              <br />
              <FormControl component="fieldset" className={classes.formControl}>
                <FormLabel component="legend">Choose a hotel</FormLabel>
                <RadioGroup
                  name="hotelId"
                  value={String(fields.hotelId)}
                  onChange={fieldChange}
                >
                  {hotels.map((hotel) => (
                    <FormControlLabel
                      value={String(hotel.id)}
                      control={<Radio />}
                      label={hotel.name}
                    />
                  ))}
                </RadioGroup>
              </FormControl>
              <br />
              {fields.hotelId && (
                <FormControl
                  component="fieldset"
                  className={classes.formControl}
                >
                  <FormLabel component="legend">Choose a room type</FormLabel>
                  <RadioGroup
                    name="room_typeId"
                    value={String(fields.room_typeId)}
                    onChange={fieldChange}
                  >
                    {getRoomTypes(fields.hotelId).map((info) => (
                      <FormControlLabel
                        value={String(info.id)}
                        control={<Radio />}
                        label={info.name}
                      />
                    ))}
                  </RadioGroup>
                </FormControl>
              )}
              <Button
                className={classes.button}
                type="submit"
                variant="contained"
                color="primary"
                disabled={!isValid()}
                onClick={createReservation}
              >
                Create
              </Button>
            </CardContent>
          </Card>
        </>
      ) : (
        <div className={classes.loader}>
          <CircularProgress />
        </div>
      )}
    </Container>
  );
}

export default CreateReservationPage;
