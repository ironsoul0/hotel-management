import React, { useEffect, useState } from "react";

import { useHistory } from "react-router-dom";

import { makeStyles } from "@material-ui/core/styles";
import Typography from "@material-ui/core/Typography";
import Container from "@material-ui/core/Container";
import CircularProgress from "@material-ui/core/CircularProgress";
import Card from "@material-ui/core/Card";
import CardActions from "@material-ui/core/CardActions";
import CardContent from "@material-ui/core/CardContent";
import Button from "@material-ui/core/Button";

import { useDispatch } from "react-redux";
import { updateAlert } from "../store/reducers/alertSlice";

import api from "../config/api";

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
  button: {
    marginBottom: 10,
  },
  nothing: {
    marginTop: 10,
    fontSize: 16,
  },
}));

function ClerkPage() {
  const hotelId = parseInt(localStorage.getItem("hotelId"));
  const classes = useStyles();
  const dispatch = useDispatch();
  const [reservations, setReservations] = useState(null);
  const history = useHistory();
  const [hotelName, setHotelName] = useState("");

  const getReservations = () => {
    api.get(`/desk/allReserves?hotelId=${hotelId}`).then((result) => {
      setReservations(result.data);
    });
  };

  useEffect(() => {
    getReservations();

    api.get("/hotels/").then((result) => {
      const target = result.data.find((hotel) => hotel.id === hotelId);
      setHotelName(target.name);
    });
  }, []);

  const approveReservation = (id) => {
    const params = new URLSearchParams();
    params.append("hotelId", hotelId);

    api.post(`/desk/allReserves/${id}/approve`, params).then(({ data }) => {
      if (data === -1) {
        dispatch(
          updateAlert({
            target: "error",
            newValue: "Impossible to approve",
          })
        );
      } else {
        getReservations();
        dispatch(
          updateAlert({
            target: "success",
            newValue: "Approved!",
          })
        );
      }
    });
  };

  const deleteReservation = (id) => {
    const params = new URLSearchParams();
    params.append("hotelId", hotelId);

    api.post(`/desk/allReserves/${id}/delete`, params).then(() => {
      getReservations();
      dispatch(
        updateAlert({
          target: "success",
          newValue: "Deleted!",
        })
      );
    });
  };

  return (
    <Container fixed>
      {reservations && hotelName ? (
        <>
          <Typography variant="h3" className={classes.bookingName}>
            {hotelName}
          </Typography>
          {reservations.length === 0 && (
            <Typography className={classes.nothing}>
              Nothing to display here
            </Typography>
          )}
          {reservations.map((reservation) => (
            <>
              <Card className={classes.root}>
                <CardContent>
                  <Typography
                    className={classes.title}
                    color="textSecondary"
                    gutterBottom
                  >
                    {reservation.prepaid_price} ₸
                  </Typography>
                  <Typography variant="h5" component="h2">
                    {reservation.userFullName}
                  </Typography>
                  <Typography className={classes.pos} color="textSecondary">
                    {reservation.room_count} room(s) for{" "}
                    {reservation.userUsername}
                  </Typography>
                  <Typography variant="body2" component="p">
                    {reservation.checkinDateToString} -{" "}
                    {reservation.checkoutDateToString}
                  </Typography>
                </CardContent>
                <CardActions>
                  <Button
                    variant="outlined"
                    className={classes.button}
                    onClick={() => history.push(`/edit/${reservation.id}`)}
                  >
                    Edit
                  </Button>
                  <Button
                    variant="outlined"
                    color="secondary"
                    className={classes.button}
                    onClick={() => deleteReservation(reservation.id)}
                  >
                    Delete
                  </Button>
                  <Button
                    variant="outlined"
                    color="primary"
                    className={classes.button}
                    disabled={reservation.approved}
                    onClick={() => approveReservation(reservation.id)}
                  >
                    Approve
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

export default ClerkPage;
