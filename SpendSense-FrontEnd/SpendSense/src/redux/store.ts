import { configureStore } from "@reduxjs/toolkit";
import { authApi } from "./rtk-queries/authService";
import authStatusReducer from "../redux/slices/authSlice";

export const store = configureStore({
  reducer: {
    [authApi.reducerPath]: authApi.reducer,
    auth: authStatusReducer,
  },

  middleware: (getDefaultMiddleWear) =>
    getDefaultMiddleWear().concat(authApi.middleware),
});

export type RootState = ReturnType<typeof store.getState>;
export type AppDispatch = typeof store.dispatch;
