import Button from "../../components/btn/Button.tsx";
import InputBox from "../../components/input-box/InputBox";
import styles from "./LogIn.module.css";
import { NavLink } from "react-router";
import { useState } from "react";

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

  //Log In API states 
  const [loading, setLoading] = useState<boolean>(false);
  const [error, setError] = useState<Error | null>(null);

  const handleChange = (e: React.ChangeEvent<HTMLInputElement>): void => {
    const { name, value } = e.target;
    // console.log(`${name}:${value}`);
    setFormData((prev) => ({
      ...prev,
      [name as keyof LogInForm]: value,
    }));
  };

  async function handleSubmit = (e: React.FormEvent): void => {
    e.preventDefault();
    const payload = formData;
    try{
      const res = await fetch("http://127.0.0.1:8080/auth/login",{
      method:"POST",
      headers:{
      "Content-Type":"application/json",
      },
      body:JSON.stringify(payload),
    })
    if(!res.ok){
      const httpErrorMsg = await res.text();
      throw new Error(
        `HTTP Error: ${res.status}, body:${httpErrorMsg}`
      ) 
    }
    const successMsg = await res.json(); 


    }catch(err){
      
    }
    

  };

  //create Sign in form
  return (
    <>
      <div className={styles.browserContainer}>
        <form className={styles.card}>
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
        </form>
        <div className={styles.background}></div>
      </div>
    </>
  );
}

export default LogIn;
