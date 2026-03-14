import { useReferenceData } from '../hooks/useReferenceData'

export default function ZoneFilters({ filters, onChange }) {
  const { groups, getCompaniesByGroup, getBrandsByCompany } = useReferenceData()

  const handleGroupChange = (e) => {
    onChange({ groupId: e.target.value || undefined, companyId: undefined, brandId: undefined })
  }

  const handleCompanyChange = (e) => {
    onChange({ companyId: e.target.value || undefined, brandId: undefined })
  }

  const handleBrandChange = (e) => {
    onChange({ brandId: e.target.value || undefined })
  }

  const filteredCompanies = getCompaniesByGroup(filters.groupId)
  const filteredBrands    = getBrandsByCompany(filters.companyId)

  return (
    <div className="grid grid-cols-1 sm:grid-cols-3 gap-4">
      {/* Group Filter */}
      <div>
        <label className="label">Group</label>
        <select
          className="input-field"
          value={filters.groupId || ''}
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

      {/* Company Filter */}
      <div>
        <label className="label">Company</label>
        <select
          className="input-field"
          value={filters.companyId || ''}
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

      {/* Brand Filter */}
      <div>
        <label className="label">Brand</label>
        <select
          className="input-field"
          value={filters.brandId || ''}
          onChange={handleBrandChange}
        >
          <option value="">All Brands</option>
          {filteredBrands.map((b) => (
            <option key={b.brandId} value={b.brandId}>
              {b.brandName}
            </option>
          ))}
        </select>
      </div>
    </div>
  )
}
