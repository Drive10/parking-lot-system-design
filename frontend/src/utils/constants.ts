import type { VehicleType } from '../types'

export const VEHICLE_CONFIG: Record<VehicleType, { label: string; icon: string; color: string }> = {
  CAR: { label: 'Car', icon: '🚗', color: 'blue' },
  BIKE: { label: 'Bike', icon: '🏍️', color: 'green' },
  TRUCK: { label: 'Truck', icon: '🚛', color: 'orange' },
} as const

export const PAYMENT_METHODS = ['CASH', 'CREDIT_CARD', 'DEBIT_CARD', 'UPI', 'PAYPAL'] as const
