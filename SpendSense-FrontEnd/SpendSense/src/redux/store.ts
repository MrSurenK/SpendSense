import { configureStore } from "@reduxjs/toolkit";
import { authApi } from "./rtk-queries/authService";
import authStatusReducer from "../redux/slices/authSlice";
import { dashboardApi } from "./rtk-queries/dashboardService";

export const store = configureStore({
  reducer: {
    [authApi.reducerPath]: authApi.reducer,
    auth: authStatusReducer,
    [dashboardApi.reducerPath]: dashboardApi.reducer,
  },

  middleware: (getDefaultMiddleWare) =>
    getDefaultMiddleWare().concat(authApi.middleware, dashboardApi.middleware),
});

export type RootState = ReturnType<typeof store.getState>;
export type AppDispatch = typeof store.dispatch;
