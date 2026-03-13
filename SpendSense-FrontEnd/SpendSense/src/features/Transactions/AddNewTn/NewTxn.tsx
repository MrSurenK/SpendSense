import styles from "./NewTxn.module.css";
import InputBox from "../../../components/input-box/InputBox";
import Button from "../../../components/btn/Button";
import { useState } from "react";
import {
  useAddNewTxnMutation,
  useGetCategoriesWithFullResQuery,
  type NewTransactionRequest,
  type TransactionType,
} from "../../../redux/rtk-queries/transactionService";

export default function NewTxn() {
  //State to manage form information

  const [newTxnForm, setNewTxnForm] = useState<NewTransactionRequest>({
    amount: 0,
    title: "",
    remarks: "",
    recurring: false,
    date: new Date().toISOString().slice(0, 10),
    category: 0,
  });

  const [transactionType, setTransactionType] = useState<TransactionType>();
  const hasSelectedCategory = newTxnForm.category !== 0;

  const handleCatChange = (e: React.ChangeEvent<HTMLSelectElement>) => {
    const rawValue = e.target.value;
    const selectedCategoryId = rawValue === "" ? 0 : Number(rawValue);

    // Update form state with selected category id.
    setNewTxnForm((prev) => ({ ...prev, category: selectedCategoryId }));

    //Update transactionType state to display the correct state
    const selectedCat = catData?.data?.find(
      (cat) => cat.id === selectedCategoryId,
    );
    setTransactionType(selectedCat?.transactionType);
  };

  const handleInputChange = (
    e: React.ChangeEvent<
      HTMLInputElement | HTMLTextAreaElement | HTMLSelectElement
    >,
  ) => {
    const { name, value } = e.target;

    let parsedValue: string | number | boolean = value;

    if (name === "amount" || name === "category") {
      parsedValue = value === "" ? 0 : Number(value);
    } else if (name === "recurring") {
      parsedValue = value === "true";
    }

    setNewTxnForm((prev) => ({ ...prev, [name]: parsedValue }));
  };

  const handleSubmit = (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();
    try {
      // const response = await addNewTxn(newTxnForm).unwrap();
    } catch (error) {
      console.error("Failed to create transaction", error);
    }
    // console.log(newTxnForm);
  };

  //API call to get categories
  const { data: catData } = useGetCategoriesWithFullResQuery();

  const [addNewTxn, { isSuccess, isLoading, isError }] = useAddNewTxnMutation();

  //Todo: Categories is where the type is derived from so in the form user should not select type before selecting a category
  /*
   * If user creates a new categories ensure that the Cat API updates the state and calls again as a dependency in the service layer
   *
   */

  return (
    <div className={styles.pageBackground}>
      <div className={styles.receipt}>
        {/* ── Header ── */}
        <h1 className={styles.header}>New Transaction</h1>
        <p className={styles.storeName}>SpendSense Receipt</p>

        <hr className={styles.divider} />

        <form className={styles.form} onSubmit={handleSubmit}>
          {/* ── Transaction Type toggle ── */}
          <div className={styles.row}>
            <label>Type</label>
            <div
              className={styles.typeToggleWrap}
              data-tooltip={
                hasSelectedCategory
                  ? undefined
                  : "Select a category first or create a new one"
              }
            >
              <div className={styles.typeDisplay}>
                <div
                  className={`${styles.typeBox} ${
                    !hasSelectedCategory ? styles.typeBoxMuted : ""
                  } ${
                    transactionType === "expense"
                      ? styles.typeBoxActiveExpense
                      : ""
                  }`}
                >
                  Expense
                </div>
                <div
                  className={`${styles.typeBox} ${
                    !hasSelectedCategory ? styles.typeBoxMuted : ""
                  } ${
                    transactionType === "income"
                      ? styles.typeBoxActiveIncome
                      : ""
                  }`}
                >
                  Income
                </div>
              </div>
            </div>
          </div>
          {/* ── Amount ── */}
          <div className={styles.row}>
            <label>Amount</label>
            <div className={styles.amountGroup}>
              <span className={styles.dollarSign}>$</span>
              <input
                type="number"
                name="amount"
                placeholder="0.00"
                step="0.01"
                min="0"
                onChange={handleInputChange}
              />
            </div>
          </div>
          <hr className={styles.divider} />
          {/* ── Title ── */}
          <div className={styles.row}>
            <label>Title</label>
            <InputBox name="title" placeholder="e.g. Groceries" size="md" />
          </div>

          {/* ── Remarks ── */}
          <div className={styles.row}>
            <label>Remarks</label>
            <textarea
              name="remarks"
              className={styles.remarks}
              placeholder="Optional notes..."
              rows={2}
              onChange={handleInputChange}
            />
          </div>

          <hr className={styles.divider} />

          {/* ── Date ── */}
          <div className={styles.row}>
            <label>Date</label>
            <InputBox
              name="date"
              type="date"
              size="md"
              onChange={handleInputChange}
            />
          </div>

          {/* ── Category ── */}
          <div className={styles.row}>
            <label>Category</label>
            <div className={styles.selectWrap}>
              <select
                name="category"
                value={newTxnForm.category === 0 ? "" : newTxnForm.category}
                onChange={handleCatChange}
              >
                <option value="">Select a category</option>
                {catData?.data?.map((cat) => (
                  <option key={cat.id} value={cat.id}>
                    {cat.name} ({cat.transactionType})
                  </option>
                ))}
              </select>
            </div>
          </div>

          {/* ── Recurring ── */}
          <div className={styles.row}>
            <label>Recurring</label>
            <div className={styles.radioGroup}>
              <label>
                <input
                  type="radio"
                  name="recurring"
                  value="true"
                  onChange={handleInputChange}
                />
                Yes
              </label>
              <label>
                <input
                  type="radio"
                  name="recurring"
                  value="false"
                  defaultChecked
                  onChange={handleInputChange}
                />
                No
              </label>
            </div>
          </div>
          <hr className={styles.divider} />
          {/* ── Totals area (visual receipt touch) ── */}
          <div className={styles.totals}>
            <div className={styles.totalsRow}>
              <span>TOTAL</span>
              <span>$0.00</span>
            </div>
          </div>
          <hr className={styles.divider} />
          {/* ── Actions ── */}
          <div className={styles.footer}>
            <Button text="Submit Transaction" size="lg" type="submit" />
            <Button
              text="Add New Category"
              variant="btn-secondary"
              size="sm"
              type="button"
            />
          </div>
        </form>

        {/* ── Decorative barcode ── */}
        <div className={styles.barcode}>
          {Array.from({ length: 40 }).map((_, i) => (
            <span key={i} />
          ))}
        </div>
      </div>
    </div>
  );
}
