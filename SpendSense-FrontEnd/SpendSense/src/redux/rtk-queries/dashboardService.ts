import { createApi, fetchBaseQuery } from "@reduxjs/toolkit/query/react";

type DashboardApiResponse = {
  success: boolean;
  message: string;
  data: TopSubAndSpendData[] | IncomeDetails[];
};

type TopSubAndSpendData = {
  amount: number;
  catName: string;
  description: string;
  transactionDate: Date;
};

type IncomeDetails = {
  salary: number;
  bonus: number;
  cpf_contribution: number;
  takeHomePay: number;
};

export const dashboardApi = createApi({
  baseQuery: fetchBaseQuery({
    baseUrl: "http://127.0.0.1:8080/dash/",
  }),
  endpoints: (builder) => ({
    getTopFiveSpend: builder.query<
      DashboardApiResponse,
      { month: number; year: number }
    >({
      query: ({ month, year }) => ({
        url: "topFiveSpend",
        params: { month, year },
      }),
    }),
    getTopSubs: builder.query<
      DashboardApiResponse,
      { page: number; size: number }
    >({
      query: ({ page, size }) => ({
        url: "subscriptions",
        params: { page, size },
      }),
    }),
    getUserIncomeInfo: builder.query<
      DashboardApiResponse,
      { startDate: Date; endDate: Date }
    >({
      query: ({ startDate, endDate }) => ({
        url: "incomeSummary",
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
