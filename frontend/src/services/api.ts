import type { ApiError } from '../types'

const BASE = import.meta.env.VITE_API_BASE_URL || '/api/v1'

const MAX_RETRIES = 2
const RETRY_DELAY = 500

class ApiClient {
  private controller: AbortController | null = null

  private async request<T>(path: string, options: RequestInit = {}): Promise<T> {
    const url = `${BASE}${path}`
    const defaultHeaders: Record<string, string> = {
      'Content-Type': 'application/json',
    }

    let lastError: Error | null = null

    for (let attempt = 0; attempt <= MAX_RETRIES; attempt++) {
      try {
        this.controller = new AbortController()
        const timeout = setTimeout(() => this.controller?.abort(), 15000)

        const response = await fetch(url, {
          ...options,
          headers: { ...defaultHeaders, ...options.headers },
          signal: this.controller.signal,
        })
        clearTimeout(timeout)

        if (!response.ok) {
          const err: ApiError = await response.json().catch(() => ({
            status: response.status,
            error: response.statusText,
            message: 'An unexpected error occurred',
            details: [],
          }))
          throw new ApiClientError(err)
        }

        return response.json()
      } catch (err) {
        lastError = err instanceof Error ? err : new Error(String(err))
        if (err instanceof ApiClientError) throw err
        if (attempt < MAX_RETRIES) {
          await new Promise((r) => setTimeout(r, RETRY_DELAY * (attempt + 1)))
        }
      }
    }

    throw lastError || new Error('Request failed')
  }

  cancel() {
    this.controller?.abort()
  }

  get<T>(path: string) {
    return this.request<T>(path)
  }

  post<T>(path: string, body?: unknown) {
    return this.request<T>(path, {
      method: 'POST',
      body: body ? JSON.stringify(body) : undefined,
    })
  }
}

export class ApiClientError extends Error {
  constructor(public readonly apiError: ApiError) {
    super(apiError.message)
    this.name = 'ApiClientError'
  }
}

export const api = new ApiClient()
