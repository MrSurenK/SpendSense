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
              <th>Actions</th>
              <th>Delete</th>
            </tr>
          </thead>
          <tbody>
            <tr>
              <td>
                <span>01/08/2025</span>
              </td>
              <td>
                <div>Groceries</div>
                <div className={styles.remarks}>World cup winners are here</div>
              </td>
              <td>
                <span>-$121.40</span>
              </td>
              <td>
                <span className={styles.words}>Groceriesssssssssssss</span>
              </td>
              <td>
                <span>Expense</span>
              </td>
              <td>
                <span>Yes</span>
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
            <tr>
              <td>
                <span>01/08/2025</span>
              </td>
              <td>
                <div>Dummy</div>
                <div className={styles.remarks}>World cup winners are here</div>
              </td>
              <td>
                <span>+$100000.40</span>
              </td>
              <td>
                <span className={styles.words}>Groceriesssssssssssss</span>
              </td>
              <td>
                <span>Income</span>
              </td>
              <td>
                <span>No</span>
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
          </tbody>
        </table>
      </div>
      <div>Pagination</div>
    </>
  );
}
