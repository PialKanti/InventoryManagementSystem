import { defineStore } from "pinia";
import { ref } from "vue";

export const useAuthStore = defineStore('auth', () => {
    const isLoggedIn = ref(false);
    const token = ref('');
    const firstname = ref('');
    const lastname = ref('');

    return { isLoggedIn, token, firstname, lastname };
}, { persist: true })