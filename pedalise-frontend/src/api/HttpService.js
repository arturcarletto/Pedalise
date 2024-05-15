import Axios from "axios";
import createAuthRefreshInterceptor from "axios-auth-refresh";

const apiBaseUrl = import.meta.env.VITE_API_URL;

class HttpService{

  constructor() {
      this.axios = Axios.create({
        baseURL: apiBaseUrl,
        headers: {
          'Content-type': 'application/json',
        },
      });

    }

    async get(endpoint) {
      return this.axios.get(endpoint);
    }

    async post(endpoint, data) {
      console.log(data)
      console.log(endpoint)
      return this.axios.post(endpoint, data);
    }

    async put(endpoint, data) {
      return this.axios.put(endpoint, data);
    }

    async delete(endpoint) {
      return this.axios.delete(endpoint);
    }



}

export default new HttpService();
