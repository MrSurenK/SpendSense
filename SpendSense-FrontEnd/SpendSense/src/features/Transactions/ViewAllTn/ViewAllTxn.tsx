import Button from "../../../components/btn/Button";
import InputBox from "../../../components/input-box/InputBox";
import styles from "./ViewAllTxn.module.css";
import Pagination from "../../../components/pagination/Pagination";
import { useGetAllTransactionsSortedByLastUpdatedQuery } from "../../../redux/rtk-queries/transactionService";

export function ViewAllTxn() {
  /*
    ToDo: 
    1. Filters Section
    2. Table formatting 
    3. Import data 
    4. Add button functionality for delete entry
    5. Modal to confirm deletion 
    6. UseEffect to update table data after delete
    7. Call API and remove all dummy data and hardcoded rows in table

    ------------------------------ Pagination Section ----------------------------
    1. Responsive pagination Sections(Pagination is server side)
        - Get total pages in API and render it dynamically from 1 up to 10 only 
        - UI must indicate current page user is looking at
\
    ----------------------------- Filters Section --------------------------------
    1. Full functionality is in the backend so the UI only needs to update accordingly and have all the
    filter options present 
    2. List of Filter options: 
      a.
      b. 
      c. 
      d. 

    */
  //How do I map the total number of pages dynamically? Its only 1 to ... n pages

  //Call API
  const {
    data: transactionResponse,
    error,
    isLoading: boolean,
  } = useGetAllTransactionsSortedByLastUpdatedQuery({
    page: 1,
    size: 5,
    sort: "lastUpdated",
  });

  return (
    <>
      <h1>All Transactions</h1>
      <div className={styles.filterContainer}>
        <div className={styles.filterContent}>
          <InputBox
            name={"search box"}
            size="lg"
            placeholder="search transactions..."
          ></InputBox>
          <select>
            <option>All Types</option>
            <option>Income</option>
            <option>Expense</option>
          </select>
          <select>
            {/* Has to be dynamic from API */}
            <option>All Categories</option>
            <option>Groceries</option>
            <option>Phone Bills</option>
            <option>Insurance</option>
          </select>
          <label>Rows per page:</label>
          <select title="page count">
            <option>5</option>
            <option>10</option>
            <option>15</option>
            <option>20</option>
          </select>
          {/* Add date filter */}
          <Button text={"Go"} size="sm"></Button>
        </div>
      </div>
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
                    {txn.transactionType === "expense" ? "Expense" : "Income"}
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
      <Pagination totalPages={2} />
    </>
  );
}
