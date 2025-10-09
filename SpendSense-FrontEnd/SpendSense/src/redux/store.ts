import { configureStore } from "@reduxjs/toolkit";
import login from "./rtk-queries/authService";
import authStatusReducer from "../redux/slices/authSlice";

export const store = configureStore({
  reducer: {
    [login.reducerPath]: login.reducer,
    auth: authStatusReducer,
  },

  middleware: (getDefaultMiddleWear) =>
    getDefaultMiddleWear().concat(login.middleware),
});

export type RootState = ReturnType<typeof store.getState>;
export type AppDispatch = typeof store.dispatch;
