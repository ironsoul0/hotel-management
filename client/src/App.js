import React from "react";
import { useSelector } from "react-redux";
import {
  BrowserRouter as Router,
  Switch,
  Redirect,
  Route,
} from "react-router-dom";

import Main from "./components/Main";
import Alert from "./components/Alert";

import LoginPage from "./pages/LoginPage";
import MainPage from "./pages/MainPage";
import ProfilePage from "./pages/ProfilePage";
import HotelPage from "./pages/HotelPage";
import ManagerPage from "./pages/ManagerPage";
import EmployeePage from "./pages/EmployeePage";
import SeasonsPage from "./pages/SeasonsPage";
import AdminLoginPage from "./pages/AdminLoginPage";
import ClerkPage from "./pages/ClerkPage";
import CreateReservationPage from "./pages/CreateReservationPage";
import EditReservationPage from "./pages/EditReservationPage";

function App() {
  const auth = useSelector((state) => state.auth);

  let content = (
    <Router>
      <Switch>
        <Route exact path="/" component={LoginPage} />
        <Route path="/vpn-access" component={AdminLoginPage} />
        <Redirect to="/" />
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
          <Redirect to="/" />
        </Main>
      );
    } else if (auth.role === "manager") {
      content = (
        <Main>
          <Route exact path="/" component={ManagerPage} />
          <Route exact path="/seasons" component={SeasonsPage} />
          <Route exact path="/employee/:id" component={EmployeePage} />
          <Redirect to="/" />
        </Main>
      );
    } else if (auth.role === "clerk") {
      content = (
        <Main>
          <Route exact path="/" component={ClerkPage} />
          <Route exact path="/create" component={CreateReservationPage} />
          <Route exact path="/edit/:id" component={EditReservationPage} />
          <Redirect to="/" />
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
