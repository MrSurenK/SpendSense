import "./App.css";
import Login from "./features/user-account/LogIn";
import { BrowserRouter, Routes, Route, useLocation } from "react-router";
import Registration from "./features/user-account/Registration";
import { Dashboard } from "./features/Dashboard/Dashboard";
import AccountSettings from "./features/AccountMgmt/AccountSettings";
import NavBar from "./components/side-nav-bar/NavBar";
import { ViewAllTxn } from "./features/Transactions/ViewAllTn/ViewAllTxn";
import NewTxn from "./features/Transactions/AddNewTn/NewTxn";
import TxnLayout from "./features/Transactions/TxnLayout";
import ViewTxnModal from "./components/modal/ViewTxnModal";

function AppWrapper() {
  const location = useLocation();

  //Paths that do not need NavBar
  const noNavPaths = ["/", "/register"];
  const isAuthRoute = noNavPaths.includes(location.pathname);
  const showNav = !isAuthRoute;

  return (
    <div className="app-layout">
      {showNav && <NavBar />}

      <main className={`main-content ${isAuthRoute ? "auth-content" : ""}`}>
        <Routes>
          <Route path="/" element={<Login />} />
          <Route path="/register" element={<Registration />} />
          <Route path="/dashboard" element={<Dashboard />} />
          <Route path="/settings" element={<AccountSettings />} />

          {/* Transactions parent route with nested children */}
          <Route path="/txn" element={<TxnLayout />}>
            <Route index element={<ViewAllTxn />} />
            <Route path="addNew" element={<NewTxn />} />
          </Route>

          <Route
            path="/modal"
            element={
              <ViewTxnModal
                setOpenViewModal={() => {}}
                transaction={{
                  id: "",
                  amount: 0,
                  title: "",
                  remarks: "",
                  recurring: false,
                  transactionDate: "",
                  nextDueDate: undefined,
                  lastUpdated: undefined,
                  catId: 0,
                  catName: "",
                  transactionType: "income",
                }}
              />
            }
          />
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
