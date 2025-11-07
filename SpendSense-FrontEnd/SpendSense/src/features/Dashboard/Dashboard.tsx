import NavBar from "../../components/side-nav-bar/navBar";
import { useAppSelector } from "../../hooks/reduxHooks";
import {
  useGetNetCashflowQuery,
  useGetTopFiveSpendQuery,
  useGetTopSubsQuery,
} from "../../redux/rtk-queries/dashboardService";
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
    month: 8, //toDo: remove hardcoded month and add currMth in here
    year: currYear,
  });

  const topFiveList = topSpendData?.data;

  //Call API to display top subscription spends
  const {
    data: subData,
    error: subError,
    isLoading: isSubLoading,
  } = useGetTopSubsQuery({ page: 0, size: 5 });

  const topSubs = subData?.data;

  //call API to display income details

  const {
    data: netCashFlow,
    error: cashFlowError,
    isLoading: isCashFlowLoading,
  } = useGetNetCashflowQuery({
    startDate: "2025-08-01",
    endDate: "2025-11-30",
  });

  const cashflowDetails = netCashFlow?.data;
  const resNetCashflow = cashflowDetails?.netCashflow;

  let cashflowElement;

  //format netCashflow string based on positive or negative value
  if (resNetCashflow != undefined && resNetCashflow > 0) {
    cashflowElement = (
      <h1 id={styles.netCashflowValue} className={styles.positiveCashflow}>
        +$ {resNetCashflow}
      </h1>
    );
  } else if (resNetCashflow != undefined && resNetCashflow < 0) {
    const cashflow = Math.abs(resNetCashflow);
    cashflowElement = (
      <h1 id={styles.netCashflowValue} className={styles.negativeCashflow}>
        -$ {cashflow}
      </h1>
    );
  }

  const resInflow = cashflowDetails?.totalInflow;
  const resOutflow = cashflowDetails?.totalOutflow;

  // -- protected component -- //
  const loginInfo = useAppSelector((state) => state.auth);

  return (
    <>
      {loginInfo.isLoggedIn && (
        <>
          <div className="pageTitle">Dashboard</div>
          <div className={styles.cardsContainer}>
            <div className={styles.card}>
              <div id={styles.netCashflowGrid}>
                <h1 id={styles.netCashflowHeader}>Net Cashflow</h1>
                {isCashFlowLoading ? (
                  <div className={styles.cashflowErrorAndLoader}>
                    <div className="loader"></div>
                  </div>
                ) : cashFlowError ? (
                  <div className={styles.cashflowErrorAndLoader}>
                    <p>⚠️ Unable to load data</p>
                    <small>
                      {cashFlowError.message || "Something went wrong"}
                    </small>
                  </div>
                ) : (
                  <>
                    {cashflowElement}
                    <ul id={styles.cashflowInfo}>
                      <li>
                        <span className={styles.cashflowLabel}>Inflow:</span>
                        <span className={styles.cashflowValue}>
                          {" "}
                          ${resInflow}
                        </span>
                      </li>
                      <li>
                        <span className={styles.cashflowLabel}>Outflow:</span>
                        {resOutflow ? (
                          <span className={styles.cashflowValue}>
                            ${resOutflow}
                          </span>
                        ) : (
                          <span></span>
                        )}
                      </li>
                    </ul>
                  </>
                )}
              </div>
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
              <div className={styles.titleAndListSpacing}>
                <div className={styles.titleAndCurrency}>
                  <h2 className={styles.cardTitle}> Top Subscriptions</h2>
                  <h2>$</h2>
                </div>
                {isSubLoading ? (
                  <div className={styles.loaderPosition}>
                    <div className="loader"></div>
                  </div>
                ) : subError ? (
                  <div className={styles.errorPosition}>
                    <p>⚠️ Unable to load data</p>
                    <small>{subError.message || "Something went wrong"}</small>
                  </div>
                ) : (
                  <ul>
                    {topSubs?.map((item, index) => (
                      <li key={index} className={styles.spanSpacing}>
                        <span>{item.title}</span>
                        <span>{item.amount.toFixed(2)}</span>
                      </li>
                    ))}
                  </ul>
                )}
              </div>
            </div>
          </div>
          <div>Chart(s)</div>
        </>
      )}
    </>
  );
}
