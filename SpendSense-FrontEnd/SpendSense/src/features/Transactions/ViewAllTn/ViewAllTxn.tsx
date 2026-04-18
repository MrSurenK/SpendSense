import Button from "../../../components/btn/Button";
import styles from "./ViewAllTxn.module.css";
import Pagination from "../../../components/pagination/Pagination";
import {
  useGetCategoriesQuery,
  useGetTransactionQuery,
  useSearchTransactionsQuery,
  type TransactionRow,
  type TransactionFilters,
} from "../../../redux/rtk-queries/transactionService";
import { useState } from "react";
import { createPortal } from "react-dom";
import { skipToken } from "@reduxjs/toolkit/query";
import EditTxnModal from "../../../components/modal/EditTxnModal";
import ViewTxnModal from "../../../components/modal/ViewTxnModal";
import TransactionFiltersComponent from "../../../components/filters/TransactionFiltersComponent";

export function ViewAllTxn() {
  //page states

  //Defaults
  const defaultRequest: TransactionFilters = {
    page: 0,
    size: 10,
    sortField: "lastUpdated",
    sortDirection: "DESC",
  };

  const [searchRequest, setSearchRequest] =
    useState<TransactionFilters>(defaultRequest);
  const [selectedTxn, setSelectedTxn] = useState<TransactionRow | null>(null);
  const [viewTxnId, setViewTxnId] = useState<string | null>(null);

  const handlePageChange = (uiPage: number) => {
    setSearchRequest((prev) => ({
      ...prev,
      page: Math.max(uiPage - 1, 0),
    }));
  };

  const { data: catData } = useGetCategoriesQuery();

  console.log(catData);

  //Call API
  const { data: transactionResponse } = useSearchTransactionsQuery({
    ...searchRequest,
  });

  const { data: selectedViewTxn } = useGetTransactionQuery(
    viewTxnId ?? skipToken,
  );

  return (
    <>
      <h1>All Transactions</h1>
      <TransactionFiltersComponent
        onSearch={(uiFilters) => {
          if (Object.keys(uiFilters).length === 0) {
            //reset: restore parent defaults
            setSearchRequest(defaultRequest);
          } else {
            // replace searchRequest entirely — only include what child sent + pagination defaults
            setSearchRequest({
              ...defaultRequest,
              ...uiFilters,
              page: 0,
            });
          }
        }}
        categories={catData}
      />
      <div className={styles.tableContainer}>
        <table className={styles.tableStyle}>
          <thead>
            <tr>
              <th>Date</th>
              <th>Item</th>
              <th>Amount</th>
              <th>Category</th>
              <th>Type</th>
              <th>Recurring</th>
              <th>Actions</th>
              <th>Delete</th>
            </tr>
          </thead>
          <tbody>
            {transactionResponse?.content.map((txn) => (
              <tr key={txn.id}>
                <td>{new Date(txn.transactionDate).toLocaleDateString()}</td>
                <td>
                  <div>{txn.title}</div>
                  <div className={styles.remarks}>{txn.remarks}</div>
                </td>
                <td>
                  <span>
                    {txn.transactionType === "expense" ? "-" : "+"}$
                    {txn.amount.toFixed(2)}
                  </span>
                </td>
                <td>
                  <span className={styles.words}>{txn.catName}</span>
                </td>
                <td>
                  <span>
                    {" "}
                    {txn.transactionType === "expense" ? "EXPENSE" : "INCOME"}
                  </span>
                </td>
                <td>
                  <span>{txn.recurring ? "Yes" : "No"}</span>
                </td>
                <td>
                  <div className={styles.actions}>
                    <Button
                      text={"View"}
                      size={"sm"}
                      onClick={() => setViewTxnId(txn.id)}
                    ></Button>
                    <Button
                      text={"Edit"}
                      size={"sm"}
                      onClick={() => setSelectedTxn(txn)}
                    ></Button>
                  </div>
                </td>
                <td>
                  <button className={styles.deleteBtn}>
                    <svg
                      xmlns="http://www.w3.org/2000/svg"
                      x="0px"
                      y="0px"
                      width="35"
                      height="35"
                      viewBox="0 0 50 50"
                    >
                      <path
                        fill="currentColor"
                        d="M44,24c0,11.045-8.955,20-20,20S4,35.045,4,24S12.955,4,24,4S44,12.955,44,24z"
                      ></path>
                      <path
                        fill="#fff"
                        d="M29.656,15.516l2.828,2.828l-14.14,14.14l-2.828-2.828L29.656,15.516z"
                      ></path>
                      <path
                        fill="#fff"
                        d="M32.484,29.656l-2.828,2.828l-14.14-14.14l2.828-2.828L32.484,29.656z"
                      ></path>
                    </svg>
                  </button>
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>
      {/*Make this more functional with state*/}
      <Pagination
        currPage={(searchRequest.page ?? 0) + 1}
        totalPages={transactionResponse?.totalPages ?? 1}
        onPageChange={handlePageChange}
      />
      {selectedTxn &&
        createPortal(
          <EditTxnModal
            transaction={selectedTxn}
            setOpenEditModal={(open) => {
              if (!open) setSelectedTxn(null);
            }}
          />,
          document.body,
        )}

      {viewTxnId &&
        selectedViewTxn &&
        createPortal(
          <ViewTxnModal
            transaction={selectedViewTxn}
            setOpenViewModal={(open) => {
              if (!open) setViewTxnId(null);
            }}
          />,
          document.body,
        )}
    </>
  );
}
