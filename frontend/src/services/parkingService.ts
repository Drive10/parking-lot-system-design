import { api } from './api'
import type { TicketResponse, ParkingLotStatus, ParkVehicleRequest } from '../types'

export const parkingService = {
  park(data: ParkVehicleRequest) {
    return api.post<TicketResponse>('/parking/park', data)
  },

  unpark(ticketUid: string) {
    return api.post<TicketResponse>(`/parking/unpark/${ticketUid}`)
  },

  getTicket(ticketUid: string) {
    return api.get<TicketResponse>(`/parking/tickets/${ticketUid}`)
  },

  getLotStatus(lotId = 1) {
    return api.get<ParkingLotStatus>(`/lots/${lotId}/status`)
  },
}
