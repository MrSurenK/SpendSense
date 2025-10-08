import { createApi, fetchBaseQuery } from "@reduxjs/toolkit/query/react";

type SignInCredentials = {
  username: string;
  password: string;
};

type Message = {
  message: string;
};

export const authApi = createApi({
  reducerPath: "authApi",
  baseQuery: fetchBaseQuery({ baseUrl: "http://127.0.0.1:8080/auth/" }),
  endpoints: (build) => ({
    //the query accepts sign in credentials of type SignInCredentials and returns a message of type Message
    login: build.mutation<Message, SignInCredentials>({
      query: (credentials) => ({
        url: "login",
        method: "POST",
        body: credentials,
        headers: { "Content-Type": "application/json" },
      }),
      // transformResponse: (response: { message: string }) => response,
      // transformErrorResponse: No need to change it
    }),
  }),
});

//export format according to redux framework : use[endpoint_name]Mutation
export const { useLoginMutation } = authApi;

export default authApi;
