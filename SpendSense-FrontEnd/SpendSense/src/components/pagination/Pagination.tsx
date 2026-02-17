import { useState } from "react";
import styles from "./pagination.module.css";

export interface PaginationDetails {
  currPage: number;
  totalPages: number;
  onPageChange: (page: number) => void;
  pageWindowSize?: number;
}

export default function Pagination({
  currPage,
  totalPages,
  onPageChange,
  pageWindowSize = 5, //default
}: PaginationDetails) {
  const goToPage = (newPage: number) => {
    if (newPage >= 1 && newPage <= totalPages) {
      onPageChange(newPage);
    }
  };

  /*
  Grouping example assuming 10 pages per grp: 
  Grp 0: 1 to 10
  Grp 1: 11 to 20
  Grp 3: 21 to 30
  */

  const grpIdx = Math.floor((currPage - 1) / pageWindowSize);

  const startPage = grpIdx * pageWindowSize + 1;

  const endPage = Math.min(startPage + pageWindowSize - 1, totalPages);

  return (
    <>
      <div className={styles.pagination}>
        {/* Navigate to Prev group */}
        <button
          disabled={currPage === 1}
          onClick={() => goToPage(currPage - 1)}
        >
          {"<"}
        </button>
        {/* Page buttons */}
        {Array.from(
          { length: Math.min(pageWindowSize, totalPages - startPage + 1) },
          (_, i) => {
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
                  goToPage(page);
                }}
                className={page === currPage ? styles.activePage : ""}
                aria-current={page === currPage ? "page" : undefined} //accessibility for the blind
              >
                {page}
              </button>
            );
          },
        )}
        {/* Navigate to next group */}

        <button
          disabled={currPage === totalPages}
          onClick={() => goToPage(currPage + 1)}
        >
          {" "}
          {">"}
        </button>
      </div>
    </>
  );
}
