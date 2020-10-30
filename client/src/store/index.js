import { configureStore } from "@reduxjs/toolkit";
import counterReducer from "../store/reducers/counterSlice";

export default configureStore({
  reducer: {
    counter: counterReducer,
  },
});
