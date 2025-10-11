import { useAppSelector } from "../../hooks/reduxHooks";

export function Dashboard() {
  const loginInfo = useAppSelector((state) => state.auth);

  return <>{loginInfo.isLoggedIn && <h1>Hello {loginInfo.username} !</h1>}</>;
}
