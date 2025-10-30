import NavBar from "../../components/side-nav-bar/navBar";
import { useAppSelector } from "../../hooks/reduxHooks";
import { useGetTopFiveSpendQuery } from "../../redux/rtk-queries/dashboardService";
import styles from "./Dashboard.module.css";
import type { DashboardApiResponse } from "../../redux/rtk-queries/dashboardService";

export function Dashboard() {
  //Get curr month and year for dashboard data
  const today = new Date();
  const currYear = today.getFullYear();
  const currMth = today.getMonth();

  //Call API to display top list
  const { data, error, isLoading } = useGetTopFiveSpendQuery({
    month: 8,
    year: currYear,
  });

  const topFiveList = data?.data;

  // -- protected component -- //
  const loginInfo = useAppSelector((state) => state.auth);

  return (
    <>
      {loginInfo.isLoggedIn && (
        <>
          <div className="pageTitle">Dashboard</div>
          <div className={styles.cardsContainer}>
            <div className={styles.card}>
              <h2 className={styles.cardTitle}>Income</h2>
            </div>
            <div className={styles.card}>
              <div className={styles.titleAndListSpacing}>
                <h2 className={styles.cardTitle}>Top Spend</h2>
                {isLoading ? (
                  <div className="loader"></div>
                ) : (
                  <ul>
                    {topFiveList?.map((item, index) => (
                      <li key={index} className={styles.spanSpacing}>
                        <span>{item.title}</span>
                        <span>{item.amount}</span>
                      </li>
                    ))}
                  </ul>
                )}
              </div>
            </div>
            <div className={styles.card}>
              <h2 className={styles.cardTitle}> Top Subscriptions</h2>
            </div>
          </div>
          <div>Chart(s)</div>
        </>
      )}
    </>
  );
}
