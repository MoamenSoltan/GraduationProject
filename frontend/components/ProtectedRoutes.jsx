import React, { useEffect } from 'react'

import { Navigate, replace, useNavigate } from 'react-router';
import { useStateContext } from '../contexts/ContextProvider';
import { Outlet } from 'react-router';

const ProtectedRoutes = ({allowedRoles}) => {
    const { auth } = useStateContext();
    const navigate=useNavigate()
    // cant use <Navigate> </Navigate> with useEffect , use normal navigate hook
    // TODO: use useEffect to wait for auth to be changed first to avoid the asynchronous state issues 
  useEffect(()=>{
      // if user isnt authenticated
      if (!auth?.accessToken) {
        navigate("/registration" ,replace)
      }
      console.log("current role :",auth?.user?.roles[0].toLowerCase());
      
      if (!allowedRoles.includes(auth?.user?.roles[0])) {
        navigate(`${auth?.user?.roles[0].toLowerCase()}Dashboard`,replace)
      }
  },[auth,allowedRoles])
  
    return <Outlet />;
}

export default ProtectedRoutes