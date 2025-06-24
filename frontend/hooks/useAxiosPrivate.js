import { axiosPrivate } from "../api/axios";
import { useEffect } from "react";
import { useStateContext } from "../contexts/ContextProvider";

const useAxiosPrivate = () => {
    const { auth } = useStateContext();

    useEffect(() => {
        const requestIntercept = axiosPrivate.interceptors.request.use(
            config => {
                if (auth?.accessToken && !config.headers['Authorization']) {
                    config.headers['Authorization'] = `Bearer ${auth.accessToken}`;
                }
                return config;
            },
            error => Promise.reject(error)
        );

        return () => {
            axiosPrivate.interceptors.request.eject(requestIntercept);
        };
    }, [auth]);

    return axiosPrivate;
};

export default useAxiosPrivate;
