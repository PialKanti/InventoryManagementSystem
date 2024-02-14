<template>
    <v-container>
        <h1>Products</h1>
        <v-data-table-server v-model:items-per-page="itemsPerPage" :headers="headers" :items-length="totalItems"
            :items="serverItems" :loading="loading" item-value="name" @update:options="loadItems"></v-data-table-server>
    </v-container>
</template>

<script setup>
import { ref } from 'vue';

const loading = ref(false);
const itemsPerPage = ref(0);
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

};
</script>