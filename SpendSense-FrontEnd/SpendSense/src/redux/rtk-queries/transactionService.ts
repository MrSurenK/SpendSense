import { createApi } from "@reduxjs/toolkit/query/react";
import baseQueryWithReauth from "../config/baseQueryWithReauth";

export interface TransactionApiResponse {
  success: boolean;
  message: string;
  content: TransactionRow[];
  page: number;
  size: number;
  total: number;
  totalPages: number;
  first: boolean;
  last: boolean;
}

export interface TransactionRow {
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

export interface TransactionFilters {
  startDate?: string; //yyyy-mm-dd
  endDate?: string; //yyyy-mm-dd
  min?: number;
  max?: number;
  catId?: number;
  transactionType?: TransactionType;
  isRecurring?: boolean;
  title?: string;
}

export type TransactionType = "INCOME" | "EXPENSE";

//Call the API
export const transactionApi = createApi({
  reducerPath: "transactionApi",
  baseQuery: baseQueryWithReauth,
  endpoints: (builder) => ({
    getAllTransactions: builder.query<
      TransactionApiResponse,
      { page: number; size: number; sort: string; filters?: TransactionFilters }
    >({
      query: ({ page, size, sort, filters = {} }) => {
        //format filters to only show if filter is appplied
        const cleanedFilters = Object.fromEntries(
          Object.entries(filters).filter(
            ([_, v]) => v != undefined && v != null && v !== "",
          ),
        );

        return {
          url: `/transactions`,
          params: {
            ...cleanedFilters,
            page,
            size,
            sort,
          },
        };
      },
    }),
  }),
});

export const { useGetAllTransactionsQuery } = transactionApi;

export default transactionApi;
