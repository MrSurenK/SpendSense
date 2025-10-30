import Button from "../../components/btn/Button.tsx";
import InputBox from "../../components/input-box/InputBox";
import styles from "./LogIn.module.css";
import { NavLink, useNavigate } from "react-router";
import { useEffect, useState } from "react";
import { useLoginMutation } from "../../redux/rtk-queries/authService.ts";
import { useAppDispatch, useAppSelector } from "../../hooks/reduxHooks.ts";
import { loginState, type LoginPayload } from "../../redux/slices/authSlice.ts";

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
    return obj && typeof obj.message === "string";
  }

  const handleChange = (e: React.ChangeEvent<HTMLInputElement>): void => {
    const { name, value } = e.target;
    // console.log(`${name}:${value}`);
    setFormData((prev) => ({
      ...prev,
      [name as keyof LogInForm]: value,
    }));
  };

  const [login, { data, error, isLoading, isSuccess }] = useLoginMutation();

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    try {
      // wait for the login mutation to finish and unwrap the result
      const result = await login(formData).unwrap();
      if (result) {
        // update redux state AFTER login mutation resolves
        const userInfo: LoginPayload = {
          userId: result.userId,
          username: result.username,
          lastLogin: new Date(result.lastLogin),
        };
        dispatch(loginState(userInfo));

        // navigate only after Redux state updated
        navigate("/dashboard");
      }
    } catch (err) {
      console.error("Login failed:", err);
    }
  };

  //Manage log in state
  const loginInfo = useAppSelector((state) => state.auth);
  const dispatch = useAppDispatch();
  const navigate = useNavigate();

  useEffect(() => {
    if (isSuccess && data) {
      //update redux login state

      const userInfo: LoginPayload = {
        userId: data.userId,
        username: data.username,
        lastLogin: new Date(data.lastLogin),
      };

      dispatch(loginState(userInfo));
    }
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [isSuccess, data, dispatch]);

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
