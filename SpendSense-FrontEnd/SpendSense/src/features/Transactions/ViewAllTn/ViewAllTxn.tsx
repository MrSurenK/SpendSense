import Button from "../../../components/btn/Button";
import styles from "./ViewAllTxn.module.css";
import Pagination from "../../../components/pagination/Pagination";
import {
  useSearchTransactionsMutation,
  type TransactionFilters,
} from "../../../redux/rtk-queries/transactionService";
import { useState, useEffect } from "react";
import TransactionFiltersComponent from "../../../components/filters/TransactionFiltersComponent";

export function ViewAllTxn() {
  //page states

  const [searchRequest, setSearchRequest] = useState<TransactionFilters>({
    page: 0,
    size: 5,
    sortField: "lastUpdated",
    sortDirection: "DESC",
  });

  const handlePageChange = (uiPage: number) => {
    setSearchRequest((prev) => ({
      ...prev,
      page: Math.max(uiPage - 1, 0),
    }));
  };

  //Call API
  const [searchTransactions, { data: transactionResponse }] =
    useSearchTransactionsMutation();

  useEffect(() => {
    searchTransactions({
      ...searchRequest,
    });
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [searchRequest]);

  return (
    <>
      <h1>All Transactions</h1>
      <TransactionFiltersComponent
        onSearch={(filters: TransactionFilters) => {
          setSearchRequest(filters); // directly sets your state
        }}
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
                    {txn.transactionType === "EXPENSE" ? "-" : "+"}$
                    {txn.amount.toFixed(2)}
                  </span>
                </td>
                <td>
                  <span className={styles.words}>{txn.catName}</span>
                </td>
                <td>
                  <span>
                    {" "}
                    {txn.transactionType === "EXPENSE" ? "EXPENSE" : "INCOME"}
                  </span>
                </td>
                <td>
                  <span>{txn.recurring ? "Yes" : "No"}</span>
                </td>
                <td>
                  <div className={styles.actions}>
                    <Button text={"View"} size={"sm"}></Button>
                    <Button text={"Edit"} size={"sm"}></Button>
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
    </>
  );
}
