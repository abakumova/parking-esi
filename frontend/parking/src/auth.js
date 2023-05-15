import jwtDecode from "jwt-decode";
import {ROLES} from "@/constants/roles";

export default {
    user: {
        authenticated: false,
        role: ROLES.GUEST,
        email: '',
        userId: ''
    },
    authenticated: async function () {
        const token = localStorage.getItem('access_token');
        if (token) {
            this.setUserByCurrentToken()
            return this.user.authenticated;
        } else {
            return false;
        }
    },

    logout: function () {
        localStorage.removeItem('access_token');
        this.user = {role: ROLES.GUEST, email: "", authenticated: false, userId: ""};
    },

    getUserRole: function () {
        return this.user.role;
    },

    getToken: function() {
        return localStorage.getItem('access_token');
    },

    decodeAccessToken: function () {
        return jwtDecode(this.getToken())
    },

    isAuthenticated: function () {
        const token = this.getToken();
        return token !== null;
    },

    setUserByCurrentToken() {
        const tokenData = this.decodeAccessToken()
        const user = {
            authenticated: true,
            role: tokenData.role,
            email: tokenData.sub,
            userId: tokenData.userId
        }
        this.user = user
    },

}