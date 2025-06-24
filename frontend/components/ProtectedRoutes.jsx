import { useStateContext } from '../contexts/ContextProvider';
import { Navigate, Outlet, useLocation, useNavigate } from 'react-router-dom';
import { useEffect } from 'react';

const ProtectedRoutes = () => {
    const { auth } = useStateContext();
    const location = useLocation();
    const navigate = useNavigate();

    useEffect(() => {
        if (!auth?.roles) return; // Not authenticated, let below handle
        const userRole = auth.roles[0]?.toLowerCase();
        const dashboardRoute = `/${userRole}Dashboard`;
        // If user is authenticated and not already on their dashboard, redirect
        if (!location.pathname.startsWith(dashboardRoute)) {
            navigate(dashboardRoute, { replace: true });
        }
    }, [auth, location.pathname, navigate]);

    if (!auth?.roles) {
        // Not authenticated
        return <Navigate to="/registration" replace />;
    }
    return <Outlet />;
};

export default ProtectedRoutes;
