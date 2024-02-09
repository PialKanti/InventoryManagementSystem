<template>
    <v-container fluid id="login-page-container" class="d-flex justify-center align-center fill-height">
        <LoginForm class="loginForm" />
    </v-container>
</template>
  
<script setup>
//
</script>

<style scoped>
#login-page-container {
    background: url('../assets/login_page_background.jpg');
    background-size: cover;
    background-position: center;
    background-repeat: no-repeat;
    overflow: hidden;
}

.loginForm {
    width: 30vw;
}

/* Hide scrollbar for Chrome, Safari, and Opera */
#login-page-container::-webkit-scrollbar {
    display: none;
}

/* Hide scrollbar for IE, Edge, and Firefox */
#login-page-container {
    -ms-overflow-style: none;
    /* IE and Edge */
    scrollbar-width: none;
    /* Firefox */
}
</style>
  