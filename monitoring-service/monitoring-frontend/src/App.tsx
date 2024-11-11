import React, { useState } from 'react'
import FourierAnalysisDashboard from './components/FourierAnalysisDashboard'

function App(): React.JSX.Element {
  return (
    <div className="min-h-screen bg-gray-100">
      <FourierAnalysisDashboard />
    </div>
  )
}

export default App