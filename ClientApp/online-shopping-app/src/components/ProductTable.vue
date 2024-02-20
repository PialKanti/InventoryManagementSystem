<template>
    <v-container>
        <h1>Products</h1>
        <v-alert v-if="isAlertShown" closable :text="alertText" :type="alertType"></v-alert>
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
                            <v-btn color="blue-darken-1" variant="text" @click="confirmDelete">OK</v-btn>
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
import { HttpStatusCode } from 'axios';
import { ref } from 'vue';
import { getAllProducts, deleteProduct } from '@/services/product';

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
const indexOfDeletedItem = ref('');

const isAlertShown = ref(false);
const alertText = ref('');
const alertType = ref('');

const loadItems = async () => {
    loading.value = true;
    await getAllProducts(currentPage.value, itemsPerPage.value)
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
    indexOfDeletedItem.value = item.id;
    isDeleteDialogShowing.value = true;
};

const confirmDelete = async () => {
    console.log('Index of deleted item = ', indexOfDeletedItem.value);
    await deleteProduct(indexOfDeletedItem.value)
        .then(response => {
            console.log(response);
            if (response.status === HttpStatusCode.NoContent) {
                hideItemFromView(indexOfDeletedItem.value);
                showSuccessAlert('Product deleted successfully.');
            } else {
                showErrorAlert('Product deletion has been failed.');
            }
        })
        .finally(() => {
            closeDeleteDialog();
        });
};

const hideItemFromView = (id) => {
    serverItems.value = serverItems.value.filter((item) => item.id !== id);
}

const closeDeleteDialog = () => {
    isDeleteDialogShowing.value = false;
};

const showSuccessAlert = (text) => {
    showAlert(text, 'success');
}

const showErrorAlert = (text) => {
    showAlert(text, 'error');
}

const showAlert = (text, type) => {
    alertText.value = text;
    alertType.value = type;
    isAlertShown.value = true;
}
</script>