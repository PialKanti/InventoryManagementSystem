<template>
    <v-app-bar :elevation="5">
        <template v-slot:prepend>
            <v-app-bar-nav-icon></v-app-bar-nav-icon>
        </template>

        <v-app-bar-title>Online Shopping</v-app-bar-title>
        <v-menu open-on-hover>
            <template v-slot:activator="{ props }">
                <v-avatar class="avatar-icon bg-primary" v-bind="props">
                    <span class="text-h5">{{ initials }}</span>
                </v-avatar>
            </template>

            <v-card>
                <v-list class="avatar-menu">
                    <v-list-item class="text-center">
                        <v-list-item-title>Hello,</v-list-item-title>
                        <v-list-item-subtitle class="userinfo-subtitle">{{ name }}</v-list-item-subtitle>
                    </v-list-item>
                </v-list>
                <v-divider></v-divider>
                <v-list>
                    <v-list-item link class="text-center" @click="logout">
                        <v-list-item-title>
                            Logout
                        </v-list-item-title>
                    </v-list-item>
                </v-list>
            </v-card>
        </v-menu>


    </v-app-bar>
</template>

<script setup>
import { useAuthStore } from '@/stores/authStore';
import { HttpStatusCode } from 'axios';
import axiosInstance from '@/plugins/axios';
import { computed } from 'vue';
import { ref } from 'vue';
import { performLogout } from '@/services/auth';

const name = ref('');
const initials = computed(() => {
    return getFirstLetterUpperCase(authStore.firstname) + getFirstLetterUpperCase(authStore.lastname);
});

const authStore = useAuthStore();
name.value = authStore.firstname + ' ' + authStore.lastname;

const getFirstLetterUpperCase = (word) => {
    if (!word) {
        return '';
    }

    return word.charAt(0).toUpperCase();
};

const logout = async () => {
    await axiosInstance.get('/api/auth/logout')
        .then(response => {
            if (response.status === HttpStatusCode.NoContent) {
                performLogout();
            }
        })
        .catch(error => {
            console.error('Error occured during logout.');
            console.error(error);
        });
};
</script>

<style scoped>
.avatar-icon {
    margin-right: 20px;
    cursor: pointer;
}

.avatar-menu {
    padding-left: 20px;
    padding-right: 20px;
}

.userinfo-subtitle {
    margin-top: 5px;
}
</style>
