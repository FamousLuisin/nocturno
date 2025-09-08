"use client";

import { Link } from "react-router";
import { Button } from "./ui/button";

export default function Header() {
  return (
    <header className="w-11/12 border-b-1 flex justify-between py-4 border-zinc-500">
      <div className="flex gap-2 items-center">
        <Link to="/">
          <img
            src="src/assets/icon_black.svg"
            className="w-10 h-10 blok dark:hidden"
            alt=""
          />
        </Link>
        <Link to="/">
          <h1 className="font-semibold text-2xl text-secondary-foreground">
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
        <Link to="/singup">
          <Button variant="secondary" className="cursor-pointer">
            Sign up
          </Button>
        </Link>
      </div>
    </header>
  );
}
