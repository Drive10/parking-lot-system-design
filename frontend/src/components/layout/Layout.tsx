import { Outlet } from 'react-router-dom'
import { Navbar } from './Navbar'
import { ToastContainer } from '../ui/Toast'
import type { ParkingLotStatus } from '../../types'

interface LayoutProps {
  status: ParkingLotStatus | null
}

export function Layout({ status }: LayoutProps) {
  return (
    <div className="min-h-screen bg-gray-50">
      <Navbar status={status} />
      <main className="max-w-6xl mx-auto px-4 py-6">
        <Outlet />
      </main>
      <ToastContainer />
    </div>
  )
}
