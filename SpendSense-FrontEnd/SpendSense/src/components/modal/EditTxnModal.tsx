import style from "./EditTxnModal.module.css";
import Button from "../btn/Button";

type Props = {
  setOpenEditModal: (open: boolean) => void;
};

export default function EditTxnModal({ setOpenEditModal }: Props) {
  return (
    <div className={style.modalPos}>
      <div className={style.modal}>
        {/* ── Header ── */}
        <div className={style.header}>
          <h1>Edit Transaction</h1>
          <div className={style.closeBtn}>
            <button
              type="button"
              aria-label="Close"
              onClick={() => setOpenEditModal(false)}
            >
              X
            </button>
          </div>
        </div>

        {/* ── Form ── */}
        <form className={style.form}>
          {/* Amount */}
          <div className={style.formRow}>
            <label htmlFor="edit-amount">Amount</label>
            <div className={style.amountGroup}>
              <span className={style.dollarSign}>$</span>
              <input
                id="edit-amount"
                type="number"
                name="amount"
                placeholder="0.00"
                step="0.01"
                min="0"
                onInput={(e) => {
                  const input = e.currentTarget;
                  const val = input.value;
                  const dotIndex = val.indexOf(".");
                  if (dotIndex !== -1 && val.length - dotIndex - 1 > 2) {
                    input.value = val.slice(0, dotIndex + 3);
                  }
                }}
              />
            </div>
          </div>

          {/* Title */}
          <div className={style.formRow}>
            <label htmlFor="edit-title">Title</label>
            <div className={style.fieldStack}>
              <input
                id="edit-title"
                type="text"
                name="title"
                placeholder="e.g. Groceries"
              />
            </div>
          </div>

          {/* Remarks */}
          <div className={style.formRow}>
            <label htmlFor="edit-remarks">Remarks</label>
            <div className={style.fieldStack}>
              <textarea
                id="edit-remarks"
                name="remarks"
                className={style.remarks}
                placeholder="Optional notes..."
                rows={2}
              />
            </div>
          </div>

          {/* Recurring */}
          <div className={style.formRow}>
            <label>Recurring</label>
            <div className={style.radioGroup}>
              <label>
                <input type="radio" name="recurring" value="true" />
                Yes
              </label>
              <label>
                <input
                  type="radio"
                  name="recurring"
                  value="false"
                  defaultChecked
                />
                No
              </label>
            </div>
          </div>

          {/* Date */}
          <div className={style.formRow}>
            <label htmlFor="edit-date">
              Transaction
              <br />
              Date
            </label>
            <div className={style.fieldStack}>
              <input id="edit-date" type="date" name="date" />
            </div>
          </div>

          {/* Category */}
          <div className={style.formRow}>
            <label htmlFor="edit-category">Category</label>
            <div className={style.fieldStack}>
              <div className={style.select}>
                <select id="edit-category" name="categoryId">
                  <option value="" disabled>
                    Select a category
                  </option>
                  {/* Map categories here */}
                </select>
                <svg
                  viewBox="0 0 12 8"
                  xmlns="http://www.w3.org/2000/svg"
                  aria-hidden="true"
                >
                  <path
                    d="M1 2.5l5 4 5-4"
                    stroke="currentColor"
                    strokeWidth="2"
                    fill="none"
                    strokeLinecap="round"
                    strokeLinejoin="round"
                  />
                </svg>
              </div>
            </div>
          </div>

          {/* Submit */}
          <div className={style.actions}>
            <Button size="sm" text="Save Changes" type="submit" />
          </div>
        </form>
      </div>
    </div>
  );
}
