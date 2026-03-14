import { useState }        from 'react'
import { Link }             from 'react-router-dom'
import toast                from 'react-hot-toast'
import { useZones }         from '../hooks/useZones'
import { zoneService }      from '../services/zoneService'
import ZoneTable            from '../components/ZoneTable'
import ZoneFilters          from '../components/ZoneFilters'
import Pagination           from '../components/Pagination'
import Spinner              from '../components/Spinner'
import ConfirmModal         from '../components/ConfirmModal'

export default function ZoneDashboard() {
  const { zones, loading, error, params, updateParams, setPage, refetch } = useZones()
  const [search, setSearch]           = useState('')
  const [deleteTarget, setDeleteTarget] = useState(null)
  const [deleting, setDeleting]       = useState(false)

  // ── Search ───────────────────────────────────────────────────────────
  const handleSearch = (e) => {
    e.preventDefault()
    updateParams({ search: search.trim() || undefined })
  }

  const handleClearSearch = () => {
    setSearch('')
    updateParams({ search: undefined })
  }

  // ── Filters ──────────────────────────────────────────────────────────
  const handleFilterChange = (newFilters) => updateParams(newFilters)

  // ── Delete ───────────────────────────────────────────────────────────
  const handleDeleteClick  = (zone) => setDeleteTarget(zone)
  const handleDeleteCancel = ()     => setDeleteTarget(null)

  const handleDeleteConfirm = async () => {
    if (!deleteTarget) return
    setDeleting(true)
    try {
      await zoneService.deleteZone(deleteTarget.zoneId)
      toast.success(`Zone "${deleteTarget.zoneName}" deleted successfully`)
      setDeleteTarget(null)
      refetch()
    } catch (err) {
      toast.error(err.message)
    } finally {
      setDeleting(false)
    }
  }

  return (
    <div className="space-y-6">
      {/* ── Header ─────────────────────────────────────────────────────── */}
      <div className="flex flex-col sm:flex-row justify-between items-start sm:items-center gap-4">
        <div>
          <h1 className="text-2xl font-bold text-gray-900">Zone Dashboard</h1>
          <p className="text-sm text-gray-500 mt-0.5">
            Manage all zones linked to brands
          </p>
        </div>
        <Link to="/zones/add" className="btn btn-primary gap-2">
          <span>+</span> Add Zone
        </Link>
      </div>

      {/* ── Filter Card ────────────────────────────────────────────────── */}
      <div className="card space-y-4">
        <h2 className="text-sm font-semibold text-gray-700 uppercase tracking-wide">Filters</h2>
        <ZoneFilters
          filters={{
            groupId:   params.groupId,
            companyId: params.companyId,
            brandId:   params.brandId,
          }}
          onChange={handleFilterChange}
        />

        {/* Search */}
        <form onSubmit={handleSearch} className="flex gap-2 mt-2">
          <input
            type="text"
            placeholder="Search zone name…"
            className="input-field max-w-xs"
            value={search}
            onChange={(e) => setSearch(e.target.value)}
          />
          <button type="submit" className="btn btn-primary">Search</button>
          {params.search && (
            <button type="button" onClick={handleClearSearch} className="btn btn-secondary">
              Clear
            </button>
          )}
        </form>
      </div>

      {/* ── Table Card ─────────────────────────────────────────────────── */}
      <div className="card p-0 overflow-hidden">
        {/* Summary bar */}
        {zones && (
          <div className="px-6 py-3 bg-gray-50 border-b flex items-center justify-between">
            <p className="text-sm text-gray-500">
              <span className="font-medium text-gray-800">{zones.totalElements}</span> zone
              {zones.totalElements !== 1 ? 's' : ''} found
            </p>
            <select
              className="input-field w-auto text-xs py-1"
              value={params.size}
              onChange={(e) => updateParams({ size: Number(e.target.value) })}
            >
              {[5, 10, 20, 50].map((n) => (
                <option key={n} value={n}>
                  {n} per page
                </option>
              ))}
            </select>
          </div>
        )}

        {/* Content */}
        {loading && <Spinner className="py-16" />}
        {error   && (
          <div className="p-6 text-center text-red-600 text-sm">
            ⚠️ {error}
            <button onClick={refetch} className="ml-3 underline">Retry</button>
          </div>
        )}
        {!loading && !error && (
          <>
            <ZoneTable zones={zones?.content} onDelete={handleDeleteClick} />
            {zones && (
              <div className="px-6 py-4 border-t">
                <Pagination
                  pageNumber={zones.pageNumber}
                  totalPages={zones.totalPages}
                  totalElements={zones.totalElements}
                  pageSize={zones.pageSize}
                  onPageChange={setPage}
                />
              </div>
            )}
          </>
        )}
      </div>

      {/* ── Delete Confirmation ─────────────────────────────────────────── */}
      <ConfirmModal
        isOpen={!!deleteTarget}
        title="Delete Zone"
        message={`Are you sure you want to delete "${deleteTarget?.zoneName}"? This action will deactivate the zone.`}
        onConfirm={handleDeleteConfirm}
        onCancel={handleDeleteCancel}
        loading={deleting}
      />
    </div>
  )
}
