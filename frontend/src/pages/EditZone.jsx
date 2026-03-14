import { useState, useEffect }  from 'react'
import { useNavigate, useParams, Link } from 'react-router-dom'
import toast                     from 'react-hot-toast'
import { zoneService }           from '../services/zoneService'
import { useReferenceData }      from '../hooks/useReferenceData'
import Spinner                   from '../components/Spinner'

export default function EditZone() {
  const { id }     = useParams()
  const navigate   = useNavigate()
  const { groups, getCompaniesByGroup, getBrandsByCompany, loading: refLoading } = useReferenceData()

  const [form, setForm]       = useState({ zoneName: '', brandId: '' })
  const [errors, setErrors]   = useState({ zoneName: '', brandId: '' })
  const [saving, setSaving]   = useState(false)
  const [fetchLoading, setFetchLoading] = useState(true)
  const [fetchError, setFetchError]     = useState(null)

  const [selectedGroup,   setSelectedGroup]   = useState('')
  const [selectedCompany, setSelectedCompany] = useState('')

  const filteredCompanies = getCompaniesByGroup(selectedGroup)
  const filteredBrands    = getBrandsByCompany(selectedCompany || form.brandId ? selectedCompany : '')

  // Pre-populate all brands when company/group not explicitly filtered
  const displayBrands = selectedCompany
    ? getBrandsByCompany(selectedCompany)
    : selectedGroup
    ? getBrandsByCompany('')   // will return all if companyId empty
    : getBrandsByCompany('')

  // Load zone data
  useEffect(() => {
    zoneService
      .getZoneById(id)
      .then((res) => {
        const z = res.data
        setForm({ zoneName: z.zoneName, brandId: String(z.brandId) })
        setSelectedGroup(String(z.groupId))
        setSelectedCompany(String(z.companyId))
      })
      .catch((err) => setFetchError(err.message))
      .finally(() => setFetchLoading(false))
  }, [id])

  const handleGroupChange = (e) => {
    setSelectedGroup(e.target.value)
    setSelectedCompany('')
    setForm((prev) => ({ ...prev, brandId: '' }))
  }

  const handleCompanyChange = (e) => {
    setSelectedCompany(e.target.value)
    setForm((prev) => ({ ...prev, brandId: '' }))
  }

  const handleChange = (e) => {
    const { name, value } = e.target
    setForm((prev) => ({ ...prev, [name]: value }))
    setErrors((prev) => ({ ...prev, [name]: '' }))
  }

  const validate = () => {
    const errs = { zoneName: '', brandId: '' }
    if (!form.zoneName.trim())        errs.zoneName = 'Zone name is required'
    else if (form.zoneName.trim().length > 50)
                                      errs.zoneName = 'Zone name must not exceed 50 characters'
    if (!form.brandId)                errs.brandId  = 'Brand is required'
    setErrors(errs)
    return !Object.values(errs).some(Boolean)
  }

  const handleSubmit = async (e) => {
    e.preventDefault()
    if (!validate()) return
    setSaving(true)
    try {
      await zoneService.updateZone(id, {
        zoneName: form.zoneName.trim(),
        brandId:  Number(form.brandId),
      })
      toast.success('Zone updated successfully!')
      navigate('/zones')
    } catch (err) {
      toast.error(err.message)
    } finally {
      setSaving(false)
    }
  }

  if (fetchLoading || refLoading) return <Spinner className="py-20" />
  if (fetchError) return (
    <div className="text-center py-16 text-red-600">
      <p>⚠️ {fetchError}</p>
      <Link to="/zones" className="mt-3 underline text-blue-600">← Back to Dashboard</Link>
    </div>
  )

  const brandList = selectedCompany
    ? getBrandsByCompany(selectedCompany)
    : getBrandsByCompany('')

  return (
    <div className="max-w-xl mx-auto space-y-6">
      {/* Header */}
      <div className="flex items-center gap-3">
        <Link to="/zones" className="text-gray-400 hover:text-gray-600 transition-colors">
          ← Back
        </Link>
        <div>
          <h1 className="text-2xl font-bold text-gray-900">Edit Zone</h1>
          <p className="text-sm text-gray-500">Update zone details — ID #{id}</p>
        </div>
      </div>

      {/* Form */}
      <div className="card">
        <form onSubmit={handleSubmit} noValidate className="space-y-5">
          {/* Zone Name */}
          <div>
            <label className="label" htmlFor="zoneName">
              Zone Name <span className="text-red-500">*</span>
            </label>
            <input
              id="zoneName"
              name="zoneName"
              type="text"
              maxLength={50}
              placeholder="e.g. Marol Zone"
              className={`input-field ${errors.zoneName ? 'input-error' : ''}`}
              value={form.zoneName}
              onChange={handleChange}
            />
            {errors.zoneName && (
              <p className="mt-1 text-xs text-red-600">{errors.zoneName}</p>
            )}
          </div>

          {/* Group filter */}
          <div>
            <label className="label" htmlFor="groupId">
              Group <span className="text-gray-400">(optional filter)</span>
            </label>
            <select
              id="groupId"
              className="input-field"
              value={selectedGroup}
              onChange={handleGroupChange}
            >
              <option value="">All Groups</option>
              {groups.map((g) => (
                <option key={g.groupId} value={g.groupId}>
                  {g.groupName}
                </option>
              ))}
            </select>
          </div>

          {/* Company filter */}
          <div>
            <label className="label" htmlFor="companyId">
              Company <span className="text-gray-400">(optional filter)</span>
            </label>
            <select
              id="companyId"
              className="input-field"
              value={selectedCompany}
              onChange={handleCompanyChange}
            >
              <option value="">All Companies</option>
              {filteredCompanies.map((c) => (
                <option key={c.companyId} value={c.companyId}>
                  {c.companyName}
                </option>
              ))}
            </select>
          </div>

          {/* Brand */}
          <div>
            <label className="label" htmlFor="brandId">
              Brand <span className="text-red-500">*</span>
            </label>
            <select
              id="brandId"
              name="brandId"
              className={`input-field ${errors.brandId ? 'input-error' : ''}`}
              value={form.brandId}
              onChange={handleChange}
            >
              <option value="">Select Brand</option>
              {brandList.map((b) => (
                <option key={b.brandId} value={b.brandId}>
                  {b.brandName}
                </option>
              ))}
            </select>
            {errors.brandId && (
              <p className="mt-1 text-xs text-red-600">{errors.brandId}</p>
            )}
          </div>

          {/* Actions */}
          <div className="flex gap-3 pt-2">
            <button type="submit" className="btn btn-primary" disabled={saving}>
              {saving ? 'Saving…' : 'Update Zone'}
            </button>
            <Link to="/zones" className="btn btn-secondary">
              Cancel
            </Link>
          </div>
        </form>
      </div>
    </div>
  )
}
