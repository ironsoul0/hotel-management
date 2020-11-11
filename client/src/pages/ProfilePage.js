import React, { useEffect, useState } from "react";

import { makeStyles } from "@material-ui/core/styles";
import Typography from "@material-ui/core/Typography";
import CircularProgress from "@material-ui/core/CircularProgress";
import Container from "@material-ui/core/Container";

import api from "../config/api";

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
  },
}));

function ProfilePage() {
  const classes = useStyles();
  const [reservations, setReservations] = useState(null);

  useEffect(() => {
    api.get("/profile/reservations").then((result) => {
      setReservations(result.data);
    });
  }, []);

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
              <>
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
                      {data.checkinDate} - {data.checkoutDate}
                    </p>
                    <p>
                      <span className={classes.bold}>Prepaid price:</span>{" "}
                      {data.prepaid_price}$
                    </p>
                    <p>
                      <span className={classes.bold}>Rooms number:</span>{" "}
                      {data.room_count}
                    </p>
                  </div>
                ))}
              </>
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
