import { combineReducers, configureStore } from "@reduxjs/toolkit";
import { authApi } from "./rtk-queries/authService";
import authStatusReducer from "../redux/slices/authSlice";
import { dashboardApi } from "./rtk-queries/dashboardService";
import {
  persistStore,
  persistReducer,
  FLUSH,
  REHYDRATE,
  PAUSE,
  PERSIST,
  PURGE,
  REGISTER,
} from "redux-persist";
import storage from "redux-persist/es/storage";

// ToDo: Redux-persisit configuration so that logIn persist when browser is refreshed.
const persistConfig = {
  key: "root",
  storage,
  whitelist: ["auth"],
};

const rootReducer = combineReducers({
  [authApi.reducerPath]: authApi.reducer,
  auth: authStatusReducer,
  [dashboardApi.reducerPath]: dashboardApi.reducer,
});

const persistedReducer = persistReducer(persistConfig, rootReducer);

export const store = configureStore({
  reducer: persistedReducer,
  middleware: (getDefaultMiddleware) =>
    getDefaultMiddleware({
      serializableCheck: {
        ignoredActions: [FLUSH, REHYDRATE, PAUSE, PERSIST, PURGE, REGISTER],
      },
    }).concat(authApi.middleware, dashboardApi.middleware),
});

export const persistor = persistStore(store);

export type RootState = ReturnType<typeof store.getState>;
export type AppDispatch = typeof store.dispatch;

// middleware: (getDefaultMiddleWare) =>
//   getDefaultMiddleWare().concat(authApi.middleware, dashboardApi.middleware),
