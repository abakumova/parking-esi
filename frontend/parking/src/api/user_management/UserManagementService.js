import HttpService from "@/api/HttpService";
import {API_USERS_ROUTE} from "@/api/routes";
import {TOAST_CLOSE_TIME} from "@/constants/toast";

class UserManagementService extends HttpService{

    constructor() {
        super(API_USERS_ROUTE)
    }

    async getUserById(id, token) {
        try {
            const response = await this.http.get(`/${id}`, {
                headers: {
                    'Authorization': `Bearer ${token}`
                }
            });
            this.toast.success(`Got user successfully!`,{
                autoClose:TOAST_CLOSE_TIME
            })

            return response.data;
        } catch (e){
            this.toast.error(`Get user failed!\n${e.toString()}`,{
                autoClose:TOAST_CLOSE_TIME
            })
        }
    }

    async updateUser(id, user) {
        try {
            const response = await this.http.put(`/${id}`, user);

            this.toast.success(`Updated user successfully!`,{
                autoClose:TOAST_CLOSE_TIME
            })

            return response.data;
        } catch (e){
            this.toast.error(`Update user failed!\n${e.toString()}`,{
                autoClose:TOAST_CLOSE_TIME
            })
        }
    }

    async deleteUser(id) {
        try {
            const response = await this.http.delete(`/${id}`);

            this.toast.success(`Updated user successfully!`,{
                autoClose:TOAST_CLOSE_TIME
            })

            return response.data;
        } catch (e){
            this.toast.error(`Update user failed!\n${e.toString()}`,{
                autoClose:TOAST_CLOSE_TIME
            })
        }
    }

    async getUserBalance(id) {
        const response = await this.http.get(`/${id}/balance`);
        return response.data;
    }

    async updateUserBalance(id, balance) {
        const response = await this.http.put(`/${id}/balance`, { balance });
        return response.data;
    }

    async getUsers({limit, page}) {
        try {
            const response = await this.http.get(``, {
                params: {
                    limit: limit,
                    page: page
                }
            });

            this.toast.success(`Updated user successfully!`,{
                autoClose:TOAST_CLOSE_TIME
            })

            return response.data;
        } catch (e){
            this.toast.error(`Update user failed!\n${e.toString()}`,{
                autoClose:TOAST_CLOSE_TIME
            })
        }
    }

    async createUser(user) {
        const response = await this.http.post('', user);
        return response.data;
    }
}

export default new UserManagementService();
