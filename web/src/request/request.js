import axios from 'axios'

const instance  = axios.create({
    baseURL:  '/api',
    timeout: 400000
})

instance.defaults.withCredentials = true 

instance.interceptors.request.use(config => {
    config.headers.token = sessionStorage.getItem('token');
    return config;
}, err => {
    return Promise.reject(err)
})

instance.interceptors.response.use(res => {
    return res;
}, err => {
    return Promise.reject(err);
})

export default instance