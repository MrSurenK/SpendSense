import { createSlice, type PayloadAction } from "@reduxjs/toolkit";
//Can use mutating logic as Immer library will detect changes and make immutable copies to satisfy reacts state rules

export type AuthState = {
  userId: number | null;
  username: string | null;
  lastLogIn: Date | null;
  isLoggedIn: boolean;
};

const initialState: AuthState = {
  userId: null,
  username: null,
  lastLogIn: null,
  isLoggedIn: false,
};

export type LoginPayload = {
  userId: number;
  username: string;
  lastLogin: Date;
};

export const authSlice = createSlice({
  name: "auth",
  initialState,
  reducers: {
    login: (state, action: PayloadAction<LoginPayload>) => {
      //user logged in
      const { userId, username, lastLogin } = action.payload;
      state.userId = userId;
      state.username = username;
      state.lastLogIn = lastLogin;
      state.isLoggedIn = true;
    },
    logout: (state) => {
      //call logout endpoint
      state.userId = null;
      state.username = null;
      state.lastLogIn = null;
      state.isLoggedIn = false;
    },
  },
});

export const { login, logout } = authSlice.actions;

export default authSlice.reducer;
