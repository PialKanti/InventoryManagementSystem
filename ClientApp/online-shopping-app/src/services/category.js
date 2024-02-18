import axios from "axios";

const getAllCategories = async () => {
    try {
        const response = await axios.get('/api/categories');
        return response.data;
    } catch (error) {
        console.error('Error fetching categories:', error);
        throw error;
    }
}

export { getAllCategories };