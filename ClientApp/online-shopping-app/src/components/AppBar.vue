<template>
    <v-app-bar :elevation="5">
        <template v-slot:prepend>
            <v-app-bar-nav-icon></v-app-bar-nav-icon>
        </template>

        <v-app-bar-title>Online Shopping</v-app-bar-title>
        <v-menu open-on-hover>
            <template v-slot:activator="{ props }">
                <!-- <v-btn color="primary">
                    Dropdown
                </v-btn> -->
                <v-avatar class="avatar-icon bg-primary" v-bind="props">
                    <span class="text-h5">{{ initials }}</span>
                </v-avatar>
            </template>

            <v-card>
                <v-list class="avatar-menu">
                    <v-list-item-content class="text-center">
                        <v-list-item-title>Hello,</v-list-item-title>
                        <v-list-item-subtitle>{{ name }}</v-list-item-subtitle>
                    </v-list-item-content>
                </v-list>
                <v-divider></v-divider>
                <v-list class="avatar-menu">
                    <v-list-item link>
                        <v-list-item-title>
                            <a>Logout</a>
                        </v-list-item-title>
                    </v-list-item>
                </v-list>
            </v-card>
        </v-menu>


    </v-app-bar>
</template>

<script setup>
import { useAuthStore } from '@/stores/authStore';
import { computed } from 'vue';
import { ref } from 'vue';

const name = ref('');
const initials = computed(() => {
    return getFirstLetterUpperCase(authStore.firstname) + getFirstLetterUpperCase(authStore.lastname);
});

const authStore = useAuthStore();
name.value = authStore.firstname + ' ' + authStore.lastname;

const getFirstLetterUpperCase = (word) => {
    if (!word) {
        return '';
    }

    return word.charAt(0).toUpperCase();
};
</script>

<style scoped>
.avatar-icon {
    margin-right: 20px;
    cursor: pointer;
}

.avatar-menu {
    padding-left: 20px;
    padding-right: 20px;
}
</style>