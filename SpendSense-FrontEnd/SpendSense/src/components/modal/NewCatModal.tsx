import Button from "../btn/Button";
import style from "./NewCatModal.module.css";

export default function NewCatModal() {
  return (
    <>
      <div>
        <div className={style.modalPos}>
          <div className={style.modal}>
            <div className={style.header}>
              <h1>Add New Category</h1>
              <div className={style.closeBtn}>
                <button type="button" aria-label="Close">
                  X
                </button>
              </div>
            </div>
            {/* input form */}
            <form>
              <div className={style.formRow}>
                <label htmlFor="newCat">Category:</label>
                <input id="newCat" type="text" name="newCat" />
              </div>

              <div className={style.formRow}>
                <label htmlFor="type">Type:</label>
                <div className={style.select}>
                  <select id="type" name="type" defaultValue="">
                    <option value="" disabled>
                      Pick category type
                    </option>
                    <option value="expense">Expense</option>
                    <option value="income">Income</option>
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

              <div className={style.actions}>
                <Button size="sm" text="submit" />
              </div>
            </form>
          </div>
        </div>
      </div>
    </>
  );
}
