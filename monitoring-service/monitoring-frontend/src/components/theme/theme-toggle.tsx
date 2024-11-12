import { Moon, Sun, Laptop } from "lucide-react"
import { Button } from "@/components/ui/button"
import { useTheme } from "./theme-provider"

export function ThemeToggle() {
  const { theme, setTheme } = useTheme()

  const toggleTheme = () => {
    switch (theme) {
      case "light":
        setTheme("dark")
        break
      case "dark":
        setTheme("system")
        break
      default:
        setTheme("light")
        break
    }
  }

  return (
    <Button
      variant="outline"
      size="icon"
      onClick={toggleTheme}
      className="relative h-9 w-9"
    >
      {/* Sun icon for light mode */}
      <Sun 
        className="h-[1.2rem] w-[1.2rem] transition-all absolute transform rotate-0 scale-100" 
        style={{ 
          opacity: theme === 'light' ? 1 : 0,
          transform: theme === 'light' ? 'rotate(0) scale(1)' : 'rotate(90deg) scale(0)'
        }} 
      />
      
      {/* Moon icon for dark mode */}
      <Moon 
        className="h-[1.2rem] w-[1.2rem] transition-all absolute transform rotate-0 scale-100" 
        style={{ 
          opacity: theme === 'dark' ? 1 : 0,
          transform: theme === 'dark' ? 'rotate(0) scale(1)' : 'rotate(-90deg) scale(0)'
        }} 
      />
      
      {/* Laptop icon for system mode */}
      <Laptop 
        className="h-[1.2rem] w-[1.2rem] transition-all absolute transform rotate-0 scale-100" 
        style={{ 
          opacity: theme === 'system' ? 1 : 0,
          transform: theme === 'system' ? 'rotate(0) scale(1)' : 'rotate(90deg) scale(0)'
        }} 
      />
      
      <span className="sr-only">
        {theme === 'light' ? 'Switch to dark mode' : 
         theme === 'dark' ? 'Switch to system mode' : 'Switch to light mode'}
      </span>
    </Button>
  )
}