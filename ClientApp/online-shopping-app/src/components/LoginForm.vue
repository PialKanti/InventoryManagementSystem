<template>
    <v-card title="Login">
        <v-form @submit.prevent class="login-form">
            <v-text-field v-model="email" label="Email" variant="solo"></v-text-field>
            <v-text-field v-model="password" label="Password" type="password" variant="solo"></v-text-field>
            <v-btn type="submit" class="mt-2 submit-button">Submit</v-btn>
        </v-form>
    </v-card>
</template>

<script setup>
</script>

<style scoped>
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