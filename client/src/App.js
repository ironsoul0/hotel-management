import React from "react";
import { useSelector } from "react-redux";
import { BrowserRouter as Router, Switch, Route } from "react-router-dom";

import Main from "./components/Main";

import LoginPage from "./pages/LoginPage";
import MainPage from "./pages/MainPage";
import ProfilePage from "./pages/ProfilePage";

function App() {
  const token = useSelector((state) => state.auth.token);

  return token ? (
    <Router>
      <Main>
        <Route exact path="/" component={MainPage} />
        <Route exact path="/profile" component={ProfilePage} />
      </Main>
    </Router>
  ) : (
    <Router>
      <Switch>
        <Route path="/" component={LoginPage} />
      </Switch>
    </Router>
  );
}

export default App;
