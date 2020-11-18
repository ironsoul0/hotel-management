import React, { useEffect, useState } from "react";

import { makeStyles } from "@material-ui/core/styles";
import Typography from "@material-ui/core/Typography";
import Container from "@material-ui/core/Container";
import CircularProgress from "@material-ui/core/CircularProgress";

import HotelCards from "../components/HotelCards";
import SearchHotels from "../components/SearchHotels";

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

function ManagerPage() {
  const classes = useStyles();
  const [employees, setEmployees] = useState(null);

  useEffect(() => {
    api.get("/manager/allemployees").then((result) => {
      setEmployees(result.data);
    });
  }, []);

  return (
    <Container fixed>
      {employees ? (
        employees.length
      ) : (
        <div className={classes.loader}>
          <CircularProgress />
        </div>
      )}
    </Container>
  );
}

export default ManagerPage;
