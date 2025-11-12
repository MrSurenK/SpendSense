import Button from "../../../components/btn/Button";
import InputBox from "../../../components/input-box/InputBox";
import styles from "./ViewAllTxn.module.css";

export function ViewAllTxn() {
  /*
    ToDo: 
    1. Filters Section
    2. Table formatting 
    3. Responsive pagination Sections(Pagination is server side)
        - Get total pages in API and display as last page in pagination UI 
    4. Import data 
    5. Add button functionality for delete entry
    6. Modal to confirm deletion 
    7. UseEffect to update table data after delete
    */
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
              <th>Last Updated</th>
              <th>Actions</th>
            </tr>
          </thead>
          <tbody>
            <tr>
              <td>
                <span>01/08/2025</span>
              </td>
              <td>
                <div>Groceries</div>
                <div className={styles.remarks}>
                  Bought groceries from the store
                </div>
              </td>
              <td>
                <span>+$500</span>
              </td>
              <td>
                <span>Groceries</span>
              </td>
              <td>
                <span>Expense</span>
              </td>
              <td>
                <span>Yes</span>
              </td>
              <td>
                <span>01/02/2025</span>
              </td>
              <td>
                <div>
                  <Button text={"Edit"} size={"sm"}></Button>
                  <Button text={"Delete"} size={"sm"}></Button>
                  <Button text={"View"} size={"sm"}></Button>
                </div>
              </td>
            </tr>
          </tbody>
        </table>
      </div>
      <div>Pagination</div>
    </>
  );
}
