import React from 'react'
import ReactDOM from 'react-dom/client'
import { ToastProvider } from './context/ToastContext'
import { ErrorBoundary } from './components/ui/ErrorBoundary'
import App from './App'
import './index.css'

ReactDOM.createRoot(document.getElementById('root')!).render(
  <React.StrictMode>
    <ErrorBoundary>
      <ToastProvider>
        <App />
      </ToastProvider>
    </ErrorBoundary>
  </React.StrictMode>,
)
