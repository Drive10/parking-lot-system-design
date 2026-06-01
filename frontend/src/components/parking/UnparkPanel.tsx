import { useState } from 'react'
import { useNavigate } from 'react-router-dom'
import { Button } from '../ui/Button'
import { Input } from '../ui/Input'
import { Card, CardHeader, CardTitle } from '../ui/Card'

interface UnparkPanelProps {
  onSuccess: () => void
  onError: (msg: string) => void
}

export function UnparkPanel({ onError }: UnparkPanelProps) {
  const [ticketUid, setTicketUid] = useState('')
  const [loading, setLoading] = useState(false)
  const navigate = useNavigate()

  const handleSubmit = async () => {
    if (!ticketUid.trim()) {
      onError('Please enter a ticket UID')
      return
    }
    setLoading(true)
    navigate(`/tickets/${ticketUid.trim()}`)
  }

  return (
    <Card>
      <CardHeader>
        <CardTitle>Find Ticket</CardTitle>
      </CardHeader>
      <div className="space-y-4">
        <Input
          label="Ticket UID"
          placeholder="e.g. 98E3A3D4-741"
          value={ticketUid}
          onChange={(e) => setTicketUid(e.target.value)}
          onKeyDown={(e) => e.key === 'Enter' && handleSubmit()}
          autoComplete="off"
        />
        <Button
          variant="secondary"
          className="w-full"
          onClick={handleSubmit}
          loading={loading}
        >
          Search Ticket
        </Button>
      </div>
    </Card>
  )
}
