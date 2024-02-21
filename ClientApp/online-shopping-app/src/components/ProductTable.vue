<template>
    <BaseTable title="Products" updateUrlPrefix="/products/update" :headers="headers" :getAllItems="getAllProducts"
        :deleteItem="deleteProduct" />
</template>

<script setup>
import { ref } from 'vue';
import { getAllProducts, deleteProduct } from '@/services/product';

const headers = ref([{
    title: 'Title',
    align: 'center',
    sortable: false,
    key: 'title',
},
{
    title: 'Description',
    align: 'center',
    sortable: false,
    key: 'description',
},
{
    title: 'Price (৳)',
    align: 'center',
    sortable: false,
    key: 'price',
},
{
    title: 'Quantity',
    align: 'center',
    sortable: false,
    key: 'quantity',
},
{
    title: 'Action',
    align: 'center',
    sortable: false,
    key: 'action'
}]);
</script>