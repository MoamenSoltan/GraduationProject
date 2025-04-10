import React from 'react'
import { useStateContext } from '../contexts/ContextProvider'
import axios from '../api/axios'

const useRefreshToken = () => {

    const {setAuth,auth} = useStateContext()
    

    // refresh token API 

    const refresh = async ()=>{
        const response = await axios.get('/refreshToken', {
        
           withCredentials:true
        //    withcredentials , means were using cookies , refresh tokens are stored in http only cookies (server side) while access tokens are stored in memory aka context of react app
        })
        setAuth((prev)=>{
            console.log(JSON.stringify(prev));
            console.log(response.data.accessToken);
            return {...prev , accessToken:response.data.body.accessToken}
        })
        return response.data.accessToken
    }

  return (
    refresh
  )
//   this is a hook , we can use this function by importing the hook in any component , and const refresh = useRefreshToken()
}

export default useRefreshToken

/**
 * import React from 'react'
import { useStateContext } from '../contexts/ContextProvider'
import axios from '../api/axios'

const useRefreshToken = () => {

    const {setAuth,auth} = useStateContext()

    // refresh token API 

    const refresh = async ()=>{
        const response = await axios.post('/auth/refreshToken',{refreshToken :auth.refreshToken} ,{
           withCredentials:true
        //    withcredentials , means were using cookies , refresh tokens are stored in http only cookies (server side) while access tokens are stored in memory aka context of react app
        })
        setAuth((prev)=>{
            console.log(JSON.stringify(prev));
            console.log(response.data.accessToken);
            return {...prev , accessToken:response.data.accessToken}
        })
        return response.data.accessToken
    }

  return (
    refresh
  )
//   this is a hook , we can use this function by importing the hook in any component , and const refresh = useRefreshToken()
}

export default useRefreshToken
 */