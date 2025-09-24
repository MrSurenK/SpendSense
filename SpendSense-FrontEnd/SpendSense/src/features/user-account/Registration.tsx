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

  const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const { name, value } = e.target;
    // console.log(value);
    setFormData((prev) => ({
      ...prev,
      [name as keyof RegistrationForm]: value,
    }));
  };

  const handleSubmit = (e: React.FormEvent) => {
    e.preventDefault();
    // eslint-disable-next-line @typescript-eslint/no-unused-vars
    const { matchPassword, ...restOfFields } = formData;
    const payload: Payload = {
      ...restOfFields,
      dob: formData.dob.split("-").reverse().join("-"),
    };
    console.log(payload);
  };

  return (
    <>
      <div className={`${styles.formContainer} ${styles.buttonPos}`}>
        <h1 className={`${styles.header}`}>Registration</h1>
        <form className={styles.form}>
          <div>
            <label>Email</label>
            <InputBox
              size="md"
              type="text"
              name="email"
              onChange={handleChange}
            ></InputBox>
          </div>
          <div>
            <label>Username</label>
            <InputBox
              size="md"
              type="text"
              name="userName"
              onChange={handleChange}
            ></InputBox>
          </div>
          <div>
            <label>First Name</label>
            <InputBox
              size="md"
              type="text"
              name="firstName"
              onChange={handleChange}
            ></InputBox>
          </div>
          <div>
            <label>Last Name</label>
            <InputBox
              size="md"
              type="text"
              name="lastName"
              onChange={handleChange}
            ></InputBox>
          </div>
          <div>
            <label>Occupation</label>
            <InputBox
              size="md"
              type="text"
              name="occupation"
              onChange={handleChange}
            ></InputBox>
          </div>
          <div className={`${styles.dob}`}>
            <label>Date of Birth</label>
            <InputBox
              size="sm"
              type="date"
              name="dob"
              onChange={handleChange}
            ></InputBox>
          </div>
          <div>
            <label>New Password</label>
            <InputBox size="md" type="password" name="password"></InputBox>
          </div>
          <div>
            <label>Repeat password</label>
            <InputBox
              size="md"
              type="password"
              name="matchPassword"
              onChange={handleChange}
            ></InputBox>
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
