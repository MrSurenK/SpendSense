interface MenuItem {
  key: string | number;
  mainMenu: string;
  subMenu?: SubMenuItem[];
}

type SubMenuItem = {
  key: string | number;
  menuItem: string;
};

export default function navBar() {
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

  return (
    <>
      <div>
        <h1 style={{ fontWeight: 700 }}>Main Menu</h1>
        <nav>
          <ul>
            {menu.map((item) => (
              <li key={item.key}>
                {item.mainMenu}
                {/* if submenu exists */}
                {item.subMenu && (
                  <ul>
                    {item.subMenu?.map((sub) => (
                      <li key={sub.key}>{sub.menuItem}</li>
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
