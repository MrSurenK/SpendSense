import Button from "../../components/btn/Button.tsx";
import InputBox from "../../components/input-box/InputBox";
import styles from "./LogIn.module.css";
import { NavLink } from "react-router";
import { useState } from "react";
import { useLoginMutation } from "../../redux/rtk-queries/authService.ts";

interface LogInForm {
  username: string;
  password: string;
}

function LogIn() {
  //Manage form states
  const [formData, setFormData] = useState<LogInForm>({
    username: "",
    password: "",
  });

  interface BackendError {
    description: string;
  }

  function isBackendError(obj: any): obj is BackendError {
    return obj && typeof obj.description === "string";
  }

  //Log In API states
  // const [loading, setLoading] = useState<boolean>(false);
  // const [error, setError] = useState<Error | null>(null);

  const handleChange = (e: React.ChangeEvent<HTMLInputElement>): void => {
    const { name, value } = e.target;
    // console.log(`${name}:${value}`);
    setFormData((prev) => ({
      ...prev,
      [name as keyof LogInForm]: value,
    }));
  };

  const [login, { data, error, isLoading }] = useLoginMutation();

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault(); // stops default GET request
    await login(formData); // call RTK Query mutation
  };

  //create Sign in form
  return (
    <>
      <div className={styles.browserContainer}>
        <form className={styles.card} onSubmit={handleSubmit}>
          {data && <div style={{ color: "green" }}>{data.message}</div>}
          {error && "data" in error && isBackendError(error.data) && (
            <div style={{ color: "red" }}>
              <div>{error.data.description}</div>
            </div>
          )}
          <div className={`${styles.spacing} ${styles.signIn}`}>
            <span>Sign In</span>
          </div>
          <div>
            <div className={styles.fieldsGap}>
              <label className={styles.labels}>Username</label>
              <InputBox
                name="username"
                size="md"
                type="text"
                required={true}
                onChange={handleChange}
              ></InputBox>
            </div>
            <div className={styles.fieldsGap}>
              <label className={styles.labels}>Password</label>
              <InputBox
                name="password"
                type="password"
                size="md"
                required={true}
                onChange={handleChange}
              ></InputBox>
            </div>
          </div>
          <div className={styles.bottomElements}>
            <div>
              <Button text="Sign In" size="sm" disabled={isLoading}></Button>
            </div>
            <div className={styles.spacing}>
              <nav>
                <NavLink className={styles.link} to="/register">
                  Not a member? Sign up here!
                </NavLink>
              </nav>
            </div>
          </div>
        </form>
        <div className={styles.background}></div>
      </div>
    </>
  );
}

export default LogIn;
