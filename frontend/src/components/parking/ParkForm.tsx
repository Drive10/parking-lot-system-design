import { useState, type FormEvent } from 'react'
import { parkingService } from '../../services/parkingService'
import { Button } from '../ui/Button'
import { Input } from '../ui/Input'
import { Card, CardHeader, CardTitle } from '../ui/Card'
import type { VehicleType, TicketResponse } from '../../types'
import { VEHICLE_CONFIG } from '../../utils/constants'
import { cn } from '../../utils/format'

interface ParkFormProps {
  onSuccess: (ticket: TicketResponse) => void
  onError: (msg: string) => void
}

export function ParkForm({ onSuccess, onError }: ParkFormProps) {
  const [reg, setReg] = useState('')
  const [color, setColor] = useState('')
  const [type, setType] = useState<VehicleType>('CAR')
  const [loading, setLoading] = useState(false)
  const [errors, setErrors] = useState<{ reg?: string }>({})

  const validate = () => {
    const e: { reg?: string } = {}
    if (!reg.trim()) e.reg = 'Registration number is required'
    setErrors(e)
    return Object.keys(e).length === 0
  }

  const handleSubmit = async (e: FormEvent) => {
    e.preventDefault()
    if (!validate()) return
    setLoading(true)
    try {
      const ticket = await parkingService.park({
        registrationNumber: reg.trim(),
        color: color.trim() || 'Unknown',
        vehicleType: type,
      })
      onSuccess(ticket)
      setReg('')
      setColor('')
    } catch (err: unknown) {
      onError(err instanceof Error ? err.message : 'Failed to park vehicle')
    } finally {
      setLoading(false)
    }
  }

  return (
    <Card>
      <CardHeader>
        <CardTitle>Park a Vehicle</CardTitle>
      </CardHeader>
      <form onSubmit={handleSubmit} className="space-y-4">
        <Input
          label="Registration Number"
          placeholder="e.g. KA-01-1234"
          value={reg}
          onChange={(e) => setReg(e.target.value)}
          error={errors.reg}
          autoComplete="off"
        />
        <Input
          label="Color"
          placeholder="e.g. White"
          value={color}
          onChange={(e) => setColor(e.target.value)}
        />
        <fieldset>
          <legend className="text-sm font-medium text-gray-700 mb-2">Vehicle Type</legend>
          <div className="flex gap-2" role="radiogroup">
            {(Object.keys(VEHICLE_CONFIG) as VehicleType[]).map((vt) => {
              const cfg = VEHICLE_CONFIG[vt]
              return (
                <button
                  key={vt}
                  type="button"
                  role="radio"
                  aria-checked={type === vt}
                  onClick={() => setType(vt)}
                  className={cn(
                    'flex-1 text-sm py-2.5 rounded-lg border-2 transition font-medium',
                    type === vt
                      ? 'border-blue-500 bg-blue-50 text-blue-700'
                      : 'border-gray-200 text-gray-500 hover:border-gray-300 hover:bg-gray-50',
                  )}
                >
                  {cfg.icon} {cfg.label}
                </button>
              )
            })}
          </div>
        </fieldset>
        <Button type="submit" loading={loading} className="w-full" size="lg">
          Park Vehicle
        </Button>
      </form>
    </Card>
  )
}
