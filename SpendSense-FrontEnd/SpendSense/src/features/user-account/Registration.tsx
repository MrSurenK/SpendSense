import styles from "./Registration.module.css";
import InputBox from "../../components/input-box/InputBox";
import Button from "../../components/btn/Button";
import { useState } from "react";

interface RegistrationForm {
  email: string;
  userName: string;
  firstName: string;
  lastName: string;
  occupation: string;
  dob: string;
  password: string;
  matchPassword: string; // for validation only
}

type Payload = Omit<RegistrationForm, "matchPassword">;

function Registration() {
  //Construct the JSON from the form fields to pass to API
  const [formData, setFormData] = useState<RegistrationForm>({
    email: "",
    userName: "",
    firstName: "",
    lastName: "",
    occupation: "",
    dob: "",
    password: "",
    matchPassword: "",
  });

  const [matchError, setMatchError] = useState<string | null>(null);
  const [passSizeError, setPassSizeError] = useState<string | null>(null);
  const [requiredCheck, setRequiredCheck] = useState<string | null>(null);

  //API states

  const [loading, setLoading] = useState<boolean>(null);
  const [response, setResponse] = useState<T | null>(null);
  const [submitError, setSubmitError] = useState<Error | null>(null);

  async function postForm<T>(options: RequestInit) {
    setLoading(true);
    try {
      const res = await fetch("http://127.0.0.1:8080/auth/register", options);
      if (!res.ok) {
        //Catch HTTP errors
        const httpError = await res.text();
        console.log(`HTTP error. Status: ${res.status}, body:${httpError}`);
        throw new Error(`HTTP error. Status: ${res.status}, body:${httpError}`);
      }
      const result = await res.json().catch(() => null);
      setResponse(result as T);
    } catch (err) {
      console.log(err);
      setSubmitError(
        err instanceof Error ? err : new Error("Unknown error occurred")
      );
    } finally {
      setLoading(false);
    }
  }

  const checkPasswordMatch = (): boolean => {
    if (formData.matchPassword != formData.password) {
      setMatchError("Passwords do not match");
      return false;
    }
    setMatchError(null);
    return true;
  };

  const checkPasswordSize = (): boolean => {
    const size = formData.password.length;
    if (size < 8) {
      setPassSizeError("Password has to be a minmum of 8 characters");
      return false;
    }
    setPassSizeError(null);
    return true;
  };

  const handleBlur = (e: React.FocusEvent<HTMLInputElement>) => {
    if (e.target.name === "matchPassword") {
      checkPasswordMatch();
    }
    if (e.target.name === "password") {
      checkPasswordSize();
    }
  };

  const handleChange = (e: React.ChangeEvent<HTMLInputElement>): void => {
    const { name, value } = e.target;
    // console.log(value);
    setFormData((prev) => ({
      ...prev,
      [name as keyof RegistrationForm]: value,
    }));
  };

  const handleSubmit = (e: React.FormEvent): void => {
    e.preventDefault();
    //check if all fields have been filled
    if (
      !formData.email ||
      !formData.dob ||
      !formData.firstName ||
      !formData.lastName ||
      !formData.occupation ||
      !formData.password ||
      !formData.userName ||
      !formData.matchPassword
    ) {
      setRequiredCheck("Please fill in all required fields!");
      return;
    } else {
      setRequiredCheck(null);
    }

    let payload: Payload = formData;
    if (checkPasswordMatch() && checkPasswordSize()) {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const { matchPassword, ...restOfFields } = formData;
      payload = {
        ...restOfFields,
        dob: formData.dob.split("-").reverse().join("-"),
      };
      console.log(payload);
    }

    const options: RequestInit = {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify(payload),
    };

    postForm(options);
  };

  return (
    <>
      <div className={`${styles.formContainer} ${styles.buttonPos}`}>
        <h1 className={`${styles.header}`}>Registration</h1>
        <form className={styles.form}>
          {requiredCheck && <p style={{ color: "red" }}>{requiredCheck}</p>}
          <div>
            <label>
              Email<span className={styles.required}>*</span>
            </label>
            <InputBox
              size="md"
              type="text"
              name="email"
              onChange={handleChange}
              required={true}
            ></InputBox>
          </div>
          <div>
            <label>
              Username <span className={styles.required}>*</span>
            </label>
            <InputBox
              size="md"
              type="text"
              name="userName"
              onChange={handleChange}
              required={true}
            ></InputBox>
          </div>
          <div>
            <label>
              First Name<span className={styles.required}>*</span>
            </label>
            <InputBox
              size="md"
              type="text"
              name="firstName"
              onChange={handleChange}
              required={true}
            ></InputBox>
          </div>
          <div>
            <label>
              Last Name<span className={styles.required}>*</span>
            </label>
            <InputBox
              size="md"
              type="text"
              name="lastName"
              onChange={handleChange}
              required={true}
            ></InputBox>
          </div>
          <div>
            <label>
              Occupation<span className={styles.required}>*</span>
            </label>
            <InputBox
              size="md"
              type="text"
              name="occupation"
              onChange={handleChange}
              required={true}
            ></InputBox>
          </div>
          <div className={`${styles.dob}`}>
            <label>
              Date of Birth<span className={styles.required}>*</span>
            </label>
            <InputBox
              size="sm"
              type="date"
              name="dob"
              onChange={handleChange}
              required={true}
            ></InputBox>
          </div>
          <div>
            <label>
              Password<span className={styles.required}>*</span>
            </label>
            <InputBox
              size="md"
              type="password"
              name="password"
              onChange={handleChange}
              onBlur={handleBlur}
              required={true}
            ></InputBox>
            {passSizeError && <p style={{ color: "red" }}>{passSizeError}</p>}
          </div>
          <div>
            <label>
              Repeat password<span className={styles.required}>*</span>
            </label>
            <InputBox
              size="md"
              type="password"
              name="matchPassword"
              onChange={handleChange}
              onBlur={handleBlur}
              required={true}
            ></InputBox>
            {matchError && <p style={{ color: "red" }}>{matchError}</p>}
          </div>
        </form>
        <div className={`${styles.buttonPos}`}>
          <Button
            text="Submit"
            size="sm"
            type="submit"
            onClick={handleSubmit}
          ></Button>
        </div>
      </div>
    </>
  );
}

export default Registration;
