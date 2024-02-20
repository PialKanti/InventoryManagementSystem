<template>
    <v-container>
        <h1>Products</h1>
        <v-data-table-server v-model:items-per-page="itemsPerPage" :headers="headers" :items-length="totalItems"
            :items="serverItems" :loading="loading" item-value="name" @update:options="loadItems"
            @update:page="pageChanged">
            <template v-slot:top>
                <v-dialog v-model="isDeleteDialogShowing" max-width="550px">
                    <v-card class="text-center pa-2">
                        <v-card-title class="text-h5">Are you sure you want to delete this product?</v-card-title>
                        <v-card-actions>
                            <v-spacer></v-spacer>
                            <v-btn color="blue-darken-1" variant="text" @click="closeDeleteDialog">Cancel</v-btn>
                            <v-btn color="blue-darken-1" variant="text">OK</v-btn>
                            <v-spacer></v-spacer>
                        </v-card-actions>
                    </v-card>
                </v-dialog>
            </template>
            <template v-slot:item.action="{ item }">
                <v-btn icon="mdi-pencil-box" variant="plain" title="Edit" color="primary"></v-btn>
                <v-btn icon="mdi-delete" variant="plain" title="Delete" color="error" @click="deleteItem(item)"></v-btn>
            </template>
        </v-data-table-server>
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
    title: 'Price',
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

const isDeleteDialogShowing = ref(false);

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

const deleteItem = (item) => {
    console.log('Item to be deleted = ', item);
    const id = serverItems.value.indexOf(item);
    console.log('Id = ', item.id);
    isDeleteDialogShowing.value = true;
};

const closeDeleteDialog = () => {
    isDeleteDialogShowing.value = false;
};
</script>