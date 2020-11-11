import React, { useEffect, useState } from "react";

import { makeStyles } from "@material-ui/core/styles";
import Typography from "@material-ui/core/Typography";
import Container from "@material-ui/core/Container";
import CircularProgress from "@material-ui/core/CircularProgress";

import HotelCards from "../components/HotelCards";

import api from "../config/api";

const useStyles = makeStyles((theme) => ({
  loader: {
    textAlign: "center",
  },
  content: {
    textAlign: "center",
    marginTop: theme.spacing(10),
  },
}));

function MainPage() {
  const classes = useStyles();
  const [hotels, setHotels] = useState(null);

  useEffect(() => {
    api.get("/hotels/").then((result) => {
      setHotels(result.data);
      console.log(result.data);
    });
  }, []);

  return (
    <Container fixed>
      {hotels ? (
        <>
          <Typography variant="h3" className={classes.bookingName}>
            Hotels
          </Typography>
          <HotelCards hotels={hotels} />
        </>
      ) : (
        <div className={classes.loader}>
          <CircularProgress />
        </div>
      )}
    </Container>
  );
}

export default MainPage;
