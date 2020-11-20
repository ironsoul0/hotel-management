import { createSlice } from "@reduxjs/toolkit";

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

      state.token = token;
      state.role = role;
    },
    logout: (state) => {
      localStorage.removeItem("token");
      state.token = "";
    },
  },
});

export const { login, logout } = authSlice.actions;

export default authSlice.reducer;
