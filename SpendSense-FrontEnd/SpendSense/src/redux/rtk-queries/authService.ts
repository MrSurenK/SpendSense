import { createApi, fetchBaseQuery } from "@reduxjs/toolkit/query/react";

type SignInCredentials = {
  username: string;
  password: string;
};

type LoginResponse = {
  userId: number;
  username: string;
  lastLogin: Date;
  message: string;
  accessTokenExpiresIn: number;
  refreshTokenExpiresIn: number;
};

type LogOutResponse = {
  success: boolean;
  message: string;
  data: null;
};

export const authApi = createApi({
  reducerPath: "authApi",
  baseQuery: fetchBaseQuery({ baseUrl: "http://127.0.0.1:8080/auth/" }),
  endpoints: (build) => ({
    //the query accepts sign in credentials of type SignInCredentials and returns a message of type Message
    login: build.mutation<LoginResponse, SignInCredentials>({
      query: (credentials) => ({
        url: "login",
        method: "POST",
        body: credentials,
        headers: { "Content-Type": "application/json" },
      }),
    }),
    logout: build.mutation<LogOutResponse, void>({
      query: () => ({
        url: "logout",
        method: "POST",
        credentials: "include",
      }),
    }),
  }),
});

//export format according to redux framework : use[endpoint_name]Mutation
export const { useLoginMutation, useLogoutMutation } = authApi;

export default authApi;
