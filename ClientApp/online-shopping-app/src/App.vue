<template>
  <v-app>
    <AppBar v-if="showAppbar" />
    <v-main>
      <router-view />
    </v-main>
  </v-app>
</template>

<script setup>
import { ref, watch } from 'vue';
import { useRoute } from 'vue-router';

const showAppbar = ref(false);

const route = useRoute();
watch(() => route.path, (path) => {
  showAppbar.value = path !== '/login' && path !== '/register';
});
</script>
