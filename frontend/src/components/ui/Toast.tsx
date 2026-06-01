import { useToast } from '../../context/ToastContext'
import { cn } from '../../utils/format'

const icons = {
  success: '✓',
  error: '✕',
  info: 'ℹ',
}

const styles = {
  success: 'bg-green-50 border-green-200 text-green-800',
  error: 'bg-red-50 border-red-200 text-red-800',
  info: 'bg-blue-50 border-blue-200 text-blue-800',
}

export function ToastContainer() {
  const { toasts, removeToast } = useToast()

  if (toasts.length === 0) return null

  return (
    <div
      className="fixed bottom-4 right-4 z-50 flex flex-col gap-2 max-w-sm"
      role="region"
      aria-label="Notifications"
    >
      {toasts.map((toast) => (
        <div
          key={toast.id}
          className={cn(
            'flex items-start gap-3 border rounded-lg px-4 py-3 text-sm shadow-lg transition-all animate-slide-up',
            styles[toast.type],
          )}
          role="alert"
        >
          <span className="mt-0.5 font-bold" aria-hidden="true">
            {icons[toast.type]}
          </span>
          <p className="flex-1">{toast.message}</p>
          <button
            onClick={() => removeToast(toast.id)}
            className="text-current opacity-50 hover:opacity-100 transition-opacity"
            aria-label="Dismiss"
          >
            ✕
          </button>
        </div>
      ))}
    </div>
  )
}
