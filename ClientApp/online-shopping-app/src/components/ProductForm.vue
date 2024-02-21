<template>
    <v-container>
        <h3>{{ isUpdate ? 'Update' : 'Add' }} Product</h3>
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
                    <v-btn type="submit" class="mt-2 submit-button" color="primary">{{ isUpdate ? 'Update' : 'Create'
                    }}</v-btn>
                </v-form>
            </v-col>
        </v-row>
    </v-container>
</template>

<script setup>
import { getAllCategories } from '@/services/category';
import { onMounted, ref } from 'vue';
import { createProduct, getProduct, updateProduct } from '@/services/product';
import { HttpStatusCode } from 'axios';
import { useRouter } from 'vue-router';

const props = defineProps({
    isUpdate: {
        type: Boolean,
        default: false
    },
    itemId: {
        type: Number
    }
});

const router = useRouter();
const categories = ref([]);

const title = ref('');
const description = ref('');
const category = ref('');
const price = ref('');
const quantity = ref('');

const getFormData = () => {
    const data = {
        title: title.value,
        description: description.value,
        price: price.value,
        quantity: quantity.value,
        categoryId: category.value
    }

    if (props.isUpdate) {
        data.id = props.itemId;
    }

    return data;
}

const submitForm = async () => {
    const data = getFormData();
    console.log('Form Data = ', data);
    if (props.isUpdate) {
        await updateProduct(props.itemId, data)
            .then(response => {
                console.log('Update product response = ', response);
                if (response.status === HttpStatusCode.Ok) {
                    router.push({ path: '/products' });
                }
            });

    } else {
        await createProduct(data)
            .then(response => {
                console.log('Create product response = ', response);
                if (response.status === HttpStatusCode.Created) {
                    router.push({ path: '/products' });
                }
            });
    }
}

onMounted(async () => {
    await getAllCategories()
        .then(response => {
            console.log('categories = ', response);
            categories.value = response.data.data;
        });

    if (props.isUpdate) {
        await getProduct(props.itemId)
            .then(response => {
                const product = response.data;
                console.log('Product = ', product);

                title.value = product.title;
                description.value = product.description;
                category.value = product.category.id;
                price.value = product.price;
                quantity.value = product.quantity;
            })
    }
})
</script>