import { useNavigate } from 'react-router-dom'
import type { TicketResponse } from '../../types'
import { Badge } from '../ui/Badge'
import { Card } from '../ui/Card'
import { VEHICLE_CONFIG } from '../../utils/constants'
import { formatCurrency, formatDate, cn } from '../../utils/format'

interface TicketCardProps {
  ticket: TicketResponse
  compact?: boolean
}

export function TicketCard({ ticket, compact = false }: TicketCardProps) {
  const navigate = useNavigate()
  const cfg = VEHICLE_CONFIG[ticket.vehicleType]
  const isActive = ticket.status === 'ACTIVE'

  const statusVariant = isActive ? 'info' : 'default'
  const borderClass = isActive ? 'border-l-4 border-l-blue-500' : ''

  return (
    <Card
      padding={compact ? 'sm' : 'md'}
      className={cn('cursor-pointer hover:shadow-md transition-shadow', borderClass)}
      onClick={() => navigate(`/tickets/${ticket.ticketUid}`)}
      role="button"
      tabIndex={0}
      aria-label={`Ticket ${ticket.ticketUid} - ${ticket.registrationNumber}`}
      onKeyDown={(e) => e.key === 'Enter' && navigate(`/tickets/${ticket.ticketUid}`)}
    >
      <div className="flex items-center justify-between mb-2">
        <div className="flex items-center gap-2 min-w-0">
          <span className="text-lg" aria-hidden="true">{cfg.icon}</span>
          <span className="font-mono text-sm font-bold text-gray-800 truncate">
            {ticket.ticketUid}
          </span>
          <Badge variant={statusVariant}>{ticket.status}</Badge>
        </div>
        <span className="text-xs text-gray-400 ml-2 shrink-0">{ticket.registrationNumber}</span>
      </div>

      <div className={cn('grid gap-2 text-xs text-gray-500', compact ? 'grid-cols-2' : 'grid-cols-3')}>
        <div>
          <span className="text-gray-400">Spot:</span>{' '}
          <span className="font-medium text-gray-700">{ticket.spotIdentifier}</span>
          <span className="text-gray-400"> (F{ticket.floorNumber})</span>
        </div>
        <div>
          <span className="text-gray-400">Entry:</span>{' '}
          <span className="font-medium text-gray-700">{formatDate(ticket.entryTime)}</span>
        </div>
        <div className={compact ? 'col-span-2' : 'text-right'}>
          {ticket.status === 'COMPLETED' ? (
            <span className="text-green-600 font-semibold">
              Paid {formatCurrency(ticket.fee)}
            </span>
          ) : ticket.fee > 0 ? (
            <span className="text-orange-500 font-semibold">
              {formatCurrency(ticket.fee)} due
            </span>
          ) : (
            <span className="text-gray-400">No fee yet</span>
          )}
        </div>
      </div>
    </Card>
  )
}
