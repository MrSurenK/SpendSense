import Button from "../../components/btn/Button.tsx";
import Input from "../../components/input-box/InputBox.tsx";
import styles from "./LogIn.module.css";

function LogIn() {
  //create Sign in form
  return (
    <>
      <div className={styles.browserContainer}>
        <div className={styles.card}>
          <div className={`${styles.spacing} ${styles.signIn}`}>
            <span>Sign In</span>
          </div>
          <div>
            <div className={styles.fieldsGap}>
              <label className={styles.labels}>Email</label>
              <Input placeholder="johnSmith@gmail.com" size="sm"></Input>
            </div>
            <div className={styles.fieldsGap}>
              <label className={styles.labels}>Password</label>
              <Input type="password" placeholder="password"></Input>
            </div>
          </div>
          <div>
            <Button text="Sign In" size="sm"></Button>
          </div>
          <div className={styles.spacing}>
            <a className={styles.link} href="#">
              Not a member? Sign up here!
            </a>
          </div>
        </div>
        <div className={styles.background}></div>
      </div>
    </>
  );
}

export default LogIn;
