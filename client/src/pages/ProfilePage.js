import React, { useEffect, useState } from "react";

import { makeStyles } from "@material-ui/core/styles";
import Typography from "@material-ui/core/Typography";
import CircularProgress from "@material-ui/core/CircularProgress";
import Container from "@material-ui/core/Container";
import Card from "@material-ui/core/Card";
import CardContent from "@material-ui/core/CardContent";
import Grid from "@material-ui/core/Grid";
import Button from "@material-ui/core/Button";

import { useDispatch } from "react-redux";

import { updateAlert } from "../store/reducers/alertSlice";
import api, { base } from "../config/api";

const useStyles = makeStyles((theme) => ({
  loader: {
    textAlign: "center",
  },
  bookingName: {
    textTransform: "capitalize",
  },
  header: {
    marginBottom: theme.spacing(7),
  },
  bold: {
    fontWeight: "bold",
  },
  booking: {
    marginBottom: theme.spacing(5),
    "&:last-of-type": {
      marginBottom: theme.spacing(2),
    },
  },
  card: {
    marginBottom: "40px",
  },
  button: {
    marginRight: "20px",
  },
}));

function ProfilePage() {
  const dispatch = useDispatch();
  const classes = useStyles();
  const [reservations, setReservations] = useState(null);

  const getReservations = () => {
    api.get("/profile/reservations").then((result) => {
      setReservations(result.data);
    });
  };

  useEffect(() => {
    getReservations();
  }, []);

  const openReservation = (id) => {
    window.open(`${base}/generate-pdf/${id}`, "_blank");
  };

  const deleteReservation = async (id) => {
    await api.get(`/profile/delete-book/${id}`);
    dispatch(
      updateAlert({
        target: "success",
        newValue: "Deleted!",
      })
    );
    getReservations();
  };

  const bookingTypes = ["current", "past", "upcoming"];

  return (
    <Container fixed>
      {reservations ? (
        <>
          <Typography variant="h3" className={classes.header}>
            My bookings
          </Typography>
          {bookingTypes.map((target) => {
            return (
              <Card className={classes.card}>
                <CardContent>
                  <Typography variant="h4" className={classes.bookingName}>
                    {target}
                  </Typography>
                  {reservations[target].length === 0 && (
                    <p>Nothing to display here</p>
                  )}
                  {reservations[target].map((data) => (
                    <div className={classes.booking}>
                      <p>
                        <span className={classes.bold}>Date:</span>{" "}
                        {data.checkinDateToString} - {data.checkoutDateToString}
                      </p>
                      <p>
                        <span className={classes.bold}>Prepaid price:</span>{" "}
                        {data.prepaid_price} ₸
                      </p>
                      <p>
                        <span className={classes.bold}>Rooms number:</span>{" "}
                        {data.room_count}
                      </p>
                      <Grid container>
                        <Button
                          className={classes.button}
                          color="primary"
                          variant="outlined"
                          onClick={() => openReservation(data.id)}
                        >
                          Get PDF
                        </Button>
                        <Button
                          className={classes.button}
                          color="secondary"
                          variant="outlined"
                          onClick={() => deleteReservation(data.id)}
                        >
                          Delete
                        </Button>
                        <Button variant="outlined">Edit</Button>
                      </Grid>
                    </div>
                  ))}
                </CardContent>
              </Card>
            );
          })}
        </>
      ) : (
        <div className={classes.loader}>
          <CircularProgress />
        </div>
      )}
    </Container>
  );
}

export default ProfilePage;
