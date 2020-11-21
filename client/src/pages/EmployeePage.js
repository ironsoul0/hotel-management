import React, { useEffect, useState } from "react";

import { makeStyles } from "@material-ui/core/styles";
import Typography from "@material-ui/core/Typography";
import Container from "@material-ui/core/Container";
import CircularProgress from "@material-ui/core/CircularProgress";
import Card from "@material-ui/core/Card";
import CardContent from "@material-ui/core/CardContent";
import TextField from "@material-ui/core/TextField";
import Button from "@material-ui/core/Button";
import Grid from "@material-ui/core/Grid";
import Tooltip from "@material-ui/core/Tooltip";

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
    margin: "25px 0px 0px 0",
    width: "100px",
    "&:nth-child(1)": {
      marginRight: "15px",
    },
  },
  grid: {
    marginTop: "20px",
  },
  textField: {
    marginRight: "30px",
  },
}));

function EmployeePage() {
  const dispatch = useDispatch();
  const classes = useStyles();
  const [employee, setEmployee] = useState(null);
  const [schedule, setSchedule] = useState(null);
  const { id } = useParams();

  const isValid = (index) => {
    const fields = schedule[index];

    const requiredFields = [
      "date_work",
      "time_start",
      "time_end",
      "total_Payment",
      "total_hours",
    ];

    const invalidEntry = requiredFields.find((field) => {
      return !fields[field];
    });

    return !invalidEntry;
  };

  const formatDatework = (datework) => {
    if (!datework) return "";
    if (datework.includes("T")) {
      datework = datework.substr(0, datework.indexOf("T"));
    }
    return datework;
  };

  const fieldChange = (index) => {
    return (event) => {
      const { name, value } = event.target;
      const updatedSchedule = [...schedule];
      updatedSchedule[index][name] = value;
      setSchedule(updatedSchedule);
    };
  };

  const getSchedule = () => {
    api.get(`/manager/schedule?employeeid=${id}`).then((result) => {
      setSchedule([...result.data, {}]);
    });
  };

  const saveHours = (index, isLast) => {
    const newValues = schedule[index];

    const params = new URLSearchParams();
    params.append("date_work", newValues.date_work);
    params.append("time_start", newValues.time_start);
    params.append("time_end", newValues.time_end);
    params.append("total_Payment", newValues.total_Payment);
    params.append("total_hours", newValues.total_hours);

    if (!isLast) {
      api
        .post(`/manager/schedule/${schedule[index].id}/edit`, params)
        .then(() => {
          dispatch(
            updateAlert({
              target: "success",
              newValue: `Successfully updated!`,
            })
          );
        });
    } else {
      params.append("employeeId", id);
      api.post("/manager/schedule/add", params).then(() => {
        dispatch(
          updateAlert({
            target: "success",
            newValue: `Successfully created!`,
          })
        );

        getSchedule();
      });
    }
  };

  const deleteWorkingHours = (id) => {
    api.post(`/manager/schedule/${id}/delete`).then(() => {
      dispatch(
        updateAlert({
          target: "success",
          newValue: `Successfully deleted!`,
        })
      );

      getSchedule();
    });
  };

  useEffect(() => {
    api.get("/manager/allemployees").then((result) => {
      setEmployee(result.data.find((current) => current.id === parseInt(id)));
    });

    getSchedule();
  }, []);

  return (
    <Container fixed>
      {employee && schedule ? (
        <>
          <Typography variant="h4" component="h4">
            {employee.name}
          </Typography>
          <Typography variant="p2" component="p">
            Working hours
          </Typography>
          {schedule.map((piece, index) => (
            <Card className={classes.root}>
              <CardContent>
                <Grid container>
                  <TextField
                    name="time_start"
                    label="Time start"
                    type="time"
                    defaultValue={piece.time_start}
                    className={classes.textField}
                    InputLabelProps={{
                      shrink: true,
                    }}
                    value={schedule[index].time_start}
                    onChange={fieldChange(index)}
                  />
                  <TextField
                    name="time_end"
                    label="Time end"
                    type="time"
                    defaultValue={piece.time_end}
                    className={classes.textField}
                    InputLabelProps={{
                      shrink: true,
                    }}
                    value={schedule[index].time_end}
                    onChange={fieldChange(index)}
                  />
                  <TextField
                    name="date_work"
                    label="Date work"
                    type="date"
                    defaultValue={piece.date_work}
                    className={classes.textField}
                    InputLabelProps={{
                      shrink: true,
                    }}
                    value={formatDatework(schedule[index].date_work)}
                    onChange={fieldChange(index)}
                  />
                  <Tooltip
                    disableFocusListener
                    disableTouchListener
                    title="Put 0 for standard salary"
                  >
                    <TextField
                      name="total_Payment"
                      label="Total payment"
                      type="number"
                      defaultValue={piece.total_Payment}
                      className={classes.textField}
                      InputLabelProps={{
                        shrink: true,
                      }}
                      value={schedule[index].total_Payment}
                      placeholder="Salary in ₸ per hour"
                      onChange={fieldChange(index)}
                    />
                  </Tooltip>
                  <TextField
                    name="total_hours"
                    label="Total hours"
                    type="number"
                    defaultValue={piece.total_hours}
                    placeholder="Hours to work"
                    className={classes.textField}
                    InputLabelProps={{
                      shrink: true,
                    }}
                    value={schedule[index].total_hours}
                    onChange={fieldChange(index)}
                  />
                </Grid>
                <Grid>
                  <Button
                    className={classes.button}
                    type="submit"
                    variant={
                      index === schedule.length - 1 ? "contained" : "outlined"
                    }
                    color="primary"
                    onClick={() =>
                      saveHours(index, index === schedule.length - 1)
                    }
                    disabled={!isValid(index)}
                  >
                    {index === schedule.length - 1 ? "Create" : "Save"}
                  </Button>
                  {index !== schedule.length - 1 && (
                    <Button
                      className={classes.button}
                      type="submit"
                      variant={"outlined"}
                      color="secondary"
                      onClick={() => deleteWorkingHours(schedule[index].id)}
                    >
                      Delete
                    </Button>
                  )}
                </Grid>
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
