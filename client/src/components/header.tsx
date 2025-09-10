"use client";

import { Link } from "react-router";
import { Button } from "./ui/button";
import ModeToggle from "./mode-toggle";

export default function Header() {
  return (
    <header className="w-11/12 border-b-1 flex justify-between py-4 border-zinc-500">
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
        <Link to="/signin">
          <Button variant="default" className="cursor-pointer">
            Sign in
          </Button>
        </Link>
        <Link to="/signup">
          <Button variant="secondary" className="cursor-pointer">
            Sign up
          </Button>
        </Link>
        <ModeToggle />
      </div>
    </header>
  );
}
