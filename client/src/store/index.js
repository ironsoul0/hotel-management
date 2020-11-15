import { configureStore } from "@reduxjs/toolkit";

import counterReducer from "../store/reducers/counterSlice";
import authReducer from "../store/reducers/authSlice";
import alertReducer from "../store/reducers/alertSlice";

export default configureStore({
  reducer: {
    counter: counterReducer,
    auth: authReducer,
    alert: alertReducer,
  },
});
