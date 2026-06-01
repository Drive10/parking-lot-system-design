import { useEffect } from 'react'
import { useParams, useNavigate } from 'react-router-dom'
import { parkingService } from '../services/parkingService'
import { paymentService } from '../services/paymentService'
import { useApi } from '../hooks/useApi'
import { useToast } from '../hooks/useToast'
import { Button } from '../components/ui/Button'
import { Card, CardHeader, CardTitle } from '../components/ui/Card'
import { Badge } from '../components/ui/Badge'
import { CardSkeleton } from '../components/ui/Skeleton'
import { VEHICLE_CONFIG } from '../utils/constants'
import { formatCurrency, formatDate } from '../utils/format'
import type { TicketResponse } from '../types'

export function TicketDetail() {
  const { ticketUid } = useParams<{ ticketUid: string }>()
  const navigate = useNavigate()
  const { data: ticket, loading, error, execute } = useApi<TicketResponse>()
  const { addToast } = useToast()

  useEffect(() => {
    if (ticketUid) execute(() => parkingService.getTicket(ticketUid!))
  }, [ticketUid, execute])

  const handlePay = async () => {
    if (!ticketUid) return
    try {
      await paymentService.processPayment(ticketUid)
      addToast('success', 'Payment processed successfully')
      execute(() => parkingService.getTicket(ticketUid))
    } catch (err: unknown) {
      addToast('error', err instanceof Error ? err.message : 'Payment failed')
    }
  }

  const handleUnpark = async () => {
    if (!ticketUid) return
    try {
      const result = await parkingService.unpark(ticketUid)
      addToast('success', `Vehicle unparked — Fee: ${formatCurrency(result.fee)}`)
      execute(() => parkingService.getTicket(ticketUid))
    } catch (err: unknown) {
      addToast('error', err instanceof Error ? err.message : 'Unpark failed')
    }
  }

  if (loading) {
    return (
      <div className="max-w-2xl mx-auto">
        <CardSkeleton />
      </div>
    )
  }

  if (error) {
    return (
      <div className="max-w-2xl mx-auto text-center py-12">
        <p className="text-gray-500 mb-4">{error}</p>
        <Button variant="secondary" onClick={() => navigate('/')}>
          Back to Dashboard
        </Button>
      </div>
    )
  }

  if (!ticket) return null

  const cfg = VEHICLE_CONFIG[ticket.vehicleType]
  const isActive = ticket.status === 'ACTIVE'

  return (
    <div className="max-w-2xl mx-auto space-y-6">
      <Button variant="ghost" onClick={() => navigate('/')} className="mb-2">
        ← Back to Dashboard
      </Button>

      <Card padding="lg">
        <CardHeader>
          <div className="flex items-center gap-3">
            <span className="text-2xl" aria-hidden="true">{cfg.icon}</span>
            <div>
              <CardTitle>Ticket {ticket.ticketUid}</CardTitle>
              <p className="text-sm text-gray-500">{ticket.registrationNumber}</p>
            </div>
          </div>
          <Badge variant={isActive ? 'info' : 'default'}>{ticket.status}</Badge>
        </CardHeader>

        <div className="grid grid-cols-2 gap-6">
          <DetailField label="Vehicle Type" value={cfg.label} />
          <DetailField label="Color" value={ticket.vehicleColor} />
          <DetailField label="Spot" value={`${ticket.spotIdentifier} (Floor ${ticket.floorNumber})`} />
          <DetailField label="Entry Time" value={formatDate(ticket.entryTime)} />
          {ticket.exitTime && <DetailField label="Exit Time" value={formatDate(ticket.exitTime)} />}
          <DetailField
            label="Duration"
            value={
              ticket.exitTime
                ? `${Math.ceil((new Date(ticket.exitTime).getTime() - new Date(ticket.entryTime).getTime()) / 3600000)}h`
                : 'Still parked'
            }
          />
          <DetailField label="Fee" value={ticket.fee > 0 ? formatCurrency(ticket.fee) : 'Pending'} highlight={ticket.fee > 0} />
          <DetailField label="Payment" value={ticket.isPaid ? `Paid` : 'Unpaid'} />
        </div>

        {isActive && (
          <div className="mt-6 pt-6 border-t border-gray-200 flex gap-3">
            <Button onClick={handlePay} className="flex-1">
              Pay {ticket.fee > 0 ? formatCurrency(ticket.fee) : 'Now'}
            </Button>
            <Button onClick={handleUnpark} variant="secondary" className="flex-1">
              Unpark Vehicle
            </Button>
          </div>
        )}
      </Card>
    </div>
  )
}

function DetailField({
  label,
  value,
  highlight = false,
}: {
  label: string
  value: string
  highlight?: boolean
}) {
  return (
    <div>
      <dt className="text-xs text-gray-400 uppercase tracking-wider mb-1">{label}</dt>
      <dd className={`text-sm font-medium ${highlight ? 'text-green-600' : 'text-gray-800'}`}>{value}</dd>
    </div>
  )
}
