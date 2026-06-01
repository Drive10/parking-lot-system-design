import { useCallback } from 'react'
import { createBrowserRouter, RouterProvider } from 'react-router-dom'
import { Layout } from './components/layout/Layout'
import { Dashboard } from './pages/Dashboard'
import { TicketDetail } from './pages/TicketDetail'
import { NotFound } from './pages/NotFound'
import { useApi } from './hooks/useApi'
import { usePolling } from './hooks/usePolling'
import { parkingService } from './services/parkingService'
import type { ParkingLotStatus } from './types'

export default function App() {
  const { data: status, execute } = useApi<ParkingLotStatus>()

  const refresh = useCallback(() => {
    execute(() => parkingService.getLotStatus())
  }, [execute])

  usePolling(refresh, 30000)

  const router = createBrowserRouter([
    {
      path: '/',
      element: <Layout status={status} />,
      children: [
        { index: true, element: <Dashboard /> },
        { path: 'tickets/:ticketUid', element: <TicketDetail /> },
        { path: '*', element: <NotFound /> },
      ],
    },
  ])

  return <RouterProvider router={router} />
}
