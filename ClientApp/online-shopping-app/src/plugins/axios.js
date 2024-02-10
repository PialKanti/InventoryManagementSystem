import { useAuthStore } from "@/stores/authStore";
import axios from "axios";

const axiosInstance = axios.create({
    baseURL: import.meta.env.VITE_BASE_URL,
});

axios.interceptors.request.use((config) => {
    const authStore = useAuthStore();
    if (!config.url.includes('/login') && !config.url.includes('/register')) {
        config.headers = {
            Authorization: 'Bearer ' + authStore.token
        };
    }

    return config;
});

export default axiosInstance;