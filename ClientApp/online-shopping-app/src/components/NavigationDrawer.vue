<template>
    <v-navigation-drawer>
        <v-list>
            <v-list-item prepend-icon="mdi-package-variant" title="Products" value="products"
                @click="clickNavItems"></v-list-item>
        </v-list>
    </v-navigation-drawer>
</template>

<script setup>
import { useRouter } from 'vue-router';

const router = useRouter();
const clickNavItems = () => {
    router.push({ path: '/products' });
};
</script>