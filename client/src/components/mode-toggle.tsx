import { Moon, Sun } from "lucide-react";

import { Button } from "@/components/ui/button";
import { useTheme } from "@/context/theme-provider";

export default function ModeToggle() {
  const { theme, setTheme } = useTheme();

  return (
    <Button
      onClick={() => (theme == "dark" ? setTheme("light") : setTheme("dark"))}
      variant="secondary"
      className="cursor-pointer"
    >
      <Sun className="h-[1.2rem] w-[1.2rem] scale-100 rotate-0 transition-all dark:scale-0 dark:-rotate-90" />
      <Moon className="absolute h-[1.2rem] w-[1.2rem] scale-0 rotate-90 transition-all dark:scale-100 dark:rotate-0" />
    </Button>
  );
}
