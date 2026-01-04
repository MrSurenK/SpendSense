import { useState } from "react";
import styles from "./pagination.module.css";

export interface PaginationDetails {
  totalPages: number;
  pageWindowSize?: number;
}

export default function Pagination({
  totalPages,
  pageWindowSize = 10, //default
}: PaginationDetails) {
  const [currPage, setCurrPage] = useState(1);
  const [grpIdx, setGrpIdx] = useState(0);

  /*
  Grouping example assuming 10 pages per grp: 
  Grp 0: 1 to 10
  Grp 1: 11 to 20
  Grp 3: 21 to 30
  */

  const startPage = grpIdx * pageWindowSize + 1;

  const endPage = Math.min(startPage + pageWindowSize - 1, totalPages);

  return (
    <>
      <div className={styles.pagination}>
        {/* Navigate to Prev group */}
        <button disabled={grpIdx === 0} onClick={() => setGrpIdx((g) => g - 1)}>
          {"<"}
        </button>

        {/* Page buttons */}
        {Array.from({ length: pageWindowSize }, (_, i) => {
          const page = startPage + i;

          if (page > totalPages) {
            return (
              <button key={i} disabled className={styles.disabledPage}>
                —
              </button>
            );
          }

          return (
            <button
              key={page}
              onClick={() => {
                setCurrPage(page);
                setGrpIdx(Math.floor((page - 1) / pageWindowSize));
              }}
              className={page === currPage ? styles.activePage : ""}
              aria-current={page === currPage ? "page" : undefined} //accessibility for the blind
            >
              {page}
            </button>
          );
        })}

        {/* Navigate to next group */}
        <button
          disabled={endPage === totalPages}
          onClick={() => setGrpIdx((g) => g + 1)}
        >
          {" "}
          {">"}
        </button>
      </div>
    </>
  );
}
