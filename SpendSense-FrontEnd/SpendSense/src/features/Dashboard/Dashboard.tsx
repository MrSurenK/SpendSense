import NavBar from "../../components/side-nav-bar/navBar";
import { useAppSelector } from "../../hooks/reduxHooks";
import styles from "./Dashboard.module.css";

export function Dashboard() {
  // -- protected component -- //
  // const loginInfo = useAppSelector((state) => state.auth);

  // return <>{loginInfo.isLoggedIn && <h1>Hello {loginInfo.username} !</h1>}</>;
  return (
    <>
      <div className="pageTitle">Dashboard</div>
      <div className={styles.cardsContainer}>
        <div className={styles.card}>
          <h2 className={styles.cardTitle}>Income</h2>
        </div>
        <div className={styles.card}>
          <h2 className={styles.cardTitle}>Top Spend</h2>
        </div>
        <div className={styles.card}>
          <h2 className={styles.cardTitle}> Top Subscriptions</h2>
        </div>
      </div>
      <div>Chart(s)</div>
    </>
  );
}
