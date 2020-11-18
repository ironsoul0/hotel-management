import React from "react";
import { useSelector } from "react-redux";
import { BrowserRouter as Router, Switch, Route } from "react-router-dom";

import Main from "./components/Main";
import Alert from "./components/Alert";

import LoginPage from "./pages/LoginPage";
import MainPage from "./pages/MainPage";
import ProfilePage from "./pages/ProfilePage";
import HotelPage from "./pages/HotelPage";
import ManagerPage from "./pages/ManagerPage";

function App() {
  const auth = useSelector((state) => state.auth);

  let content = (
    <Router>
      <Switch>
        <Route path="/" component={LoginPage} />
      </Switch>
    </Router>
  );

  if (auth.token) {
    if (auth.role === "user") {
      content = (
        <Main>
          <Route exact path="/" component={MainPage} />
          <Route exact path="/profile" component={ProfilePage} />
          <Route exact path="/hotel/:id" component={HotelPage} />
        </Main>
      );
    } else if (auth.role === "manager") {
      content = (
        <Main>
          <ManagerPage />
        </Main>
      );
    }
  }

  return (
    <Router>
      {content}
      <Alert />
    </Router>
  );
}

export default App;
