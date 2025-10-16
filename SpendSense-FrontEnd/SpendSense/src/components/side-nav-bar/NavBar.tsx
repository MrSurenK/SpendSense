import styles from "./navBar.module.css";
import { useState } from "react";

interface MenuItem {
  key: string | number;
  mainMenu: string;
  subMenu?: SubMenuItem[];
}

type SubMenuItem = {
  key: string | number;
  menuItem: string;
};

export default function NavBar() {
  const menu: MenuItem[] = [
    {
      key: 1,
      mainMenu: "Dashboard",
    },
    {
      key: 2,
      mainMenu: "Transactions",
      subMenu: [
        {
          key: 21,
          menuItem: "View All Transactions",
        },
        {
          key: 22,
          menuItem: "Add New Transactions",
        },
        {
          key: 23,
          menuItem: "Edit Transactions",
        },
      ],
    },
    {
      key: 3,
      mainMenu: "Account Management",
    },
  ];

  //State for managing selected menu item
  const [toggleTransMenu, setToggleTransMenu] = useState<boolean>(false);

  return (
    <>
      <div className={styles.sideBar}>
        <h1 id={styles.main}>Main Menu</h1>
        <nav className={styles.nav}>
          <ul className={styles.menuItems}>
            {menu.map((item) => (
              <li key={item.key}>
                {item.subMenu ? (
                  <button
                    onClick={() => {
                      if (toggleTransMenu == false) {
                        setToggleTransMenu(true);
                      } else {
                        setToggleTransMenu(false);
                      }
                    }}
                    className={styles.dropDownBtn}
                  >
                    {item.mainMenu}
                  </button>
                ) : (
                  <a>{item.mainMenu}</a>
                )}
                {/* if submenu exists */}
                {item.subMenu && toggleTransMenu && (
                  <ul style={{ display: toggleTransMenu ? "block" : "none" }}>
                    {item.subMenu?.map((sub) => (
                      <li className={styles.dropDownContent}>
                        <a key={sub.key}>{sub.menuItem}</a>
                      </li>
                    ))}
                  </ul>
                )}
              </li>
            ))}
          </ul>
        </nav>
      </div>
    </>
  );
}
