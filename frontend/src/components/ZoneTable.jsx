import { Link }          from 'react-router-dom'
import { formatDate }    from '../utils/helpers'

export default function ZoneTable({ zones, onDelete }) {
  if (!zones || zones.length === 0) {
    return (
      <div className="text-center py-16 text-gray-400">
        <span className="text-5xl">📭</span>
        <p className="mt-3 text-sm">No zones found.</p>
      </div>
    )
  }

  return (
    <div className="overflow-x-auto">
      <table className="min-w-full divide-y divide-gray-200 text-sm">
        <thead className="bg-gray-50">
          <tr>
            {['Zone ID', 'Zone Name', 'Brand', 'Group / Company', 'Created Date', 'Status', 'Actions'].map(
              (h) => (
                <th
                  key={h}
                  className="px-4 py-3 text-left text-xs font-semibold text-gray-500 uppercase tracking-wider"
                >
                  {h}
                </th>
              )
            )}
          </tr>
        </thead>
        <tbody className="bg-white divide-y divide-gray-100">
          {zones.map((zone) => (
            <tr key={zone.zoneId} className="hover:bg-gray-50 transition-colors">
              <td className="px-4 py-3 text-gray-500 font-mono">#{zone.zoneId}</td>
              <td className="px-4 py-3 font-medium text-gray-900">{zone.zoneName}</td>
              <td className="px-4 py-3 text-gray-700">{zone.brandName}</td>
              <td className="px-4 py-3 text-gray-500">
                <span className="font-medium text-gray-700">{zone.groupName}</span>
                <span className="mx-1 text-gray-300">/</span>
                {zone.companyName}
              </td>
              <td className="px-4 py-3 text-gray-500">{formatDate(zone.createdAt)}</td>
              <td className="px-4 py-3">
                {zone.isActive ? (
                  <span className="badge-active">Active</span>
                ) : (
                  <span className="badge-inactive">Inactive</span>
                )}
              </td>
              <td className="px-4 py-3">
                <div className="flex items-center gap-2">
                  <Link
                    to={`/zones/edit/${zone.zoneId}`}
                    className="btn btn-secondary text-xs px-3 py-1.5"
                  >
                    ✏️ Edit
                  </Link>
                  <button
                    onClick={() => onDelete(zone)}
                    className="btn btn-danger text-xs px-3 py-1.5"
                  >
                    🗑️ Delete
                  </button>
                </div>
              </td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  )
}
