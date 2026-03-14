import { Link, NavLink, Outlet } from 'react-router-dom'

export default function Layout() {
  return (
    <div className="min-h-screen flex flex-col">
      {/* Top Nav */}
      <header className="bg-blue-700 shadow-md">
        <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
          <div className="flex items-center justify-between h-16">
            <Link to="/zones" className="flex items-center gap-2">
              <span className="text-2xl">🗺️</span>
              <span className="text-white font-bold text-lg tracking-wide">
                Zone Management System
              </span>
            </Link>
            <nav className="flex gap-4">
              <NavLink
                to="/zones"
                end
                className={({ isActive }) =>
                  `text-sm font-medium px-3 py-2 rounded-lg transition-colors ${
                    isActive
                      ? 'bg-blue-800 text-white'
                      : 'text-blue-100 hover:bg-blue-600 hover:text-white'
                  }`
                }
              >
                Dashboard
              </NavLink>
              <NavLink
                to="/zones/add"
                className={({ isActive }) =>
                  `text-sm font-medium px-3 py-2 rounded-lg transition-colors ${
                    isActive
                      ? 'bg-blue-800 text-white'
                      : 'text-blue-100 hover:bg-blue-600 hover:text-white'
                  }`
                }
              >
                + Add Zone
              </NavLink>
            </nav>
          </div>
        </div>
      </header>

      {/* Page Content */}
      <main className="flex-1 max-w-7xl mx-auto w-full px-4 sm:px-6 lg:px-8 py-8">
        <Outlet />
      </main>

      {/* Footer */}
      <footer className="border-t border-gray-200 py-4 text-center text-xs text-gray-400">
        Zone Management System © {new Date().getFullYear()}
      </footer>
    </div>
  )
}
