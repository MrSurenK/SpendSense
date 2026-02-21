import { useState } from "react";
import styles from "./TransactionFiltersComponent.module.css";

// Mock categories for demo - replace with API call
const MOCK_CATEGORIES = [
  { id: 1, name: "Groceries" },
  { id: 2, name: "Phone Bills" },
  { id: 3, name: "Insurance" },
  { id: 4, name: "Entertainment" },
  { id: 5, name: "Transport" },
];

const defaultFilters = {
  keyword: "",
  title: "",
  startDate: "",
  endDate: "",
  min: "",
  max: "",
  catId: "",
  transactionType: "",
  isRecurring: false,
  page: 0,
  size: 10,
  sortField: "lastUpdated",
  sortDirection: "DESC",
};

export default function TransactionFilters({
  onSearch,
  categories = MOCK_CATEGORIES,
}) {
  const [filters, setFilters] = useState(defaultFilters);
  const [expanded, setExpanded] = useState(true);
  const [activeCount, setActiveCount] = useState(0);

  const update = (key, value) => {
    setFilters((prev) => ({ ...prev, [key]: value }));
  };

  const countActive = (f) => {
    let n = 0;
    if (f.keyword) n++;
    if (f.title) n++;
    if (f.startDate || f.endDate) n++;
    if (f.min || f.max) n++;
    if (f.catId) n++;
    if (f.transactionType) n++;
    if (f.isRecurring) n++;
    return n;
  };

  const handleSearch = () => {
    setActiveCount(countActive(filters));
    onSearch?.({ ...filters, page: 0 });
  };

  const handleReset = () => {
    setFilters(defaultFilters);
    setActiveCount(0);
    onSearch?.(defaultFilters);
  };

  const getTypeClass = (type) => {
    if (filters.transactionType !== type) return styles.toggleBtn;
    if (type === "INCOME") return `${styles.toggleBtn} ${styles.toggleIncome}`;
    if (type === "EXPENSE")
      return `${styles.toggleBtn} ${styles.toggleExpense}`;
    return `${styles.toggleBtn} ${styles.toggleActive}`;
  };

  return (
    <div className={styles.wrapper}>
      <div className={styles.header} onClick={() => setExpanded((p) => !p)}>
        <div className={styles.headerLeft}>
          <svg
            width="18"
            height="18"
            viewBox="0 0 24 24"
            fill="none"
            stroke="currentColor"
            strokeWidth="2"
            strokeLinecap="round"
            strokeLinejoin="round"
          >
            <polygon points="22 3 2 3 10 12.46 10 19 14 21 14 12.46 22 3" />
          </svg>
          <span className={styles.headerTitle}>Filters</span>
          {activeCount > 0 && (
            <span className={styles.badge}>{activeCount}</span>
          )}
        </div>
        <svg
          className={`${styles.chevron} ${expanded ? styles.chevronOpen : ""}`}
          width="16"
          height="16"
          viewBox="0 0 24 24"
          fill="none"
          stroke="currentColor"
          strokeWidth="2"
        >
          <polyline points="6 9 12 15 18 9" />
        </svg>
      </div>

      {expanded && (
        <div className={styles.body}>
          <div className={styles.fullRow}>
            <label className={styles.label}>Global Search</label>
            <div className={styles.searchWrap}>
              <svg
                className={styles.searchIcon}
                width="15"
                height="15"
                viewBox="0 0 24 24"
                fill="none"
                stroke="currentColor"
                strokeWidth="2"
              >
                <circle cx="11" cy="11" r="8" />
                <line x1="21" y1="21" x2="16.65" y2="16.65" />
              </svg>
              <input
                className={`${styles.input} ${styles.inputWithIcon}`}
                placeholder="Search across all fields..."
                value={filters.keyword}
                onChange={(e) => update("keyword", e.target.value)}
                onKeyDown={(e) => e.key === "Enter" && handleSearch()}
              />
            </div>
          </div>

          <div className={styles.grid}>
            <div className={styles.field}>
              <label className={styles.label}>Title</label>
              <input
                className={styles.input}
                placeholder="Search by title..."
                value={filters.title}
                onChange={(e) => update("title", e.target.value)}
              />
            </div>

            <div className={styles.field}>
              <label className={styles.label}>Type</label>
              <div className={styles.toggleGroup}>
                <button
                  className={
                    filters.transactionType === ""
                      ? `${styles.toggleBtn} ${styles.toggleActive}`
                      : styles.toggleBtn
                  }
                  onClick={() => update("transactionType", "")}
                >
                  All
                </button>
                <button
                  className={getTypeClass("INCOME")}
                  onClick={() => update("transactionType", "INCOME")}
                >
                  ↑ Income
                </button>
                <button
                  className={getTypeClass("EXPENSE")}
                  onClick={() => update("transactionType", "EXPENSE")}
                >
                  ↓ Expense
                </button>
              </div>
            </div>

            <div className={styles.field}>
              <label className={styles.label}>Category</label>
              <select
                className={styles.select}
                value={filters.catId}
                onChange={(e) => update("catId", e.target.value)}
              >
                <option value="">All Categories</option>
                {categories.map((c) => (
                  <option key={c.id} value={c.id}>
                    {c.name}
                  </option>
                ))}
              </select>
            </div>

            <div className={styles.field}>
              <label className={styles.label}>Recurring</label>
              <label className={styles.checkLabel}>
                <div
                  className={`${styles.checkbox} ${filters.isRecurring ? styles.checkboxOn : ""}`}
                  onClick={() => update("isRecurring", !filters.isRecurring)}
                >
                  {filters.isRecurring && (
                    <svg
                      width="12"
                      height="12"
                      viewBox="0 0 24 24"
                      fill="none"
                      stroke="white"
                      strokeWidth="3"
                    >
                      <polyline points="20 6 9 17 4 12" />
                    </svg>
                  )}
                </div>
                <span className={styles.checkText}>Recurring only</span>
              </label>
            </div>

            <div className={styles.field}>
              <label className={styles.label}>Start Date</label>
              <input
                type="date"
                className={styles.input}
                value={filters.startDate}
                onChange={(e) => update("startDate", e.target.value)}
              />
            </div>

            <div className={styles.field}>
              <label className={styles.label}>End Date</label>
              <input
                type="date"
                className={styles.input}
                value={filters.endDate}
                onChange={(e) => update("endDate", e.target.value)}
              />
            </div>

            <div className={styles.field}>
              <label className={styles.label}>Min Amount ($)</label>
              <input
                type="number"
                className={styles.input}
                placeholder="0.00"
                min="0"
                value={filters.min}
                onChange={(e) => update("min", e.target.value)}
              />
            </div>

            <div className={styles.field}>
              <label className={styles.label}>Max Amount ($)</label>
              <input
                type="number"
                className={styles.input}
                placeholder="No limit"
                min="0"
                value={filters.max}
                onChange={(e) => update("max", e.target.value)}
              />
            </div>
          </div>

          <div className={styles.divider} />

          <div className={styles.paginationRow}>
            <div className={styles.paginationLabel}>Pagination & Sorting</div>
            <div className={styles.paginationFields}>
              <div className={styles.miniField}>
                <label className={styles.labelSm}>Rows per page</label>
                <select
                  className={styles.selectSm}
                  value={filters.size}
                  onChange={(e) => update("size", Number(e.target.value))}
                >
                  {[5, 10, 15, 20, 50].map((n) => (
                    <option key={n} value={n}>
                      {n}
                    </option>
                  ))}
                </select>
              </div>
              <div className={styles.miniField}>
                <label className={styles.labelSm}>Sort by</label>
                <select
                  className={styles.selectSm}
                  value={filters.sortField}
                  onChange={(e) => update("sortField", e.target.value)}
                >
                  <option value="lastUpdated">Last Updated</option>
                  <option value="transactionDate">Date</option>
                  <option value="amount">Amount</option>
                  <option value="title">Title</option>
                </select>
              </div>
              <div className={styles.miniField}>
                <label className={styles.labelSm}>Direction</label>
                <div className={styles.dirToggle}>
                  <button
                    className={`${styles.dirBtn} ${filters.sortDirection === "ASC" ? styles.dirActive : ""}`}
                    onClick={() => update("sortDirection", "ASC")}
                  >
                    ↑ Asc
                  </button>
                  <button
                    className={`${styles.dirBtn} ${filters.sortDirection === "DESC" ? styles.dirActive : ""}`}
                    onClick={() => update("sortDirection", "DESC")}
                  >
                    ↓ Desc
                  </button>
                </div>
              </div>
            </div>
          </div>

          <div className={styles.actions}>
            <button className={styles.resetBtn} onClick={handleReset}>
              Reset All
            </button>
            <button className={styles.searchBtn} onClick={handleSearch}>
              <svg
                width="15"
                height="15"
                viewBox="0 0 24 24"
                fill="none"
                stroke="currentColor"
                strokeWidth="2.5"
              >
                <circle cx="11" cy="11" r="8" />
                <line x1="21" y1="21" x2="16.65" y2="16.65" />
              </svg>
              Apply Filters
            </button>
          </div>
        </div>
      )}
    </div>
  );
}
