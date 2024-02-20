import router from '@/router';
import { deleteLocalStore } from '@/utils/localStore';

const performLogout = () => {
    deleteLocalStore();
    router.push({ path: '/login' });
}

export { performLogout };