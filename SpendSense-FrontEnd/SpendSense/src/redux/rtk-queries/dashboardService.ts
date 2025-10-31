import { createApi } from "@reduxjs/toolkit/query/react";
import baseQueryWithReauth from "../config/baseQueryWithReauth";

export type DashboardApiResponse = {
  success: boolean;
  message: string;
  data: TopSubAndSpendData[] | IncomeDetails[];
};

export type TopSubAndSpendData = {
  amount: number;
  catName: string;
  description: string;
  transactionDate: Date;
};

export type IncomeDetails = {
  salary: number;
  bonus: number;
  cpf_contribution: number;
  takeHomePay: number;
};

export const dashboardApi = createApi({
  baseQuery: baseQueryWithReauth,
  endpoints: (builder) => ({
    getTopFiveSpend: builder.query<
      DashboardApiResponse,
      { month: number; year: number }
    >({
      query: ({ month, year }) => ({
        url: "/dash/topFiveSpend",
        params: { month, year },
      }),
    }),
    getTopSubs: builder.query<
      DashboardApiResponse,
      { page: number; size: number }
    >({
      query: ({ page, size }) => ({
        url: "/dash/subscriptions",
        params: { page, size },
      }),
    }),
    getUserIncomeInfo: builder.query<
      DashboardApiResponse,
      { startDate: Date; endDate: Date }
    >({
      query: ({ startDate, endDate }) => ({
        url: "/dash/incomeSummary",
        params: { startDate, endDate },
      }),
    }),
  }),
});

//export format according to redux framework for GET endpoints: use[endpoints_name]Query
export const {
  useGetTopFiveSpendQuery,
  useGetTopSubsQuery,
  useGetUserIncomeInfoQuery,
} = dashboardApi;

export default dashboardApi;
