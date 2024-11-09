// src/App.jsx

import React from 'react';
import FourierTransformPlot from './components/FourierTransformPlot';

function App() {
  return (
    <div className="min-h-screen bg-gray-100 py-6 px-4">
      <div className="max-w-7xl mx-auto">
        <h1 className="text-3xl font-bold mb-6">Trading Bot Analytics</h1>
        <FourierTransformPlot />
      </div>
    </div>
  );
}

export default App;