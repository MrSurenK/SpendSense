import NavBar from "../../components/side-nav-bar/navBar";
import { useAppSelector } from "../../hooks/reduxHooks";

export function Dashboard() {
  // -- protected component -- //
  // const loginInfo = useAppSelector((state) => state.auth);

  // return <>{loginInfo.isLoggedIn && <h1>Hello {loginInfo.username} !</h1>}</>;
  return (
    <>
      {/* <div className="windowSpace">
        <NavBar></NavBar> */}
      <div className="content">Dashboard</div>
      {/* </div> */}
    </>
  );
}
