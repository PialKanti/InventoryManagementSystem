<template>
    <BaseTable title="Categories" updateUrlPrefix="/categories/update" :headers="headers" :getAllItems="getAllCategories"
        :deleteItem="deleteCategory" />
</template>
<script setup>
import { ref } from 'vue';
import { getAllCategories, deleteCategory } from '@/services/category';

const headers = ref([{
    title: 'Name',
    align: 'center',
    sortable: false,
    key: 'name',
},
{
    title: 'Action',
    align: 'center',
    sortable: false,
    key: 'action'
}]);
</script>