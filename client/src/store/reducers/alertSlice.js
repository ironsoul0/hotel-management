import { createSlice } from "@reduxjs/toolkit";

export const alertSlice = createSlice({
  name: "alert",
  initialState: {
    success: false,
    error: false,
    info: false,
  },
  reducers: {
    updateAlert: (state, action) => {
      const { target, newValue } = action.payload;
      state[target] = newValue;
    },
  },
});

export const { updateAlert } = alertSlice.actions;

export default alertSlice.reducer;
