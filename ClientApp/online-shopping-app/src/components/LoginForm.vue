<template>
    <v-card title="Login">
        <v-form @submit.prevent class="login-form">
            <v-text-field v-model="email" label="Email" variant="outlined"></v-text-field>
            <v-text-field v-model="password" label="Password" type="password" variant="outlined"></v-text-field>
            <v-btn type="submit" class="mt-2" variant="outlined">Submit</v-btn>
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
</style>