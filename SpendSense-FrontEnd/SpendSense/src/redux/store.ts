import { configureStore } from "@reduxjs/toolkit";
import login from "./rtk-queries/authService";

export const store = configureStore({
  reducer: {
    [login.reducerPath]: login.reducer,
  },

  middleware: (getDefaultMiddleWear) =>
    getDefaultMiddleWear().concat(login.middleware),
});

export type RootState = ReturnType<typeof store.getState>;
export type AppDispatch = typeof store.dispatch;
