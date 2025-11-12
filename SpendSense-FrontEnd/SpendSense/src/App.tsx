import "./App.css";
import Login from "./features/user-account/LogIn";
import { BrowserRouter, Routes, Route, useLocation } from "react-router";
import Registration from "./features/user-account/Registration";
import { Dashboard } from "./features/Dashboard/Dashboard";
import AccountSettings from "./features/AccountMgmt/AccountSettings";
import NavBar from "./components/side-nav-bar/navBar";
import { ViewAllTxn } from "./features/Transactions/ViewAllTn/ViewAllTxn";

function AppWrapper() {
  const location = useLocation();

  //Paths that do not need NavBar
  const noNavPaths = ["/", "/register"];
  const showNav = !noNavPaths.includes(location.pathname);

  return (
    <div className="app-layout">
      {showNav && <NavBar />}

      <main className="main-content">
        <Routes>
          <Route path="/" element={<Login />} />
          <Route path="/register" element={<Registration />} />
          <Route path="/dashboard" element={<Dashboard />} />
          <Route path="/settings" element={<AccountSettings />} />
          <Route path="/txn/allTnx" element={<ViewAllTxn />} />
        </Routes>
      </main>
    </div>
  );
}

export default function App() {
  return (
    <BrowserRouter>
      <AppWrapper />
    </BrowserRouter>
  );
}
