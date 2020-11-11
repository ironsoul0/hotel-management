import React, { useState } from "react";

import { makeStyles } from "@material-ui/core/styles";

import Card from "@material-ui/core/Card";
import CardActions from "@material-ui/core/CardActions";
import CardContent from "@material-ui/core/CardContent";
import Button from "@material-ui/core/Button";
import Typography from "@material-ui/core/Typography";

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

function HotelCard({ hotels }) {
  const classes = useStyles();
  const bull = <span className={classes.bullet}>•</span>;

  return hotels.map((hotel) => (
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
            {hotel.phone}
          </Typography>
          <Typography variant="body2" component="p">
            {hotel.features}
          </Typography>
        </CardContent>
        <CardActions>
          <Button size="small">Learn More</Button>
        </CardActions>
      </Card>
    </>
  ));
}

export default HotelCard;
