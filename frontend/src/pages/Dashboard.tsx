import { useCallback, useState } from 'react'
import { parkingService } from '../services/parkingService'
import { ParkForm } from '../components/parking/ParkForm'
import { UnparkPanel } from '../components/parking/UnparkPanel'
import { StatusBoard } from '../components/parking/StatusBoard'
import { TicketCard } from '../components/parking/TicketCard'
import { CardSkeleton } from '../components/ui/Skeleton'
import { usePolling } from '../hooks/usePolling'
import { useApi } from '../hooks/useApi'
import { useToast } from '../hooks/useToast'
import type { TicketResponse, ParkingLotStatus } from '../types'

export function Dashboard() {
  const [recentTickets, setRecentTickets] = useState<TicketResponse[]>([])
  const { data: status, loading, execute: fetchStatus } = useApi<ParkingLotStatus>()
  const { addToast } = useToast()

  const refresh = useCallback(() => {
    fetchStatus(() => parkingService.getLotStatus())
  }, [fetchStatus])

  usePolling(refresh, 10000)

  const handleParkSuccess = (ticket: TicketResponse) => {
    setRecentTickets((prev) => [ticket, ...prev].slice(0, 10))
    addToast('success', `Vehicle parked — Ticket ${ticket.ticketUid}`)
    refresh()
  }

  const handleError = (msg: string) => {
    addToast('error', msg)
  }

  return (
    <div className="grid grid-cols-1 lg:grid-cols-3 gap-6">
      <div className="lg:col-span-1 space-y-6">
        <ParkForm onSuccess={handleParkSuccess} onError={handleError} />
        <UnparkPanel onSuccess={refresh} onError={handleError} />
      </div>

      <div className="lg:col-span-2 space-y-6">
        {loading && !status ? (
          <>
            <CardSkeleton />
            <CardSkeleton />
          </>
        ) : (
          <StatusBoard status={status} />
        )}

        {recentTickets.length > 0 && (
          <section>
            <h2 className="text-lg font-semibold text-gray-700 mb-3">Recent Activity</h2>
            <div className="space-y-3">
              {recentTickets.map((t) => (
                <TicketCard key={`${t.ticketUid}-${t.status}`} ticket={t} compact />
              ))}
            </div>
          </section>
        )}
      </div>
    </div>
  )
}
