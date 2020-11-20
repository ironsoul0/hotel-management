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

import HotelCards from "../components/HotelCards";
import SearchHotels from "../components/SearchHotels";

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
}));

function ManagerPage() {
  const classes = useStyles();
  const [employees, setEmployees] = useState(null);
  const history = useHistory();

  useEffect(() => {
    api.get("/manager/allemployees").then((result) => {
      setEmployees(result.data);
    });
  }, []);

  return (
    <Container fixed>
      {employees ? (
        <>
          <Typography variant="h3" className={classes.bookingName}>
            Employees
          </Typography>
          {employees.map((employee) => (
            <>
              <Card className={classes.root}>
                <CardContent>
                  <Typography
                    className={classes.title}
                    color="textSecondary"
                    gutterBottom
                  >
                    {employee.hotelName}
                  </Typography>
                  <Typography variant="h5" component="h2">
                    {employee.name} {employee.surname}
                  </Typography>
                  <Typography className={classes.pos} color="textSecondary">
                    {employee.role}
                  </Typography>
                  <Typography variant="body2" component="p">
                    {employee.payment} ₸ p. h.
                  </Typography>
                </CardContent>
                <CardActions>
                  <Button
                    size="small"
                    onClick={() => history.push(`/employee/${employee.id}`)}
                  >
                    Edit hours
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

export default ManagerPage;
