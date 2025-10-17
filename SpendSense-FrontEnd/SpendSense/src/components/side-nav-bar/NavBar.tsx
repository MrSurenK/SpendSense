import { NavLink, useLocation } from "react-router";
import styles from "./navBar.module.css";
import { useState } from "react";

interface MenuItem {
  key: string | number;
  mainMenu: string;
  subMenu?: SubMenuItem[];
  path?: string;
}

type SubMenuItem = {
  key: string | number;
  menuItem: string;
  path: string;
};

export default function NavBar() {
  const menu: MenuItem[] = [
    {
      key: 1,
      mainMenu: "Dashboard",
      path: "dashboard",
    },
    {
      key: 2,
      mainMenu: "Transactions",
      subMenu: [
        {
          key: 21,
          menuItem: "View All Transactions",
          path: "/txn/allTnx",
        },
        {
          key: 22,
          menuItem: "Add New Transactions",
          path: "/txn/addNew",
        },
        {
          key: 23,
          menuItem: "Edit Transactions",
          path: "/txn/editTxn",
        },
      ],
    },
    {
      key: 3,
      mainMenu: "Account Management",
      path: "settings",
    },
  ];

  //State for managing selected menu item
  const [toggleTransMenu, setToggleTransMenu] = useState<boolean>(false);

  const location = useLocation();

  const currPath = location.pathname; //gives curr path name
  const mainSegment = currPath.split("/")[1]; // first path segment

  return (
    <div className={styles.sideBar}>
      <h1 id={styles.main}>Main Menu</h1>
      <nav className={styles.nav}>
        <ul className={styles.menuItems}>
          {menu.map((item) => {
            // Determine if this main menu should be active
            const isMainActive =
              item.path === mainSegment ||
              item.subMenu?.some((sub) => sub.path === currPath);

            return (
              <li key={item.key}>
                {item.subMenu ? (
                  <>
                    <button
                      onClick={() => setToggleTransMenu((prev) => !prev)}
                      className={`${styles.dropDownBtn} ${
                        isMainActive ? styles.active : ""
                      }`}
                    >
                      {item.mainMenu}
                    </button>
                    {toggleTransMenu && (
                      <ul>
                        {item.subMenu.map((sub) => {
                          const isSubActive = sub.path === currPath;
                          return (
                            <li
                              key={sub.key}
                              className={styles.dropDownContent}
                            >
                              <NavLink
                                to={sub.path}
                                className={`${styles.menuWords} ${
                                  isSubActive ? styles.active : ""
                                }`}
                              >
                                {sub.menuItem}
                              </NavLink>
                            </li>
                          );
                        })}
                      </ul>
                    )}
                  </>
                ) : (
                  <NavLink
                    to={`${item.path}`}
                    className={`${styles.menuWords} ${
                      isMainActive ? styles.active : ""
                    }`}
                  >
                    {item.mainMenu}
                  </NavLink>
                )}
              </li>
            );
          })}
        </ul>
      </nav>
    </div>
  );
}
