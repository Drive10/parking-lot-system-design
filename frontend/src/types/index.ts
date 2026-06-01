export type VehicleType = 'CAR' | 'BIKE' | 'TRUCK'
export type TicketStatus = 'ACTIVE' | 'COMPLETED'
export type PaymentMethod = 'CASH' | 'CREDIT_CARD' | 'DEBIT_CARD' | 'UPI' | 'PAYPAL'

export interface ParkVehicleRequest {
  registrationNumber: string
  color: string
  vehicleType: VehicleType
}

export interface TicketResponse {
  ticketUid: string
  registrationNumber: string
  vehicleColor: string
  vehicleType: VehicleType
  spotIdentifier: string
  floorNumber: number
  entryTime: string
  exitTime: string | null
  fee: number
  isPaid: boolean
  status: TicketStatus
}

export interface ParkingLotStatus {
  id: number
  name: string
  address: string
  totalFloors: number
  totalSpots: number
  availableSpots: number
  occupiedSpots: number
  activeTickets: number
  isFull: boolean
  floors: FloorSummary[]
}

export interface FloorSummary {
  id: number
  name: string
  floorNumber: number
  totalSpots: number
  availableSpots: number
  occupiedSpots: number
}

export interface PaymentResponse {
  paymentUid: string
  ticketUid: string
  amount: number
  method: PaymentMethod
  status: string
  timestamp: string
}

export interface ApiError {
  status: number
  error: string
  message: string
  details: string[]
}
