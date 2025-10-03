import Button from "../../components/btn/Button.tsx";
import Input from "../../components/input-box/InputBox.tsx";
import styles from "./LogIn.module.css";
import { NavLink } from "react-router";

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
              <Input placeholder="johnSmith@gmail.com" size="md"></Input>
            </div>
            <div className={styles.fieldsGap}>
              <label className={styles.labels}>Password</label>
              <Input type="password" placeholder="password" size="md"></Input>
            </div>
          </div>
          <div className={styles.bottomElements}>
            <div>
              <Button text="Sign In" size="sm"></Button>
            </div>
            <div className={styles.spacing}>
              <nav>
                <NavLink className={styles.link} to="/register">
                  Not a member? Sign up here!
                </NavLink>
              </nav>
            </div>
          </div>
        </div>
        <div className={styles.background}></div>
      </div>
    </>
  );
}

export default LogIn;
