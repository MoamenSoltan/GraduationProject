import React from 'react'

import { Navigate } from 'react-router';
import { useStateContext } from '../contexts/ContextProvider';
import { Outlet } from 'react-router';

const ProtectedRoutes = ({allowedRoles}) => {
    const { auth } = useStateContext();

    // if user isnt authenticated
    if (!auth?.accessToken) {
      return <Navigate to="/registration" replace />;
    }
  
    if (!allowedRoles.includes(auth?.user?.role)) {
      return <Navigate to={`/${auth?.user?.role}Dashboard`} replace />;
    }
  
    return <Outlet />;
}

export default ProtectedRoutes