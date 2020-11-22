import { createSlice } from "@reduxjs/toolkit";

import api from "../../config/api";

export const authSlice = createSlice({
  name: "auth",
  initialState: {
    token: localStorage.getItem("token"),
    role: localStorage.getItem("role"),
  },
  reducers: {
    login: (state, action) => {
      const { token, role, hotelId } = action.payload;

      localStorage.setItem("token", token);
      localStorage.setItem("role", role);
      localStorage.setItem("hotelId", hotelId);

      api.defaults.headers["Authorization"] = `Bearer ${token}`;

      state.token = token;
      state.role = role;
    },
    logout: (state) => {
      localStorage.removeItem("token");
      localStorage.removeItem("role");
      localStorage.removeItem("hotelId");

      state.token = "";
      state.role = "";
    },
  },
});

export const { login, logout } = authSlice.actions;

export default authSlice.reducer;
