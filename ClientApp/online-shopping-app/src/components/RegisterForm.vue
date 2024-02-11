<template>
    <v-card title="Register">
        <v-form @submit.prevent="register" class="register-form">
            <v-alert class="register-alert" v-if="isAlertShown" :text="alertMessage" :type="alertType"></v-alert>
            <v-row>
                <v-col>
                    <v-text-field v-model="firstname" label="First Name" variant="solo"></v-text-field>
                </v-col>
                <v-col>
                    <v-text-field v-model="lastname" label="Last Name" variant="solo"></v-text-field>
                </v-col>
            </v-row>
            <v-text-field v-model="username" label="Username" variant="solo"></v-text-field>
            <v-text-field v-model="email" label="Email" variant="solo"></v-text-field>
            <v-text-field v-model="password" label="Password" type="password" variant="solo"></v-text-field>
            <v-text-field v-model="confirmPassword" label="Confirm Password" type="password" variant="solo"></v-text-field>
            <v-btn type="submit" class="mt-2 submit-button">Submit</v-btn>
            <p class="login-text text-center">Already have an account? <router-link to="/login"
                    class="login-link">Login</router-link></p>
        </v-form>
    </v-card>
</template>

<script setup>
import axios, { HttpStatusCode } from 'axios';
import { ref } from 'vue';
import { useRouter } from 'vue-router';

const firstname = ref('');
const lastname = ref('');
const username = ref('');
const email = ref('');
const password = ref('');
const confirmPassword = ref('');

const router = useRouter();

const register = async () => {
    const data = {
        firstName: firstname.value,
        lastName: lastname.value,
        username: username.value,
        email: email.value,
        password: password.value
    };

    console.log(data);

    await axios.post('/api/auth/register', data)
        .then(response => {
            console.log(response);

            if (response.status === HttpStatusCode.Ok) {
                router.push({ path: '/login' });
            }
        })
        .catch(error => {
            console.error('Error occured.', error);
            showErrorAlert('Something went wrong. Please try again.');
        });

};

const showErrorAlert = (message) => {
    isAlertShown.value = true;
    alertType.value = 'error';
    alertMessage.value = message;
};
</script>

<style scoped>
.register-form {
    padding-top: 30px;
    padding-left: 20px;
    padding-right: 20px;
    padding-bottom: 20px;
}

.submit-button {
    background-color: #3ab8db;
    color: white;
}

.login-text {
    margin-top: 25px;
    font-size: 14px;
}

.login-link {
    text-decoration: underline;
    color: #15a8d1;
}
</style>