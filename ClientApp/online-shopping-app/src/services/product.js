import axios from "axios"

const getAllProducts = async (currentPage, itemsPerPage) => {
    try {
        return await axios.get(`/api/products?page=${currentPage}&pageSize=${itemsPerPage}`);
    } catch (error) {
        console.log('Error occurred during fetching all products: ', error);
        throw error;
    }
}

const createProduct = async (formData) => {
    try {
        return await axios.post('/api/products', formData);
    } catch (error) {
        console.log('Error occurred during creating product: ', error);
        throw error;
    }
}

const deleteProduct = async (id) => {
    try {
        return await axios.delete(`/api/products/${id}`);
    } catch (error) {
        console.log('Error occurred during deleting product: ', error);
        throw error;
    }
}

export { getAllProducts, createProduct, deleteProduct };