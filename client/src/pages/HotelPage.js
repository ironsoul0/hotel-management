import React, { useEffect, useState } from "react";

import { makeStyles } from "@material-ui/core/styles";
import Typography from "@material-ui/core/Typography";
import Container from "@material-ui/core/Container";
import CircularProgress from "@material-ui/core/CircularProgress";
import Card from "@material-ui/core/Card";
import CardActions from "@material-ui/core/CardActions";
import CardContent from "@material-ui/core/CardContent";
import TextField from "@material-ui/core/TextField";
import Button from "@material-ui/core/Button";
import Grid from "@material-ui/core/Grid";

import Radio from "@material-ui/core/Radio";
import RadioGroup from "@material-ui/core/RadioGroup";
import FormControlLabel from "@material-ui/core/FormControlLabel";
import FormControl from "@material-ui/core/FormControl";
import FormLabel from "@material-ui/core/FormLabel";
import CardMedia from "@material-ui/core/CardMedia";

import Dialog from "@material-ui/core/Dialog";
import DialogActions from "@material-ui/core/DialogActions";
import DialogContent from "@material-ui/core/DialogContent";
import DialogContentText from "@material-ui/core/DialogContentText";
import DialogTitle from "@material-ui/core/DialogTitle";

import { useParams } from "react-router-dom";

import { useDispatch } from "react-redux";

import api, { base } from "../config/api";
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
  pos: {
    marginBottom: 12,
  },
  loader: {
    textAlign: "center",
  },
  content: {
    textAlign: "center",
    marginTop: theme.spacing(10),
  },
  formControl: {
    margin: theme.spacing(3),
  },
  button: {
    margin: theme.spacing(1, 1, 0, 0),
  },
  grid: {
    marginTop: "20px",
  },
  textField: {
    marginRight: "15px",
    marginBottom: "20px",
  },
  media: {
    height: 0,
    width: "50%",
    paddingTop: "25.25%", // 16:9
    borderRadius: "15px",
    opacity: "0.9",
    margin: "20px 0",
  },
}));

function HotelPage() {
  const dispatch = useDispatch();
  const classes = useStyles();
  const [hotel, setHotel] = useState(null);
  const { id } = useParams();
  const [fields, setFields] = useState({});

  const fieldChange = (e) => {
    setFields({ ...fields, [e.target.name]: e.target.value });
  };

  useEffect(() => {
    api.get("/hotels/").then((result) => {
      const hotels = result.data;
      const target = hotels.find((current) => {
        return current.id == id;
      });
      setHotel(target);
    });
  }, []);

  const [open, setOpen] = React.useState(false);
  const [value, setValue] = React.useState("");
  const [error, setError] = React.useState(false);
  const [price, setPrice] = React.useState(0);

  const handleClose = () => {
    setOpen(false);
  };

  const handleRadioChange = (event) => {
    setValue(event.target.value);
  };

  const handleSubmit = async (event) => {
    event.preventDefault();

    const params = new URLSearchParams();
    params.append("roomtype", parseInt(value));
    params.append("myDate", fields["check-in"]);
    params.append("myDate2", fields["check-out"]);

    const { data } = await api.post("/hotels/book-room-price", params);
    setPrice(data);
    setOpen(true);
  };

  const onConfirmPay = () => {
    const params = new URLSearchParams();
    params.append("roomtype", parseInt(value));
    params.append("myDate", fields["check-in"]);
    params.append("myDate2", fields["check-out"]);

    api.post("/hotels/book-room", params).then(() => {
      setFields({});
      setValue("");

      dispatch(
        updateAlert({
          target: "success",
          newValue: `Successfully booked!`,
        })
      );

      setOpen(false);
    });
  };

  const isValid = () => {
    if (!value) return false;
    if (!fields["check-in"] || !fields["check-out"]) return false;
    return true;
  };

  const getMedia = (hotelName) => {
    const src = base + `/api/hotels/image/${hotelName}`;
    return src;
  };

  return (
    <Container fixed>
      {hotel ? (
        <form onSubmit={handleSubmit}>
          <Card className={classes.root}>
            <CardContent>
              <Typography
                className={classes.title}
                color="textSecondary"
                gutterBottom
              >
                {hotel.address}
              </Typography>
              <Typography variant="h4" component="h2">
                {hotel.name}
              </Typography>
              <CardMedia
                className={classes.media}
                image={getMedia(hotel.name)}
                title="Paella dish"
              />
              <Typography className={classes.pos} color="textSecondary">
                {hotel.features}
              </Typography>
              <Typography variant="body2" component="p">
                {hotel.description}
              </Typography>
            </CardContent>
            <FormControl
              component="fieldset"
              error={error}
              className={classes.formControl}
            >
              <FormLabel component="legend">
                Please choose the room type
              </FormLabel>
              <RadioGroup
                aria-label="quiz"
                name="quiz"
                value={value}
                onChange={handleRadioChange}
              >
                {hotel.room_types.map((room_type) => {
                  return (
                    <FormControlLabel
                      value={String(room_type.room_type_id)}
                      control={<Radio />}
                      label={room_type.name}
                    />
                  );
                })}
                {/* <FormControlLabel */}
                {/*   value="best" */}
                {/*   control={<Radio />} */}
                {/*   label="The best!" */}
                {/* /> */}
                {/* <FormControlLabel */}
                {/*   value="worst" */}
                {/*   control={<Radio />} */}
                {/*   label="The worst." */}
                {/* /> */}
              </RadioGroup>
              <Grid container className={classes.grid}>
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
              </Grid>
              <Button
                type="submit"
                variant="outlined"
                color="primary"
                className={classes.button}
                disabled={!isValid()}
                onClick={handleSubmit}
              >
                Book
              </Button>
            </FormControl>
          </Card>
        </form>
      ) : (
        <div className={classes.loader}>
          <CircularProgress />
        </div>
      )}
      <Dialog
        open={open}
        onClose={handleClose}
        aria-labelledby="alert-dialog-title"
        aria-describedby="alert-dialog-description"
      >
        <DialogTitle id="alert-dialog-title">{"Are you sure?"}</DialogTitle>
        <DialogContent>
          <DialogContentText id="alert-dialog-description">
            This hotel is extremely good because it is full of tigers. Are you
            sure you want to pay {price} tenge?
          </DialogContentText>
        </DialogContent>
        <DialogActions>
          <Button onClick={handleClose} color="primary">
            Disagree
          </Button>
          <Button onClick={onConfirmPay} color="primary" autoFocus>
            Agree
          </Button>
        </DialogActions>
      </Dialog>
    </Container>
  );
}

export default HotelPage;
