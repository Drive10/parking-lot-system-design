import { Link } from 'react-router-dom'
import type { ParkingLotStatus } from '../../types'

interface NavbarProps {
  status: ParkingLotStatus | null
}

export function Navbar({ status }: NavbarProps) {
  return (
    <header className="bg-white shadow-sm border-b border-gray-200 sticky top-0 z-40">
      <div className="max-w-6xl mx-auto px-4 h-14 flex items-center justify-between">
        <Link to="/" className="flex items-center gap-2 no-underline">
          <span className="text-xl" aria-hidden="true">🅿️</span>
          <span className="text-lg font-bold text-gray-800">Parking Lot</span>
          <span className="hidden sm:inline text-xs text-gray-400 bg-gray-100 px-2 py-0.5 rounded-full">
            v1.0.0
          </span>
        </Link>

        <nav className="flex items-center gap-4" aria-label="Main navigation">
          <Link to="/" className="text-sm text-gray-600 hover:text-blue-600 transition">
            Dashboard
          </Link>
          {status && (
            <div className="hidden md:flex items-center gap-3 text-xs">
              <StatusBadge label="Free" value={status.availableSpots} color="text-green-600" />
              <StatusBadge label="Occupied" value={status.occupiedSpots} color="text-orange-500" />
              <StatusBadge label="Active" value={status.activeTickets} color="text-blue-600" />
            </div>
          )}
        </nav>
      </div>
    </header>
  )
}

function StatusBadge({ label, value, color }: { label: string; value: number; color: string }) {
  return (
    <span className="tabular-nums">
      <span className={color + ' font-bold'}>{value}</span>
      <span className="text-gray-400 ml-1">{label}</span>
    </span>
  )
}
