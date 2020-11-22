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
import EditProfilePage from "./pages/EditProfilePage";

function App() {
  const auth = useSelector((state) => state.auth);

  let content = (
    <Switch>
      <Route exact path="/" component={MainPage} />
      <Route exact path="/hotel/:id" component={HotelPage} />
      <Route exact path="/authorize" component={LoginPage} />
      <Route path="/vpn-access" component={AdminLoginPage} />
      <Redirect to="/" />
    </Switch>
  );

  if (auth.token) {
    if (auth.role === "user") {
      content = (
        <Switch>
          <Route exact path="/" component={MainPage} />
          <Route exact path="/profile" component={ProfilePage} />
          <Route exact path="/profile/:id" component={EditProfilePage} />
          <Route exact path="/hotel/:id" component={HotelPage} />
          <Redirect to="/" />
        </Switch>
      );
    } else if (auth.role === "manager") {
      content = (
        <Switch>
          <Route exact path="/" component={ManagerPage} />
          <Route exact path="/seasons" component={SeasonsPage} />
          <Route exact path="/employee/:id" component={EmployeePage} />
          <Redirect to="/" />
        </Switch>
      );
    } else if (auth.role === "clerk") {
      content = (
        <Switch>
          <Route exact path="/" component={ClerkPage} />
          <Route exact path="/create" component={CreateReservationPage} />
          <Route exact path="/edit/:id" component={EditReservationPage} />
          <Redirect to="/" />
        </Switch>
      );
    }
  }

  return (
    <Router>
      <Main>{content}</Main>
      <Alert />
    </Router>
  );
}

export default App;
