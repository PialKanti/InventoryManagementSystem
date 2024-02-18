import { deleteLocalStore } from '@/utils/localStore';

const performLogout = () => {
    deleteLocalStore();
    this.$router.push({ path: '/login' });
}

export { performLogout };