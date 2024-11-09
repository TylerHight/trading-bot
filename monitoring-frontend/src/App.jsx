import React from 'react';
import { useState, useEffect } from 'react';

function App() {
  const [backendStatus, setBackendStatus] = useState('Checking...');

  useEffect(() => {
    // Test connection to backend
    fetch('/api/test')
      .then(response => response.text())
      .then(data => setBackendStatus(data))
      .catch(error => setBackendStatus('Error connecting to backend: ' + error.message));
  }, []);

  return (
    <div style={{ padding: '20px' }}>
      <h1>Trading Bot Monitor</h1>
      <p>Backend Status: {backendStatus}</p>
    </div>
  );
}

export default App;