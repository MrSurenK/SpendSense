import { Outlet } from "react-router";

export default function TxnLayout() {
  return (
    <div>
      {/* Txn parent layout (can add shared header/controls here) */}
      <Outlet /> {/* child route renders here */}
    </div>
  );
}
