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

export type TransactionRow = {
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
};

export interface TransactionFilters {
  startDate?: string; //yyyy-mm-dd
  endDate?: string; //yyyy-mm-dd
  min?: number;
  max?: number;
  catId?: number;
  transactionType?: TransactionType;
  isRecurring?: boolean;
  title?: string;

  page?: number;
  size?: number;
  sortField?: string;
  sortDirection?: "ASC" | "DESC";
}

export type Categories = {
  id: number;
  name: string;
  isDeleted: boolean;
  isSystem: boolean;
  transactionType: TransactionType;
  userName: string;
};

export interface CategoriesResponse {
  success: boolean;
  message: string;
  data: Categories[];
}

export interface NewTransactionResponse {
  success: string;
  message: string;
  data: TransactionRow[]; //reuse transaction row type here as it is the same
}

export type NewTransactionRequest = {
  amount: number;
  title: string;
  remarks: string;
  recurring: boolean;
  date: string;
  category: number; //cat id
};

export type NewCategoryRequest = {
  name: string;
  transactionType: TransactionType;
};

export type TransactionType = "income" | "expense";

//Call the API
export const transactionApi = createApi({
  reducerPath: "transactionApi",
  baseQuery: baseQueryWithReauth,
  tagTypes: ["Categories"], //If user updates category in app we want the drop down list to update accordingly by refetching
  endpoints: (builder) => ({
    //POST request for all the transactions for logged in User with filters
    searchTransactions: builder.mutation<
      TransactionApiResponse,
      Partial<TransactionFilters>
    >({
      query: (body) => {
        return {
          url: `/transactions/search`,
          method: "POST",
          body,
        };
      },
    }),
    //Get Request for User categories
    getCategories: builder.query<{ id: number; name: string }[], void>({
      query: () => ({
        url: "/cat/allCategories",
        method: "GET",
      }),
      providesTags: ["Categories"],
      transformResponse: (response: CategoriesResponse) =>
        (response.data || []).map((c) => ({ id: c.id, name: c.name })),
    }),
    //Get Request to get all User Categries with full response
    getCategoriesWithFullRes: builder.query<CategoriesResponse, void>({
      query: () => ({
        url: "/cat/allCategories",
        method: "GET",
      }),
      providesTags: ["Categories"],
    }),
    //POST Request to add a new category
    addNewCat: builder.mutation<CategoriesResponse, NewCategoryRequest>({
      query: (body) => {
        return {
          url: "/cat/createCat",
          method: "POST",
          body,
        };
      },
      invalidatesTags: ["Categories"], //So everytime a new cat is passed get api for categories will be fetched again
    }),
    //POST Request for User to add new transactions
    addNewTxn: builder.mutation<NewTransactionResponse, NewTransactionRequest>({
      query: (body) => {
        return {
          url: "/transactions/addNewTransaction",
          method: "POST",
          body,
        };
      },
    }),
  }),
});

export const {
  useSearchTransactionsMutation,
  useGetCategoriesQuery,
  useGetCategoriesWithFullResQuery,
  useAddNewTxnMutation,
  useAddNewCatMutation,
} = transactionApi;

export default transactionApi;
