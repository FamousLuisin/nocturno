"use client";

import { Outlet } from "react-router";

export default function Layout() {
  return (
    <div className="w-full min-h-screen h-full flex flex-col items-center bg-background dark:bg-background">
      <Outlet />
    </div>
  );
}
