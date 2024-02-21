<template>
    <v-container>
        <h3>{{ isUpdate ? 'Update' : 'Add' }} Category</h3>
        <v-row class="mt-4">
            <v-col cols="6">
                <v-form @submit.prevent="submitForm">
                    <v-text-field v-model="name" label="Name" variant="outlined" density="comfortable"></v-text-field>
                    <v-btn type="submit" class="mt-2 submit-button" color="primary">{{ isUpdate ? 'Update' : 'Create'
                    }}</v-btn>
                </v-form>
            </v-col>
        </v-row>
    </v-container>
</template>
<script setup>
import { ref, onMounted } from 'vue';
import { createCategory, updateCategory, getCategory } from '@/services/category';
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

const name = ref('');
const router = useRouter();

const submitForm = async () => {
    const data = getFormData();
    console.log('Form Data = ', data);
    if (props.isUpdate) {
        await updateCategory(props.itemId, data)
            .then(response => {
                console.log('Update product response = ', response);
                if (response.status === HttpStatusCode.Ok) {
                    router.push({ path: '/categories' });
                }
            });

    } else {
        await createCategory(data)
            .then(response => {
                console.log('Create category response = ', response);
                if (response.status === HttpStatusCode.Created) {
                    router.push({ path: '/categories' });
                }
            });
    }
}

const getFormData = () => {
    const data = {
        name: name.value
    }

    if (props.isUpdate) {
        data.id = props.itemId;
    }

    return data;
}

onMounted(async () => {
    if (props.isUpdate) {
        await getCategory(props.itemId)
            .then(response => {
                const category = response.data;
                console.log('Category = ', category);

                name.value = category.name;
            })
    }
})
</script>