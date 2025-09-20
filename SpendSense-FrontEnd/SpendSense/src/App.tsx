import "./App.css";
import Login from "./features/user-account/LogIn";
import { BrowserRouter, Routes, Route } from "react-router";
import Registration from "./features/user-account/Registration";

function App() {
  return (
    <BrowserRouter>
      <Routes>
        <Route path="/" element={<Login />}></Route>
        <Route path="/register" element={<Registration />}></Route>
      </Routes>
    </BrowserRouter>
  );
}

export default App;
