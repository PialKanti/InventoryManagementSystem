import axios from "axios";

const getAllCategories = async () => {
    try {
        return await axios.get('/api/categories');
    } catch (error) {
        console.error('Error fetching categories:', error);
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

export { getAllCategories, deleteCategory };