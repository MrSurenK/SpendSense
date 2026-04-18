import style from "./EditTxnModal.module.css";
import Button from "../btn/Button";
import { useState, useMemo, useEffect } from "react";
import {
  type EditTransactionRequest,
  type TransactionRow,
  useEditTransactionsMutation,
  useGetCategoriesWithFullResQuery,
} from "../../redux/rtk-queries/transactionService";

type Props = {
  setOpenEditModal: (open: boolean) => void;
  transaction: TransactionRow;
};

type FormFields = {
  amount: string;
  title: string;
  remarks: string;
  recurring: boolean;
  date: string; //format date field to dd-MM-yyyy
  categoryId: number;
};

function toInputDate(dateStr: string) {
  if (!dateStr) return "";
  const trimmed = dateStr.trim();

  // Backend may return dd-MM-yyyy for some responses.
  if (/^\d{2}-\d{2}-\d{4}$/.test(trimmed)) {
    const [dd, mm, yyyy] = trimmed.split("-");
    return `${yyyy}-${mm}-${dd}`;
  }

  return trimmed.slice(0, 10);
}

function toDdMmYyyy(dateStr: string) {
  if (!dateStr) return "";
  const [yyyy, mm, dd] = dateStr.split("-");
  return `${dd}-${mm}-${yyyy}`;
}

export default function EditTxnModal({ setOpenEditModal, transaction }: Props) {
  //Get the current form details
  const initialForm = useMemo<FormFields>(
    () => ({
      amount: transaction.amount.toFixed(2),
      title: transaction.title,
      remarks: transaction.remarks,
      recurring: transaction.recurring,
      date: toInputDate(transaction.transactionDate),
      categoryId: transaction.catId,
    }),
    [transaction],
  );

  //States to handle form fields -- all fields are optional. If empty do not send to API
  const [fields, setFields] = useState<FormFields>(initialForm);
  const [dirty, setDirty] = useState<
    Partial<Record<keyof FormFields, boolean>>
  >({});

  useEffect(() => {
    setFields(initialForm);
    setDirty({});
  }, [initialForm]);

  const [editTransaction, { isLoading }] = useEditTransactionsMutation();
  const { data: catData } = useGetCategoriesWithFullResQuery();

  const handleSubmit = async (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();

    const payload: EditTransactionRequest = {};

    if (dirty.amount) {
      const parsed = Number(fields.amount);
      if (!Number.isNaN(parsed)) payload.amount = parsed;
    }
    if (dirty.title) payload.title = fields.title.trim();
    if (dirty.remarks) payload.remarks = fields.remarks.trim();
    if (dirty.recurring) payload.recurring = fields.recurring;
    if (dirty.date) payload.date = toDdMmYyyy(fields.date);
    if (dirty.categoryId) payload.categoryId = fields.categoryId;

    // Nothing changed; no API call needed.
    if (Object.keys(payload).length === 0) {
      setOpenEditModal(false);
      return;
    }

    try {
      await editTransaction({ id: transaction.id, body: payload }).unwrap();
      setOpenEditModal(false);
    } catch (error) {
      console.error("Failed to edit transaction", error);
    }
  };

  const handleChange = (
    e: React.ChangeEvent<
      HTMLInputElement | HTMLTextAreaElement | HTMLSelectElement
    >,
  ) => {
    const { name, value } = e.target;

    let nextValue: string | number | boolean = value;

    if (name === "amount") {
      // Allow only up to 2 decimals while user types.
      if (!/^\d*(\.\d{0,2})?$/.test(value)) return;
      nextValue = value;
    } else if (name === "recurring") {
      nextValue = value === "true";
    } else if (name === "categoryId") {
      nextValue = Number(value);
    }

    setFields((prev) => ({ ...prev, [name]: nextValue }) as FormFields);

    //Check only for the fields that changes from the original
    setDirty((prev) => ({
      ...prev,
      [name]: nextValue !== initialForm[name as keyof FormFields],
    }));
  };

  //handleSubmit calls API and passes changed fields to request

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
        <form className={style.form} onSubmit={handleSubmit}>
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
                value={fields.amount}
                step="0.01"
                min="0"
                onChange={handleChange}
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
                value={fields.title}
                onChange={handleChange}
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
                value={fields.remarks}
                onChange={handleChange}
              />
            </div>
          </div>

          {/* Recurring */}
          <div className={style.formRow}>
            <label>Recurring</label>
            <div className={style.radioGroup}>
              <label>
                <input
                  type="radio"
                  name="recurring"
                  value="true"
                  checked={fields.recurring === true}
                  onChange={handleChange}
                />
                Yes
              </label>
              <label>
                <input
                  type="radio"
                  name="recurring"
                  value="false"
                  checked={fields.recurring === false}
                  onChange={handleChange}
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
              <input
                id="edit-date"
                type="date"
                name="date"
                value={fields.date}
                onChange={handleChange}
              />
            </div>
          </div>

          {/* Category */}
          <div className={style.formRow}>
            <label htmlFor="edit-category">Category</label>
            <div className={style.fieldStack}>
              <div className={style.select}>
                <select
                  id="edit-category"
                  name="categoryId"
                  value={fields.categoryId}
                  onChange={handleChange}
                >
                  {catData?.data?.map((cat) => (
                    <option key={cat.id} value={cat.id}>
                      {cat.name} ({cat.transactionType})
                    </option>
                  ))}
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
            <Button
              size="sm"
              text={isLoading ? "Saving..." : "Save Changes"}
              type="submit"
              disabled={isLoading}
            />
          </div>
        </form>
      </div>
    </div>
  );
}
