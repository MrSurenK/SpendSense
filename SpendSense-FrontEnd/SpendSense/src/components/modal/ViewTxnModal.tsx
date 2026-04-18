import style from "./ViewTxnModal.module.css";
import type { TransactionRow } from "../../redux/rtk-queries/transactionService";

type Props = {
  setOpenViewModal: (open: boolean) => void;
  transaction: TransactionRow;
};

function formatCurrency(
  amount: number,
  transactionType: TransactionRow["transactionType"],
) {
  const prefix = transactionType === "expense" ? "-" : "+";
  return `${prefix}$${amount.toFixed(2)}`;
}

function formatDate(dateValue?: string | Date | null) {
  if (!dateValue) return "";

  const parsedDate = new Date(dateValue);

  if (Number.isNaN(parsedDate.getTime())) {
    return "";
  }

  return parsedDate.toLocaleDateString("en-US", {
    year: "numeric",
    month: "short",
    day: "2-digit",
  });
}

function formatDateTime(dateValue?: string | Date | null) {
  if (!dateValue) return "";

  const parsedDate = new Date(dateValue);

  if (Number.isNaN(parsedDate.getTime())) {
    return "";
  }

  return parsedDate.toLocaleString("en-US", {
    year: "numeric",
    month: "short",
    day: "2-digit",
    hour: "numeric",
    minute: "2-digit",
  });
}

export default function ViewTxnModal({ setOpenViewModal, transaction }: Props) {
  const amountTone =
    transaction.transactionType === "expense" ? style.expense : style.income;
  const formattedTransactionDate = formatDate(transaction.transactionDate);
  const formattedNextDueDate = formatDate(transaction.nextDueDate);
  const formattedLastUpdated = formatDateTime(transaction.lastUpdated);

  return (
    <div className={style.modalPos}>
      <div
        className={style.modal}
        role="dialog"
        aria-modal="true"
        aria-labelledby="view-transaction-title"
      >
        <div className={style.closeBtn}>
          <button
            type="button"
            aria-label="Close"
            onClick={() => setOpenViewModal(false)}
          >
            X
          </button>
        </div>

        <header className={style.header}>
          <div>
            <p className={style.eyebrow}>Transaction details</p>
            <h1 id="view-transaction-title" className={style.title}>
              {transaction.title}
            </h1>
            <div className={style.amountBlock}>
              <span className={style.amountLabel}>Amount</span>
              <strong className={`${style.amountValue} ${amountTone}`}>
                {formatCurrency(
                  transaction.amount,
                  transaction.transactionType,
                )}
              </strong>
            </div>
            {formattedTransactionDate && (
              <p className={style.subtext}>{formattedTransactionDate}</p>
            )}
          </div>
        </header>

        <div className={style.metaRow}>
          <span className={style.metaPill}>
            {transaction.transactionType === "expense" ? "Expense" : "Income"}
          </span>
          <span className={style.metaPill}>
            {transaction.recurring ? "Recurring" : "One-time"}
          </span>
        </div>

        <section className={style.section} aria-label="Transaction information">
          <p className={style.sectionLabel}>Overview</p>
          <dl className={style.detailsGrid}>
            <div className={`${style.row} ${style.rowWide}`}>
              <dt>Transaction name</dt>
              <dd>{transaction.title}</dd>
            </div>
            <div className={style.row}>
              <dt>Category</dt>
              <dd>{transaction.catName}</dd>
            </div>
            <div className={style.row}>
              <dt>Transaction date</dt>
              <dd>{formattedTransactionDate}</dd>
            </div>
            <div className={style.row}>
              <dt>Next due date</dt>
              <dd>{formattedNextDueDate}</dd>
            </div>
            <div className={style.row}>
              <dt>Last updated</dt>
              <dd>{formattedLastUpdated}</dd>
            </div>
          </dl>
        </section>

        <section className={style.section} aria-label="Remarks">
          <p className={style.sectionLabel}>Remarks</p>
          <p className={style.notesValue}>{transaction.remarks}</p>
        </section>
      </div>
    </div>
  );
}
