import { describe, it, expect, vi, beforeEach } from 'vitest'
import { api, ApiClientError } from '../../src/services/api'

const mockFetch = vi.fn()
globalThis.fetch = mockFetch

describe('ApiClient', () => {
  beforeEach(() => {
    mockFetch.mockReset()
  })

  it('makes successful GET request', async () => {
    mockFetch.mockResolvedValueOnce({
      ok: true,
      json: () => Promise.resolve({ data: 'ok' }),
    })
    const result = await api.get('/test')
    expect(result).toEqual({ data: 'ok' })
    expect(mockFetch).toHaveBeenCalledWith('/api/v1/test', expect.any(Object))
  })

  it('throws ApiClientError on non-ok response', async () => {
    mockFetch.mockResolvedValueOnce({
      ok: false,
      status: 404,
      statusText: 'Not Found',
      json: () => Promise.resolve({ status: 404, error: 'Not Found', message: 'Ticket not found', details: [] }),
    })
    await expect(api.get('/tickets/X')).rejects.toThrow(ApiClientError)
  })

  it('makes POST request with body', async () => {
    mockFetch.mockResolvedValueOnce({
      ok: true,
      json: () => Promise.resolve({ id: 1 }),
    })
    const body = { name: 'test' }
    await api.post('/create', body)
    expect(mockFetch).toHaveBeenCalledWith('/api/v1/create', expect.objectContaining({
      method: 'POST',
      body: JSON.stringify(body),
    }))
  })
})
