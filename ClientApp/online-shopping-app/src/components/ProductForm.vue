<template>
    <v-container>
        <h3>Add Product</h3>
        <v-row class="mt-4">
            <v-col cols="6">
                <v-form @submit.prevent="submitForm">
                    <v-text-field v-model="title" label="Title" variant="outlined" density="comfortable"></v-text-field>
                    <v-textarea v-model="description" label="Description" variant="outlined"
                        density="comfortable"></v-textarea>
                    <v-select v-model="category" label="Category" :items="categories" item-title="name" item-value="id"
                        variant="outlined" density="comfortable"></v-select>
                    <v-row>
                        <v-col>
                            <v-text-field v-model="price" label="Price" prefix="৳" variant="outlined"
                                density="comfortable"></v-text-field>
                        </v-col>
                        <v-col>
                            <v-text-field v-model="quantity" label="Amount" type="number" variant="outlined"
                                density="comfortable"></v-text-field>
                        </v-col>
                    </v-row>
                    <v-btn type="submit" class="mt-2 submit-button" color="primary">Create</v-btn>
                </v-form>
            </v-col>
        </v-row>
    </v-container>
</template>

<script setup>
import { getAllCategories } from '@/services/category';
import { onMounted, ref } from 'vue';
import { createProduct } from '@/services/product';
import { HttpStatusCode } from 'axios';
import { useRouter } from 'vue-router';

const router = useRouter();
const categories = ref([]);

const title = ref('');
const description = ref('');
const category = ref('');
const price = ref('');
const quantity = ref('');

const getFormData = () => {
    return {
        title: title.value,
        description: description.value,
        categoryId: category.value,
        price: price.value,
        quantity: quantity.value
    }
}

const submitForm = async () => {
    const data = getFormData();
    console.log('Form Data = ', data);
    await createProduct(data)
        .then(response => {
            console.log(response);
            if (response.status === HttpStatusCode.Created) {
                router.push({ path: '/products' });
            }
        })
}

onMounted(async () => {
    await getAllCategories()
        .then(response => {
            console.log('categories = ', response);
            categories.value = response.data;
        });
})
</script>