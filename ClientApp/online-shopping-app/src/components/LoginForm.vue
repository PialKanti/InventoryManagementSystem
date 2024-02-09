<template>
    <v-card title="Login">
        <v-form @submit.prevent="login" class="login-form">
            <v-alert class="login-alert" v-if="isAlertShown" :text="alertMessage" :type="alertType"></v-alert>
            <v-text-field v-model="username" label="Username" variant="solo"></v-text-field>
            <v-text-field v-model="password" label="Password" type="password" variant="solo"></v-text-field>
            <v-btn type="submit" class="mt-2 submit-button">Submit</v-btn>
        </v-form>
    </v-card>
</template>

<script setup>
import axios, { HttpStatusCode } from 'axios';
import { ref } from 'vue';

const username = ref('');
const password = ref('');

const isAlertShown = ref(false);
const alertMessage = ref('');
const alertType = ref('');

const login = async () => {
    const data = {
        username: username.value,
        password: password.value
    };

    await axios.post('/api/auth/login', data)
        .then(response => {
            console.log(response);
        })
        .catch(error => {
            const data = error.response.data;
            if (data.status === HttpStatusCode.NotFound || data.status === HttpStatusCode.Unauthorized) {
                showErrorAlert('Username or password is not correct.');
            } else {
                showErrorAlert('Something went wrong. Please try again.');
            }
        });
};

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
</style>