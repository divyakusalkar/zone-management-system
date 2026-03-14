import api from './api'

export const zoneService = {
  // GET /api/zones with filters and pagination
  getZones: (params = {}) =>
    api.get('/zones', { params }).then((res) => res.data),

  // GET /api/zones/:id
  getZoneById: (id) =>
    api.get(`/zones/${id}`).then((res) => res.data),

  // POST /api/zones
  createZone: (data) =>
    api.post('/zones', data).then((res) => res.data),

  // PUT /api/zones/:id
  updateZone: (id, data) =>
    api.put(`/zones/${id}`, data).then((res) => res.data),

  // DELETE /api/zones/:id  (soft delete)
  deleteZone: (id) =>
    api.delete(`/zones/${id}`).then((res) => res.data),
}
