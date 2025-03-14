// import React, { useEffect } from 'react'

// import { Navigate, replace, useNavigate } from 'react-router';
// import { useStateContext } from '../contexts/ContextProvider';
// import { Outlet } from 'react-router';

// const ProtectedRoutes = ({allowedRoles}) => {
//     const { auth } = useStateContext();
//     const navigate=useNavigate()
//     // cant use <Navigate> </Navigate> with useEffect , use normal navigate hook
//     // TODO: use useEffect to wait for auth to be changed first to avoid the asynchronous state issues 
//   useEffect(()=>{
//       // if user isnt authenticated
//       if (!auth?.accessToken) {
//         navigate("/registration" ,replace)
//       }
//       console.log("current role :",auth?.user?.roles[0].toLowerCase());
      
//       if (allowedRoles.includes(auth?.user?.roles[0])) {
//         navigate(`${auth?.user?.roles[0].toLowerCase()}Dashboard`,replace)
//       }
//   },[auth,allowedRoles,navigate])
  
//     return <Outlet />;
// }

// export default ProtectedRoutes






// import React, { useEffect } from 'react';
// import { useNavigate, useLocation } from 'react-router-dom';
// import { useStateContext } from '../contexts/ContextProvider';
// import { Outlet } from 'react-router';

// const ProtectedRoutes = ({ allowedRoles }) => {
//     const { auth } = useStateContext();
//     const navigate = useNavigate();
//     const location = useLocation();

//     useEffect(() => {
//         // If the user is not authenticated, redirect to registration
//         if (!auth?.accessToken) {
//             navigate("/registration", { replace: true });
//             return;
//         }

//         // Extract user role
//         const userRole = auth?.user?.roles[0]?.toLowerCase();
//         const dashboardRoute = `/${userRole}Dashboard`;

//         console.log("Current role:", userRole);

//         // If user is in allowed roles and not already on their dashboard, redirect
//         if (allowedRoles.includes(auth?.user?.roles[0]) && location.pathname !== dashboardRoute) {
//             navigate(dashboardRoute, { replace: true });
//         }
//     }, [auth, allowedRoles, navigate, location.pathname]);

//     return <Outlet />;
// };

// export default ProtectedRoutes;


import React, { useEffect, useState } from 'react';
import { useNavigate, useLocation } from 'react-router-dom';
import { useStateContext } from '../contexts/ContextProvider';
import { Outlet } from 'react-router';
import toast from 'react-hot-toast';

const ProtectedRoutes = ({ allowedRoles }) => {
    const { auth } = useStateContext();
    const navigate = useNavigate();
    const location = useLocation();
    const [loading, setLoading] = useState(true);

    useEffect(() => {
        if (auth === undefined) return; // Prevent early redirects
        setLoading(false);

        if (!auth?.accessToken) {
            console.warn("User is not authenticated, redirecting to /registration...");
            toast.error("Session expired")
            navigate("/registration", { replace: true });
            return;
        }

        const userRole = auth?.roles?.[0]?.toLowerCase(); // Extract role properly
        if (!userRole) {
            console.warn("No role found for user:", auth);
            return;
        }

        console.log("User role:", userRole);
        const dashboardRoute = `/${userRole}Dashboard`;

        // Only redirect if user is not already inside their correct dashboard path
        if (
            allowedRoles.map(role => role.toLowerCase()).includes(userRole) &&
            !location.pathname.startsWith(dashboardRoute) // Allow nested routes
        ) {
            console.log(`Redirecting user to: ${dashboardRoute}`);
            navigate(dashboardRoute, { replace: true });
        }
    }, [auth, allowedRoles, navigate, location.pathname]);

    if (loading) return null;

    return <Outlet />;
};

export default ProtectedRoutes;
