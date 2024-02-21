import axios from "axios";

const getAllCategories = async () => {
    try {
        return await axios.get('/api/categories');
    } catch (error) {
        console.error('Error fetching categories:', error);
        throw error;
    }
}

const getCategory = async (id) => {
    try {
        return await axios.get(`/api/categories/${id}`);
    } catch (error) {
        console.log('Error occurred during fetching category: ', error);
        throw error;
    }
}

const createCategory = async (formData) => {
    try {
        return await axios.post('/api/categories', formData);
    } catch (error) {
        console.log('Error occurred during creating category: ', error);
        throw error;
    }
}

const updateCategory = async (id, formData) => {
    try {
        return await axios.put(`/api/categories/${id}`, formData);
    } catch (error) {
        console.log('Error occurred during updating category: ', error);
        throw error;
    }
}

const deleteCategory = async (id) => {
    try {
        return await axios.delete(`/api/categories/${id}`);
    } catch (error) {
        console.log('Error occurred during deleting categories: ', error);
        throw error;
    }
}

export { getAllCategories, getCategory, createCategory, updateCategory, deleteCategory };