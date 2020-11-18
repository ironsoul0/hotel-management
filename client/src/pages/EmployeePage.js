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
import { useParams } from "react-router-dom";

import { useDispatch } from "react-redux";

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
}));

function EmployeePage() {
  const dispatch = useDispatch();
  const classes = useStyles();
  const [employee, setEmployee] = useState(null);
  const [schedule, setSchedule] = useState(null);
  const { id } = useParams();
  const [fields, setFields] = useState({});

  const fieldChange = (e) => {
    setFields({ ...fields, [e.target.name]: e.target.value });
  };

  useEffect(() => {
    api.get("/manager/allemployees").then((result) => {
      setEmployee(result.data.find((current) => current.id === parseInt(id)));
    });

    api.get(`/manager/schedule?employeeid=${id}`).then((result) => {
      setSchedule(result.data);
    });
  }, []);

  return (
    <Container fixed>
      {employee && schedule ? (
        <>
          <Typography variant="h4" component="h2">
            {employee.name}
          </Typography>
          {schedule.map((piece) => (
            <Card className={classes.root}>
              <CardContent>
                {/* <Typography className={classes.pos} color="textSecondary"> */}
                {/*   {hotel.features} */}
                {/* </Typography> */}
                {/* <Typography variant="body2" component="p"> */}
                {/*   {hotel.description} */}
                {/* </Typography> */}
              </CardContent>
            </Card>
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

export default EmployeePage;
