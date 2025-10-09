import { createSlice } from "@reduxjs/toolkit";
//Can use mutating logic as Immer library will detect changes and make immutable copies to satisfy reacts state rules

export type AuthState = {
  isLoggedIn: boolean;
};

const initialState: AuthState = {
  isLoggedIn: false,
};

export const authSlice = createSlice({
  name: "auth",
  initialState,
  reducers: {
    login: (state) => {
      //log user in
      state.isLoggedIn = true;
    },
    logout: (state) => {
      //call logout endpoint
      state.isLoggedIn = false;
    },
  },
});

export const { login, logout } = authSlice.actions;

export default authSlice.reducer;
