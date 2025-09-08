"use client";

import { Outlet } from "react-router";
import Header from "./header";
import Footer from "./footer";

export default function Layout() {
  return (
    <div className="w-full min-h-screen h-full flex flex-col items-center bg-background dark:bg-background">
      <Header />
      <Outlet />
      <Footer />
    </div>
  );
}
