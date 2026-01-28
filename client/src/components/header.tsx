"use client";

import { Link } from "react-router";
import { Button } from "./ui/button";
import ModeToggle from "./mode-toggle";
import { useAuth } from "@/context/auth-provider";

export default function Header() {
  const { isAuth, setAuth } = useAuth();

  const logoutAuth = () => {
    setAuth(false);
  };

  return (
    <header className="w-11/12 border-b flex justify-between py-4 border-zinc-500">
      <div className="flex items-center">
        <Link to="/">
          <img
            src="src/assets/icon_black.png"
            className="w-10 h-10 block dark:hidden"
            alt=""
          />
        </Link>
        <Link to="/">
          <img
            src="src/assets/icon_white.png"
            className="w-10 h-10 hidden dark:block"
            alt=""
          />
        </Link>
        <Link to="/">
          <h1 className="font-semibold text-2xl text-secondary-foreground ml-2">
            Nocturno
          </h1>
        </Link>
      </div>
      <div className="flex gap-2">
        {!isAuth && (
          <nav className="flex gap-2">
            <Button asChild variant="default">
              <Link to="/signin">Sign in</Link>
            </Button>
            <Button asChild variant="secondary">
              <Link to="/signup">Sign up</Link>
            </Button>
          </nav>
        )}
        {isAuth && (
          <nav className="flex gap-2">
            <Button asChild variant="secondary" onClick={logoutAuth}>
              <Link to="/">Logout</Link>
            </Button>
          </nav>
        )}
        <ModeToggle />
      </div>
    </header>
  );
}
