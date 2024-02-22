<template>
    <v-container>
        <v-row class="mb-5">
            <v-col>
                <h1>{{ title }}</h1>
            </v-col>
            <v-col class="text-right">
                <router-link :to="createUrl">
                    <v-btn prepend-icon="mdi-plus" color="teal-darken-1" size="small">
                        Create
                    </v-btn>
                </router-link>
            </v-col>
        </v-row>
        <v-alert v-if="isAlertShown" closable :text="alertText" :type="alertType"></v-alert>
        <v-data-table-server v-model:items-per-page="itemsPerPage" :headers="headers" :items-length="totalItems"
            :items="serverItems" :loading="loading" item-value="name" @update:options="loadItems"
            @update:page="pageChanged">
            <template v-slot:top>
                <v-dialog v-model="isDeleteDialogShowing" max-width="550px">
                    <v-card class="text-center pa-2">
                        <v-card-title class="text-h5">Are you sure you want to delete this item?</v-card-title>
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
                <router-link :to="`${updateUrlPrefix}/${item.id}`">
                    <v-btn icon="mdi-pencil-box" variant="plain" title="Edit" color="primary"></v-btn>
                </router-link>
                <v-btn icon="mdi-delete" variant="plain" title="Delete" color="error"
                    @click="showDeleteDialog(item)"></v-btn>
            </template>
        </v-data-table-server>
    </v-container>
</template>
<script setup>
import { ref } from 'vue';
import { HttpStatusCode } from 'axios';

const props = defineProps({
    title: String,
    createUrl: String,
    updateUrlPrefix: String,
    headers: Object,
    getAllItems: Function,
    deleteItem: Function
});

const alertMessage = {
    success: 'Item deleted successfully.',
    error: 'Item deletion has been failed.'
}

const currentPage = ref(0);
const loading = ref(false);
const itemsPerPage = ref(10);
const totalItems = ref(0);
const serverItems = ref([]);

const isAlertShown = ref(false);
const alertText = ref('');
const alertType = ref('');

const isDeleteDialogShowing = ref(false);
const indexOfDeletedItem = ref('');

const loadItems = async () => {
    loading.value = true;
    await props.getAllItems(currentPage.value, itemsPerPage.value)
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

const showDeleteDialog = (item) => {
    console.log('Item to be deleted = ', item);
    indexOfDeletedItem.value = item.id;
    isDeleteDialogShowing.value = true;
};

const closeDeleteDialog = () => {
    isDeleteDialogShowing.value = false;
};

const confirmDelete = async () => {
    console.log('Index of deleted item = ', indexOfDeletedItem.value);
    await props.deleteItem(indexOfDeletedItem.value)
        .then(response => {
            console.log(response);
            if (response.status === HttpStatusCode.NoContent) {
                hideItemFromView(indexOfDeletedItem.value);
                showSuccessAlert(alertMessage.success);
            } else {
                showErrorAlert(alertMessage.error);
            }
        })
        .finally(() => {
            closeDeleteDialog();
        });
};

const hideItemFromView = (id) => {
    serverItems.value = serverItems.value.filter((item) => item.id !== id);
}

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