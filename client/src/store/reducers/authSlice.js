import { createSlice } from "@reduxjs/toolkit";

export const authSlice = createSlice({
  name: "auth",
  initialState: {
    token: localStorage.getItem("token"),
    role: "manager",
  },
  reducers: {
    login: (state, action) => {
      localStorage.setItem("token", action.payload);
      state.token = action.payload;
    },
    logout: (state) => {
      localStorage.removeItem("token");
      state.token = "";
    },
  },
});

export const { login, logout } = authSlice.actions;

export default authSlice.reducer;
