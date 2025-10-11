import "./App.css";
import Login from "./features/user-account/LogIn";
import { BrowserRouter, Routes, Route } from "react-router";
import Registration from "./features/user-account/Registration";
import { Dashboard } from "./features/Dashboard/Dashboard";

function App() {
  return (
    <BrowserRouter>
      <Routes>
        <Route path="/" element={<Login />}></Route>
        <Route path="/register" element={<Registration />}></Route>
        <Route path="/dashboard" element={<Dashboard />}></Route>
      </Routes>
    </BrowserRouter>
  );
}

export default App;
