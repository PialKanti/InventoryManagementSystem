<template>
    <v-container>
        <h3>Add Product</h3>
        <v-row class="mt-4">
            <v-col cols="6">
                <v-form>
                    <v-text-field clearable label="Title" variant="outlined"></v-text-field>
                    <v-textarea label="Description" variant="outlined"></v-textarea>
                    <v-row>
                        <v-col>
                            <v-text-field label="Price" prefix="৳" variant="outlined"></v-text-field>
                        </v-col>
                        <v-col>
                            <v-text-field label="Amount" type="number" variant="outlined"></v-text-field>
                        </v-col>
                    </v-row>
                    <v-btn type="submit" class="mt-2 submit-button" color="primary">Create</v-btn>
                </v-form>
            </v-col>
        </v-row>
    </v-container>
</template>

<script setup></script>