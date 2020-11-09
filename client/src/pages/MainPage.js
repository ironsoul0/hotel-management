import React, { useEffect } from "react";

import { makeStyles } from "@material-ui/core/styles";
import Typography from "@material-ui/core/Typography";

const useStyles = makeStyles((theme) => ({
  content: {
    textAlign: "center",
    marginTop: theme.spacing(10),
  },
}));

function MainPage() {
  const classes = useStyles();

  return (
    <Typography className={classes.content} variant="h6">
      Awesome content is coming soon!
    </Typography>
  );
}

export default MainPage;
