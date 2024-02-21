<template>
    <CategoryForm is-update="true" :item-id="route.params.id" />
</template>

<script setup>
import { onMounted, ref } from 'vue';
import { useRoute } from 'vue-router';

const route = useRoute();
</script>