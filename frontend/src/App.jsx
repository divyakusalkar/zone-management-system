import { Routes, Route, Navigate } from 'react-router-dom'
import Layout from './components/Layout'
import ZoneDashboard from './pages/ZoneDashboard'
import AddZone     from './pages/AddZone'
import EditZone    from './pages/EditZone'

export default function App() {
  return (
    <Routes>
      <Route path="/" element={<Layout />}>
        <Route index element={<Navigate to="/zones" replace />} />
        <Route path="zones"         element={<ZoneDashboard />} />
        <Route path="zones/add"     element={<AddZone />} />
        <Route path="zones/edit/:id" element={<EditZone />} />
      </Route>
    </Routes>
  )
}
