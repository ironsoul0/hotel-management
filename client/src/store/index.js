import { configureStore } from "@reduxjs/toolkit";
import counterReducer from "../store/reducers/counterSlice";
import authReducer from "../store/reducers/authSlice";

export default configureStore({
  reducer: {
    counter: counterReducer,
    auth: authReducer,
  },
});
