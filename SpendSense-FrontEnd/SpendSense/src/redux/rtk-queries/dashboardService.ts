import { createApi } from "@reduxjs/toolkit/query/react";
import baseQueryWithReauth from "../config/baseQueryWithReauth";

export type TopListApiResponse = {
  success: boolean;
  message: string;
  data: TopSubAndSpendData[];
};

export type TopSubAndSpendData = {
  amount: number;
  title?: string;
  catName: string;
  description?: string;
  transactionDate: Date;
};

export type NetCashFlow = {
  data: {
    totalInflow: number;
    totalOutflow: number;
    netCashflow: number;
  };
};

export type ChartJsPieData = {
  labels: string[];
  values: number[];
  percentages: number[];
};

type ChartJsPieApiResponse = {
  success: boolean;
  message: string;
  data: ChartJsPieData;
};

export type LineChartDataset = {
  label: string;
  data: number[];
  fill: boolean;
  borderColor: string;
  tension: number;
};

export type ChartJsLineData = {
  labels: string[];
  datasets: LineChartDataset[];
};

type ChartJsLineApiResponse = {
  success: boolean;
  message: string;
  data: ChartJsLineData;
};

export const dashboardApi = createApi({
  reducerPath: "dashboardApi",
  baseQuery: baseQueryWithReauth,
  tagTypes: ["Dashboard"],
  endpoints: (builder) => ({
    getTopFiveSpend: builder.query<
      TopListApiResponse,
      { month: number; year: number }
    >({
      query: ({ month, year }) => ({
        url: "/dash/topFiveSpend",
        params: { month, year },
      }),
      providesTags: ["Dashboard"],
    }),
    getTopSubs: builder.query<
      TopListApiResponse,
      { page: number; size: number }
    >({
      query: ({ page, size }) => ({
        url: "/dash/subscriptions",
        params: { page, size },
      }),
      providesTags: ["Dashboard"],
    }),
    getNetCashflow: builder.query<
      NetCashFlow,
      { startDate: string; endDate: string }
    >({
      query: ({ startDate, endDate }) => ({
        url: "/dash/netCashflow",
        params: { startDate, endDate },
      }),
      providesTags: ["Dashboard"],
    }),
    getSpendingPieChart: builder.query<
      ChartJsPieData,
      { month: number; year: number }
    >({
      query: ({ month, year }) => ({
        url: `/chart/pie/${month}/${year}`,
        method: "GET",
      }),
      transformResponse: (response: ChartJsPieApiResponse) => response.data,
      providesTags: ["Dashboard"],
    }),
    getYearlyLineChart: builder.query<ChartJsLineData, { year: number }>({
      query: ({ year }) => ({
        url: `/chart/line/${year}`,
        method: "GET",
      }),
      transformResponse: (response: ChartJsLineApiResponse) => response.data,
      providesTags: ["Dashboard"],
    }),
  }),
});

//export format according to redux framework for GET endpoints: use[endpoints_name]Query
export const {
  useGetTopFiveSpendQuery,
  useGetTopSubsQuery,
  useGetNetCashflowQuery,
  useGetSpendingPieChartQuery,
  useGetYearlyLineChartQuery,
} = dashboardApi;

export default dashboardApi;
