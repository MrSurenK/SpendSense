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
  const {
    data: topSpendData,
    error: topSpendError,
    isLoading: isTopSpendLoading,
  } = useGetTopFiveSpendQuery({
    month: 8,
    year: currYear,
  });

  const topFiveList = topSpendData?.data;

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
                <div className={styles.titleAndCurrency}>
                  <h2 className={styles.cardTitle}>Top Spend</h2>
                  <h2>$</h2>
                </div>
                {isTopSpendLoading ? (
                  <div className={styles.loaderPosition}>
                    <div className="loader"></div>
                  </div>
                ) : topSpendError ? (
                  <div className={styles.errorPosition}>
                    <p>⚠️ Unable to load data</p>
                    <small>
                      {topSpendError.message || "Something went wrong"}
                    </small>
                  </div>
                ) : (
                  <ul>
                    {topFiveList?.map((item, index) => (
                      <li key={index} className={styles.spanSpacing}>
                        <span>{item.title}</span>
                        <span>{item.amount.toFixed(2)}</span>
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
