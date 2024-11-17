import { useAuthStore } from "@/stores/authStore";
import axios, { HttpStatusCode } from "axios";
import { performLogout } from '@/services/auth';

const axiosInstance = axios.create({
    withCredentials: true,
    baseURL: import.meta.env.VITE_BASE_URL
});

axiosInstance.interceptors.request.use((config) => {
    const authStore = useAuthStore();
    if (!config.url.includes('/login') && !config.url.includes('/register')) {
        config.headers = {
            Authorization: 'Bearer ' + authStore.token
        };
    }

    return config;
});

axiosInstance.interceptors.response.use(response => {
    return response;
}, error => {
    console.error(error);
    if (error.response.status === HttpStatusCode.Unauthorized) {
        performLogout();
    }
});

export default axiosInstance;
