<template>
    <v-container>
        <h1>Products</h1>
        <v-data-table-server v-model:items-per-page="itemsPerPage" :headers="headers" :items-length="totalItems"
            :items="serverItems" :loading="loading" item-value="name" @update:options="loadItems"
            @update:page="pageChanged"></v-data-table-server>
    </v-container>
</template>

<script setup>
import axios, { HttpStatusCode } from 'axios';
import { ref } from 'vue';

const currentPage = ref(0);
const loading = ref(false);
const itemsPerPage = ref(10);
const totalItems = ref(0);
const serverItems = ref([]);
const headers = ref([{
    title: 'Title',
    align: 'start',
    sortable: false,
    key: 'title',
},
{
    title: 'Description',
    align: 'end',
    sortable: false,
    key: 'description',
},
{
    title: 'Price',
    align: 'end',
    sortable: false,
    key: 'price',
},
{
    title: 'Quantity',
    align: 'end',
    sortable: false,
    key: 'quantity',
}]);

const loadItems = async () => {
    loading.value = true;
    await axios.get(`/api/products?page=${currentPage.value}&pageSize=${itemsPerPage.value}`)
        .then(response => {
            if (response.status === HttpStatusCode.Ok) {
                console.log('Data found');
                const data = response.data;
                console.log(data);

                totalItems.value = data.totalItems;
                serverItems.value = data.data;
            }
        })
        .catch(error => {
            console.error('Error occured. ', error);
        })
        .finally(() => {
            loading.value = false;
        })
};

const pageChanged = (page) => {
    console.log('Page = ', page);
    currentPage.value = page - 1;
};
</script>