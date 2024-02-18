<template>
    <v-card title="Login">
        <v-form @submit.prevent="login" class="login-form">
            <v-alert class="login-alert" v-if="isAlertShown" :text="alertMessage" :type="alertType"></v-alert>
            <v-text-field v-model="username" label="Username" variant="solo"></v-text-field>
            <v-text-field v-model="password" label="Password" type="password" variant="solo"></v-text-field>
            <v-btn type="submit" class="mt-2 submit-button">Submit</v-btn>
            <p class="register-text text-center">Don't have an account? <router-link to="/register"
                    class="register-link">Register</router-link></p>
        </v-form>
    </v-card>
</template>

<script setup>
import { useAuthStore } from '@/stores/authStore';
import axios, { HttpStatusCode } from 'axios';
import { ref } from 'vue';
import { useRouter } from 'vue-router';
import { deleteLocalStore } from '@/utils/localStore';

const username = ref('');
const password = ref('');

const isAlertShown = ref(false);
const alertMessage = ref('');
const alertType = ref('');

const router = useRouter();
const authStore = useAuthStore();

const login = async () => {
    const data = {
        username: username.value,
        password: password.value
    };

    deleteLocalStore();

    await axios.post('/api/auth/login', data)
        .then(async response => {
            console.log(response);
            if (response.status === HttpStatusCode.Ok) {
                authStore.isLoggedIn = true;
                authStore.token = response.data.access_token;

                const user = await fetchUserInfo();
                if (user) {
                    authStore.firstname = user.firstName;
                    authStore.lastname = user.lastName;
                }


                router.push({ path: '/' });
            }
        })
        .catch(error => {
            const data = error.response.data;
            if (data.status === HttpStatusCode.NotFound || data.status === HttpStatusCode.Unauthorized) {
                showErrorAlert('Username or password is incorrect.');
            } else {
                showErrorAlert('Something went wrong. Please try again.');
            }
        });
};

const fetchUserInfo = async () => {
    return await axios.get(`api/users/${username.value}`)
        .then(response => response.data);
}

const showErrorAlert = (message) => {
    isAlertShown.value = true;
    alertType.value = 'error';
    alertMessage.value = message;
};
</script>

<style scoped>
.login-alert {
    margin-bottom: 30px;
}

.login-form {
    padding-top: 30px;
    padding-left: 20px;
    padding-right: 20px;
    padding-bottom: 20px;
}

.submit-button {
    background-color: #3ab8db;
    color: white;
}

.register-text {
    margin-top: 25px;
    font-size: 14px;
}

.register-link {
    text-decoration: underline;
    color: #15a8d1;
}
</style>