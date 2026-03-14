import { useState, useEffect } from 'react'
import { groupService, companyService, brandService } from '../services/referenceService'

export function useReferenceData() {
  const [groups, setGroups]       = useState([])
  const [companies, setCompanies] = useState([])
  const [brands, setBrands]       = useState([])
  const [loading, setLoading]     = useState(false)

  useEffect(() => {
    setLoading(true)
    Promise.all([
      groupService.getGroups(),
      companyService.getCompanies(),
      brandService.getBrands(),
    ])
      .then(([g, c, b]) => {
        setGroups(g.data || [])
        setCompanies(c.data || [])
        setBrands(b.data || [])
      })
      .finally(() => setLoading(false))
  }, [])

  const getCompaniesByGroup = (groupId) =>
    groupId ? companies.filter((c) => c.groupId === Number(groupId)) : companies

  const getBrandsByCompany = (companyId) =>
    companyId ? brands.filter((b) => b.companyId === Number(companyId)) : brands

  const getBrandsByGroup = (groupId) =>
    groupId ? brands.filter((b) => b.groupId === Number(groupId)) : brands

  return {
    groups,
    companies,
    brands,
    loading,
    getCompaniesByGroup,
    getBrandsByCompany,
    getBrandsByGroup,
  }
}
