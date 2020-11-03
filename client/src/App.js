import React from "react";
import { useSelector } from "react-redux";

import LoginPage from "./pages/LoginPage";
import MainPage from "./pages/MainPage";

function App() {
  const token = useSelector((state) => state.auth.token);

  return token ? <MainPage /> : <LoginPage />;
}

export default App;
