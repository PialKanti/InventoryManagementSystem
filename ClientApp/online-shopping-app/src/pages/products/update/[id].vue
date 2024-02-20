<template></template>

<script setup>
import { onMounted } from 'vue';
import { useRoute } from 'vue-router';

const route = useRoute();

onMounted(() => {
    console.log('Id = ', route.params.id);
});
</script>