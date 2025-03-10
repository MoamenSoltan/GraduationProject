import { axiosPrivate } from "../api/axios";
import { useEffect } from "react";
import useRefreshToken from "./useRefreshToken";
import { useStateContext } from "../contexts/ContextProvider";

const useAxiosPrivate = ()=>{

    // here we attach interceptors , note : they are much like event listeners , need to be removed on unmount

    const refresh = useRefreshToken()
    const {auth} = useStateContext()

    useEffect (()=>{

        const requestIntercept = axiosPrivate.interceptors.request.use(
            config => {
                if (!config.headers['Authorization']) {
                    config.headers['Authorization'] = `Bearer ${auth.accessToken}`;
                }
                return config;
            }, (error) =>Promise.reject(error)
        )

        const responseIntercept = axiosPrivate.interceptors.response.use(
            response=>response,
            async (error) => {
            const prevRequest = error?.config
            if (error?.response?.status===403 && !prevRequest.sent)
            {
                prevRequest.sent = true
                const newAccessToken = await refresh()
                prevRequest.headers["Authorization"] = `Bearer ${newAccessToken}`
                return axiosPrivate(prevRequest)

            } return Promise.reject(error)
           }
        )


        return ()=> {
            axiosPrivate.interceptors.response.eject(responseIntercept)
            axiosPrivate.interceptors.request.eject(requestIntercept)

        }

    },[auth,refresh])
    

    return axiosPrivate
}

export default useAxiosPrivate;