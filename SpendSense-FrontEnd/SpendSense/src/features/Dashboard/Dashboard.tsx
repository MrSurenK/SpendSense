import {
  ArcElement,
  CategoryScale,
  Chart as ChartJS,
  Legend,
  LinearScale,
  LineElement,
  PointElement,
  Title,
  Tooltip,
  type TooltipItem,
} from "chart.js";
import { Line, Pie } from "react-chartjs-2";
import { useAppSelector } from "../../hooks/reduxHooks";
import {
  useGetNetCashflowQuery,
  useGetSpendingPieChartQuery,
  useGetTopFiveSpendQuery,
  useGetTopSubsQuery,
  useGetYearlyLineChartQuery,
} from "../../redux/rtk-queries/dashboardService";
import styles from "./Dashboard.module.css";

ChartJS.register(
  ArcElement,
  CategoryScale,
  LinearScale,
  PointElement,
  LineElement,
  Title,
  Tooltip,
  Legend,
);

export function Dashboard() {
  //Get curr month and year for dashboard data
  const today = new Date();
  const currYear = today.getFullYear();
  const currMth = today.getMonth(); // 0-indexed
  const currMthNum = currMth + 1; // 1-indexed for API
  const pad = (n: number) => String(n).padStart(2, "0");
  const mthStartDate = `${currYear}-${pad(currMthNum)}-01`;
  const mthLastDay = new Date(currYear, currMthNum, 0).getDate();
  const mthEndDate = `${currYear}-${pad(currMthNum)}-${pad(mthLastDay)}`;

  //Call API to display top list
  const {
    data: topSpendData,
    error: topSpendError,
    isLoading: isTopSpendLoading,
  } = useGetTopFiveSpendQuery({
    month: currMthNum,
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
    startDate: mthStartDate,
    endDate: mthEndDate,
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

  //Call API to display monthly category spend as pie chart
  const {
    data: spendingPieData,
    error: spendingPieError,
    isLoading: isSpendingPieLoading,
  } = useGetSpendingPieChartQuery({
    month: currMthNum,
    year: currYear,
  });

  const pieChartData = {
    labels: spendingPieData?.labels ?? [],
    datasets: [
      {
        label: "Spending",
        data: spendingPieData?.values ?? [],
        backgroundColor: [
          "#2563eb",
          "#f97316",
          "#16a34a",
          "#dc2626",
          "#eab308",
          "#7c3aed",
          "#0d9488",
          "#475569",
        ],
        borderColor: "#ffffff",
        borderWidth: 2,
      },
    ],
  };

  const pieChartOptions = {
    responsive: true,
    maintainAspectRatio: false,
    plugins: {
      legend: {
        position: "bottom" as const,
      },
      tooltip: {
        callbacks: {
          label: (context: TooltipItem<"pie">) => {
            const amount = Number(context.parsed ?? 0);
            const percentage =
              spendingPieData?.percentages?.[context.dataIndex];
            if (percentage === undefined) {
              return `${context.label}: $${amount.toFixed(2)}`;
            }
            return `${context.label}: $${amount.toFixed(2)} (${percentage.toFixed(2)}%)`;
          },
        },
      },
    },
  };

  //Call API to display yearly income vs expense line chart
  const {
    data: yearlyLineData,
    error: yearlyLineError,
    isLoading: isYearlyLineLoading,
  } = useGetYearlyLineChartQuery({ year: currYear });

  const lineChartOptions = {
    responsive: true,
    maintainAspectRatio: false,
    plugins: {
      legend: {
        position: "bottom" as const,
      },
      title: {
        display: false,
      },
    },
    scales: {
      y: {
        beginAtZero: true,
      },
    },
  };

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
          <div className={styles.bottomChartsContainer}>
            <div className={styles.bottomChartLeft}>
              <h2 className={styles.chartTitle}>
                Spending Breakdown by Category
              </h2>
              {isSpendingPieLoading ? (
                <div className={styles.chartFeedback}>
                  <div className="loader"></div>
                </div>
              ) : spendingPieError ? (
                <div className={styles.chartFeedback}>
                  <p>⚠️ Unable to load chart data</p>
                  <small>
                    {"message" in spendingPieError
                      ? spendingPieError.message
                      : "Something went wrong"}
                  </small>
                </div>
              ) : spendingPieData && spendingPieData.labels.length > 0 ? (
                <div className={styles.pieChartWrapper}>
                  <Pie data={pieChartData} options={pieChartOptions} />
                </div>
              ) : (
                <div className={styles.chartFeedback}>
                  <p>No spending data for this month.</p>
                </div>
              )}
            </div>
            <div className={styles.bottomChartRight}>
              <h2 className={styles.chartTitle}>
                Income vs Expense ({currYear})
              </h2>
              {isYearlyLineLoading ? (
                <div className={styles.chartFeedback}>
                  <div className="loader"></div>
                </div>
              ) : yearlyLineError ? (
                <div className={styles.chartFeedback}>
                  <p>⚠️ Unable to load chart data</p>
                  <small>
                    {"message" in yearlyLineError
                      ? yearlyLineError.message
                      : "Something went wrong"}
                  </small>
                </div>
              ) : yearlyLineData ? (
                <div className={styles.lineChartWrapper}>
                  <Line data={yearlyLineData} options={lineChartOptions} />
                </div>
              ) : (
                <div className={styles.chartFeedback}>
                  <p>No data available for this year.</p>
                </div>
              )}
            </div>
          </div>
        </>
      )}
    </>
  );
}
