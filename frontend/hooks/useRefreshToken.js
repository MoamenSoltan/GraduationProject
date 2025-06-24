import axios from '../api/axios';

const useRefreshToken = () => {
    // This hook checks authentication by calling /auth/me
    const checkAuth = async () => {
        try {
            const response = await axios.get('/auth/me', { withCredentials: true });
            return response.data;
        } catch {
            return null;
        }
    };
    return checkAuth;
};

export default useRefreshToken;

// Usage: const checkAuth = useRefreshToken();
// const user = await checkAuth();