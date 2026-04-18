import Button from "../btn/Button";
import style from "./NewCatModal.module.css";
import {
  type NewCategoryRequest,
  useAddNewCatMutation,
  type TransactionType,
} from "../../redux/rtk-queries/transactionService";
import { useState } from "react";

type Props = {
  setOpenCatModal?: (open: boolean) => void;
};

//Use this type first as state is not properly set on first render
type NewCatForm = {
  name: string;
  transactionType: TransactionType | "";
};

type FormErrors = {
  name?: string;
  transactionType?: string;
  submit?: string;
};

//ToDo: update the states and handle open close and form elements interactivity.
//ToDo: Update the modal into the parent compoenent and ensure full functionality when sending front end to API

export default function NewCatModal({ setOpenCatModal }: Props) {
  const [form, setForm] = useState<NewCatForm>({
    name: "",
    transactionType: "",
  });
  const [errors, setErrors] = useState<FormErrors>({});

  //call the api
  const [addNewCat] = useAddNewCatMutation();

  const handleChange = (
    e: React.ChangeEvent<HTMLInputElement | HTMLSelectElement>,
  ) => {
    const { name, value } = e.target;
    setForm((prev) => ({
      ...prev,
      [name]: value,
    }));

    // Clear error state for a field as soon as the user updates it.
    setErrors((prev) => ({ ...prev, [name]: undefined, submit: undefined }));
  };

  const validateForm = () => {
    const nextErrors: FormErrors = {};

    if (!form.name.trim()) {
      nextErrors.name = "Category name is required.";
    }

    if (!form.transactionType) {
      nextErrors.transactionType = "Please select a category type.";
    }

    setErrors(nextErrors);
    return Object.keys(nextErrors).length === 0;
  };

  const handleSubmit = async (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();

    if (!validateForm() || form.transactionType === "") return;

    const payload: NewCategoryRequest = {
      name: form.name.trim(),
      transactionType: form.transactionType,
    };

    try {
      await addNewCat(payload).unwrap();
      setOpenCatModal?.(false); //close the modal if successful
    } catch (error) {
      console.error("Failed to create category", error);
      setErrors((prev) => ({
        ...prev,
        submit: "Unable to create category. Please try again.",
      }));
    }
  };

  return (
    <>
      <div>
        <div className={style.modalPos}>
          <div className={style.modal}>
            <div className={style.header}>
              <h1>Add New Category</h1>
              <div className={style.closeBtn}>
                <button
                  type="button"
                  aria-label="Close"
                  onClick={() => setOpenCatModal?.(false)}
                >
                  X
                </button>
              </div>
            </div>
            {/* input form */}
            <form onSubmit={handleSubmit}>
              <div className={style.formRow}>
                <label htmlFor="newCat">Category:</label>
                <div className={style.fieldStack}>
                  {errors.name && (
                    <p className={style.errorText}>{errors.name}</p>
                  )}
                  <input
                    id="newCat"
                    type="text"
                    name="name"
                    value={form.name}
                    onChange={handleChange}
                  />
                </div>
              </div>

              <div className={style.formRow}>
                <label htmlFor="type">Type:</label>
                <div className={style.fieldStack}>
                  {errors.transactionType && (
                    <p className={style.errorText}>{errors.transactionType}</p>
                  )}
                  <div className={style.select}>
                    <select
                      id="type"
                      name="transactionType"
                      value={form.transactionType}
                      onChange={handleChange}
                    >
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
              </div>
              {errors.submit && (
                <p className={style.errorText}>{errors.submit}</p>
              )}
              <div className={style.actions}>
                <Button size="sm" text="submit" type="submit" />
              </div>
            </form>
          </div>
        </div>
      </div>
    </>
  );
}
