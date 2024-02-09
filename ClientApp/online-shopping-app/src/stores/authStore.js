import { defineStore } from "pinia";
import { ref } from "vue";

export const useAuthStore = defineStore('auth', () => {
    const isLoggedIn = ref(false);
    const token = ref('');

    return { isLoggedIn, token };
})