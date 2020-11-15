import React, { useState, useEffect } from "react";
import { useSelector, useDispatch } from "react-redux";

import { updateAlert } from "../store/reducers/alertSlice";

import Snackbar from "@material-ui/core/Snackbar";
import MuiAlert from "@material-ui/lab/Alert";

function Alert(props) {
  return <MuiAlert elevation={6} variant="filled" {...props} />;
}

function AlertWrapper() {
  const alert = useSelector((state) => state.alert);
  const dispatch = useDispatch();

  const handleClose = (target) => {
    return (event, reason) => {
      if (reason == "clickaway") {
        return;
      }

      dispatch(updateAlert({ target, newValue: false }));
    };
  };

  return (
    <>
      <Snackbar
        open={alert.success}
        autoHideDuration={3000}
        onClose={handleClose("success")}
      >
        <Alert onClose={handleClose("success")} severity="success">
          {alert.success}
        </Alert>
      </Snackbar>
      <Snackbar
        open={alert.error}
        autoHideDuration={3000}
        onClose={handleClose("error")}
      >
        <Alert onClose={handleClose("error")} severity="error">
          {alert.error}
        </Alert>
      </Snackbar>
      <Snackbar
        open={alert.info}
        autoHideDuration={3000}
        onClose={handleClose("info")}
      >
        <Alert onClose={handleClose("info")} severity="info">
          {alert.info}
        </Alert>
      </Snackbar>
    </>
  );
}

export default AlertWrapper;
