import styles from "./Registration.module.css";
import InputBox from "../../components/input-box/InputBox";
import Button from "../../components/btn/Button";

function Registration() {
  return (
    <>
      <div className={`${styles.formContainer} ${styles.buttonPos}`}>
        <h1 className={`${styles.header}`}>Registration</h1>
        <div className={styles.form}>
          <div>
            <label>Email</label>
            <InputBox size="md" type="text"></InputBox>
          </div>
          <div>
            <label>Username</label>
            <InputBox size="md" type="text"></InputBox>
          </div>
          <div>
            <label>First Name</label>
            <InputBox size="md" type="text"></InputBox>
          </div>
          <div>
            <label>Last Name</label>
            <InputBox size="md" type="text"></InputBox>
          </div>
          <div>
            <label>Occupation</label>
            <InputBox size="md" type="text"></InputBox>
          </div>
          <div className={`${styles.dob}`}>
            <label>Date of Birth</label>
            <InputBox size="sm" type="date"></InputBox>
          </div>
          <div>
            <label>New Password</label>
            <InputBox size="md" type="password"></InputBox>
          </div>
          <div>
            <label>Repeat password</label>
            <InputBox size="md" type="password"></InputBox>
          </div>
        </div>
        <div className={`${styles.buttonPos}`}>
          <Button text="Submit" size="sm"></Button>
        </div>
      </div>
    </>
  );
}

export default Registration;
