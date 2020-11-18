import React, { useState } from "react";

import { makeStyles } from "@material-ui/core/styles";

import Card from "@material-ui/core/Card";
import CardActions from "@material-ui/core/CardActions";
import CardContent from "@material-ui/core/CardContent";
import Button from "@material-ui/core/Button";
import Typography from "@material-ui/core/Typography";

import { useHistory } from "react-router-dom";

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
}));

function HotelCards({ hotels }) {
  const classes = useStyles();
  const history = useHistory();

  if (hotels.length === 0) {
    return <Typography>No hotels found</Typography>;
  }

  return (
    <>
      <Typography variant="h3" className={classes.bookingName}>
        Hotels
      </Typography>
      {hotels.map((hotel) => (
        <>
          <Card className={classes.root}>
            <CardContent>
              <Typography
                className={classes.title}
                color="textSecondary"
                gutterBottom
              >
                {hotel.address}
              </Typography>
              <Typography variant="h5" component="h2">
                {hotel.name}
              </Typography>
              <Typography className={classes.pos} color="textSecondary">
                {hotel.features}
              </Typography>
              <Typography variant="body2" component="p">
                {hotel.description}
              </Typography>
            </CardContent>
            <CardActions>
              <Button
                size="small"
                onClick={() => history.push(`/hotel/${hotel.id}`)}
              >
                Learn More
              </Button>
            </CardActions>
          </Card>
        </>
      ))}
    </>
  );
}

export default HotelCards;
