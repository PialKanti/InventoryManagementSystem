import axios from "axios"

const createProduct = async (formData) => {
    try {
        return await axios.post('/api/products', formData);
    } catch (error) {
        console.log('Error occurred during creating product: ', error);
        throw error;
    }
}

export { createProduct };