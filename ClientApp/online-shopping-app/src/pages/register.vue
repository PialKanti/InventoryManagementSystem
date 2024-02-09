<template>
    <v-container fluid id="register-page-container" class="d-flex justify-center align-center fill-height">
        <RegisterForm class="registerForm" />
    </v-container>
</template>

<script setup></script>

<style scoped>
#register-page-container {
    background: url('../assets/login_page_background.jpg');
    background-size: cover;
    background-position: center;
    background-repeat: no-repeat;
    overflow: hidden;
}

.registerForm {
    width: 35vw;
}
</style>