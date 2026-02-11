import { createApi } from "@reduxjs/toolkit/query/react";
import baseQueryWithReauth from "../config/baseQueryWithReauth";

export interface TransactionApiResponse {
  success: boolean;
  message: string;
  content: transactionRow[];
  page: number;
  size: number;
  total: number;
  totalPages: number;
  first: boolean;
  last: boolean;
}

export interface transactionRow {
  id: string; //UUID is a string not number
  amount: number;
  title: string;
  remarks: string;
  recurring: boolean;
  transactionDate: string; //ISO date string
  nextDueDate: Date;
  lastUpdated: Date;
  catId: number;
  catName: string;
  transactionType: TransactionType;
}

export type TransactionType = "income" | "expense";

//Call the API
export const transactionApi = createApi({
  reducerPath: "transactionApi",
  baseQuery: baseQueryWithReauth,
  endpoints: (builder) => ({
    getAllTransactionsSortedByLastUpdated: builder.query<
      TransactionApiResponse,
      { page: number; size: number; sort: string }
    >({
      query: ({ page, size, sort }) => ({
        url: `/getAllTransactions`,
        params: {
          page,
          size,
          sort,
        },
      }),
    }),
  }),
});

export const { useGetAllTransactionsSortedByLastUpdatedQuery } = transactionApi;

export default transactionApi;
