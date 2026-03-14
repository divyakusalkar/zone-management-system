import { useState, useEffect, useCallback } from 'react'
import { zoneService } from '../services/zoneService'

export function useZones(initialParams = {}) {
  const [zones, setZones]     = useState(null)
  const [loading, setLoading] = useState(false)
  const [error, setError]     = useState(null)
  const [params, setParams]   = useState({
    page: 0,
    size: 10,
    sortBy: 'zoneId',
    sortDir: 'asc',
    ...initialParams,
  })

  const fetchZones = useCallback(async () => {
    setLoading(true)
    setError(null)
    try {
      const response = await zoneService.getZones(params)
      setZones(response.data)
    } catch (err) {
      setError(err.message)
    } finally {
      setLoading(false)
    }
  }, [params])

  useEffect(() => {
    fetchZones()
  }, [fetchZones])

  const updateParams = (newParams) =>
    setParams((prev) => ({ ...prev, ...newParams, page: 0 }))

  const setPage = (page) =>
    setParams((prev) => ({ ...prev, page }))

  return { zones, loading, error, params, updateParams, setPage, refetch: fetchZones }
}
