import { render, screen } from '@testing-library/react'
import userEvent from '@testing-library/user-event'
import { describe, it, expect, vi } from 'vitest'
import { ParkForm } from '../../src/components/parking/ParkForm'

describe('ParkForm', () => {
  it('renders all vehicle type buttons', () => {
    render(<ParkForm onSuccess={vi.fn()} onError={vi.fn()} />)
    expect(screen.getByText(/car/i)).toBeInTheDocument()
    expect(screen.getByText(/bike/i)).toBeInTheDocument()
    expect(screen.getByText(/truck/i)).toBeInTheDocument()
  })

  it('shows validation error for empty registration', async () => {
    const onError = vi.fn()
    render(<ParkForm onSuccess={vi.fn()} onError={onError} />)
    await userEvent.click(screen.getByRole('button', { name: /park/i }))
    expect(screen.getByText(/registration number is required/i)).toBeInTheDocument()
  })

  it('selects vehicle type on click', async () => {
    render(<ParkForm onSuccess={vi.fn()} onError={vi.fn()} />)
    const bikeBtn = screen.getByRole('radio', { name: /bike/i })
    await userEvent.click(bikeBtn)
    expect(bikeBtn).toHaveAttribute('aria-checked', 'true')
  })
})
