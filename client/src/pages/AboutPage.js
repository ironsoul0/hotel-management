import React, { useEffect, useState } from "react";

import { makeStyles } from "@material-ui/core/styles";
import Typography from "@material-ui/core/Typography";
import Container from "@material-ui/core/Container";
import CardMedia from "@material-ui/core/CardMedia";
import Card from "@material-ui/core/Card";
import CircularProgress from "@material-ui/core/CircularProgress";
import { Link } from "react-router-dom";

import HotelCards from "../components/HotelCards";
import SearchHotels from "../components/SearchHotels";

import api from "../config/api";
import frogs from "../images/frogs.png";

const useStyles = makeStyles((theme) => ({
  container: {
    "& > p": {
      fontSize: "18px",
    },
  },
  content: {
    textAlign: "center",
    marginTop: theme.spacing(10),
  },
  information: {
    marginTop: "18px",
  },
  media: {
    height: 0,
    width: "50%",
    paddingTop: "25.25%", // 16:9
    borderRadius: "15px",
    opacity: "0.9",
    marginTop: "40px",
  },
}));

function MainPage() {
  const classes = useStyles();
  const [hotels, setHotels] = useState(null);

  useEffect(() => {
    api.get("/hotels/").then((result) => {
      setHotels(result.data);
    });
  }, []);

  return (
    <Container className={classes.container} fixed>
      <Typography variant="h3" className={classes.heading}>
        About
      </Typography>
      <Typography variant="body2" className={classes.information}>
        This website is a final project we made for our Software Engineering
        class at Nazarbayev University.
      </Typography>
      <Typography variant="body2" className={classes.information}>
        The project implements a Hotel Management System where guests can book
        hotel rooms, desk clerks can approve reservations and managers can
        control hotel employees (for example, working hours of desk clerks).
      </Typography>
      <Typography variant="body2" className={classes.information}>
        In order to get access to the admin panel, you have to login as a
        manager or desk clerk via this <Link to="/vpn-access">VPN access</Link>{" "}
        link. For the sake of simplicity, this URL is publicly available but in
        the real project, it should be accessible only through VPN.
      </Typography>
      <Typography variant="body2" className={classes.information}>
        All the code is available on{" "}
        <a
          href="https://github.com/ironsoul0/hotel-management"
          rel="noopener noreferrer"
        >
          Github
        </a>{" "}
        with detailed explanations of technologies used and how to run the
        project locally.
      </Typography>
      <Typography className={classes.information}>
        Brought to you proudly by frogs. Have fun!
      </Typography>
      <CardMedia className={classes.media} image={frogs} title="Paella dish" />
    </Container>
  );
}

export default MainPage;
