import { ThemeProvider } from "./components/theme/theme-provider"
import { ThemeToggle } from "./components/theme/theme-toggle"
import FourierAnalysisDashboard from "./components/FourierAnalysisDashboard"

function App() {
  return (
    <ThemeProvider defaultTheme="light">
      <div className="min-h-screen bg-background text-foreground">
        <div className="container mx-auto p-4">
          <div className="flex justify-end mb-4">
            <ThemeToggle />
          </div>
          <FourierAnalysisDashboard />
        </div>
      </div>
    </ThemeProvider>
  )
}

export default App