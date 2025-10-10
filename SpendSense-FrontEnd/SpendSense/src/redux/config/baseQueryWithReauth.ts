import { fetchBaseQuery } from "@reduxjs/toolkit/query";
import type {
  BaseQueryFn,
  FetchArgs,
  FetchBaseQueryError,
} from "@reduxjs/toolkit/query";
import { logout } from "../slices/authSlice";
import { Mutex } from "async-mutex";

const mutex = new Mutex(); //to prevent multiple unecessary calls to refresh token endpoint
const baseQuery = fetchBaseQuery({ baseUrl: "http://127.0.0.1:8080/auth" });
const baseQueryWithReauth: BaseQueryFn<
  string | FetchArgs,
  unknown,
  FetchBaseQueryError
> = async (args, api, extraOptions) => {
  await mutex.waitForUnlock();
  let result = await baseQuery(args, api, extraOptions);
  if (result.error && result.error.status === 401) {
    //check if mutex is locked
    if (!mutex.isLocked()) {
      const release = await mutex.acquire();
      //try to call refresh token if still valid
      try {
        const refreshResult = await baseQuery(
          "/refreshToken",
          api,
          extraOptions
        );
        if (refreshResult.data) {
          //refresh token will be set in HTTP only header by backend so no need to handle in front end
          //retry the initial query
          result = await baseQuery(args, api, extraOptions);
        } else {
          api.dispatch(logout());
        }
      } finally {
        //release must be called once the mutex should be released again
        release();
      }
    } else {
      //wait until the mutex is available without locking it
      await mutex.waitForUnlock();
      result = await baseQuery(args, api, extraOptions);
    }
  }
  return result;
};

export default baseQueryWithReauth;
