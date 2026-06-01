import type { ParkingLotStatus } from '../../types'
import { Card, CardHeader, CardTitle } from '../ui/Card'
import { cn } from '../../utils/format'

interface StatusBoardProps {
  status: ParkingLotStatus | null
}

export function StatusBoard({ status }: StatusBoardProps) {
  if (!status) return null

  return (
    <Card padding="lg">
      <CardHeader>
        <div>
          <CardTitle>{status.name}</CardTitle>
          <p className="text-xs text-gray-400 mt-0.5">{status.address}</p>
        </div>
      </CardHeader>

      <div className="grid grid-cols-2 sm:grid-cols-4 gap-3 mb-6">
        <StatCard label="Total Spots" value={status.totalSpots} />
        <StatCard label="Available" value={status.availableSpots} color="green" />
        <StatCard label="Occupied" value={status.occupiedSpots} color="orange" />
        <StatCard label="Active Tickets" value={status.activeTickets} color="blue" />
      </div>

      {status.isFull && (
        <div className="bg-red-50 border border-red-200 text-red-700 rounded-lg px-4 py-3 text-sm mb-6 flex items-center gap-2" role="alert">
          <span aria-hidden="true">⚠️</span>
          Parking lot is full. No spots available.
        </div>
      )}

      <div>
        <h3 className="text-xs font-semibold text-gray-500 uppercase tracking-wider mb-3">
          Floors
        </h3>
        <div className="space-y-2.5">
          {status.floors.map((f) => {
            const pct = f.totalSpots > 0 ? Math.round((f.occupiedSpots / f.totalSpots) * 100) : 0
            const barColor =
              pct > 80 ? 'bg-red-400' : pct > 50 ? 'bg-yellow-400' : 'bg-green-400'
            return (
              <div key={f.id} className="flex items-center gap-3">
                <span className="w-8 text-sm font-semibold text-gray-600 tabular-nums">
                  {f.name}
                </span>
                <div className="flex-1 bg-gray-100 rounded-full h-3 overflow-hidden" role="progressbar" aria-valuenow={pct} aria-valuemin={0} aria-valuemax={100} aria-label={`Floor ${f.name} occupancy ${pct}%`}>
                  <div
                    className={cn('h-full rounded-full transition-all duration-500', barColor)}
                    style={{ width: `${pct}%` }}
                  />
                </div>
                <span className="w-24 text-right text-sm text-gray-500 tabular-nums">
                  <span className="font-semibold text-gray-700">{f.availableSpots}</span>
                  <span className="text-gray-400">/{f.totalSpots}</span>
                </span>
              </div>
            )
          })}
        </div>
      </div>
    </Card>
  )
}

function StatCard({ label, value, color = 'gray' }: { label: string; value: number; color?: string }) {
  const colors: Record<string, string> = {
    gray: 'text-gray-700',
    green: 'text-green-600',
    orange: 'text-orange-500',
    blue: 'text-blue-600',
  }

  return (
    <div className="bg-gray-50 rounded-lg p-4 text-center border border-gray-100">
      <div className={cn('text-2xl font-bold tabular-nums', colors[color])}>{value}</div>
      <div className="text-xs text-gray-400 mt-1">{label}</div>
    </div>
  )
}
