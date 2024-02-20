<template>
    <ProductForm is-update="true" :item-id="itemId" />
</template>

<script setup>
import { onMounted, ref } from 'vue';
import { useRoute } from 'vue-router';

const route = useRoute();
const itemId = ref('');

onMounted(() => {
    console.log('Id = ', route.params.id);
    itemId.value = route.params.id;
});
</script>