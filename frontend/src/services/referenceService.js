import api from './api'

export const brandService = {
  getBrands: (params = {}) =>
    api.get('/brands', { params }).then((res) => res.data),
}

export const companyService = {
  getCompanies: (params = {}) =>
    api.get('/companies', { params }).then((res) => res.data),
}

export const groupService = {
  getGroups: () =>
    api.get('/groups').then((res) => res.data),
}
