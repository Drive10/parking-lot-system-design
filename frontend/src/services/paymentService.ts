import { api } from './api'
import type { PaymentResponse, PaymentMethod } from '../types'

export const paymentService = {
  processPayment(ticketUid: string, method: PaymentMethod = 'CREDIT_CARD') {
    return api.post<PaymentResponse>(`/payments/${ticketUid}?method=${method}`)
  },
}
