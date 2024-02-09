<template>
    <v-card title="Register">
        <v-form @submit.prevent class="register-form">
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
        </v-form>
    </v-card>
</template>

<script setup>
import { ref } from 'vue';

const firstname = ref('');
const lastname = ref('');
const username = ref('');
const email = ref('');
const password = ref('');
const confirmPassword = ref('');
</script>

<style scoped>
.register-form {
    padding-top: 30px;
    padding-left: 20px;
    padding-right: 20px;
    padding-bottom: 20px;
}
</style>