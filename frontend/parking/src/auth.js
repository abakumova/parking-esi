import jwt_decode from 'jwt-decode';
import jwtDecode from "jwt-decode";

export default {
    user: {
        authenticated: false,
        role: '',
        email: '',
        userId: ''
    },
    authenticated: async function () {
        const token = localStorage.getItem('access_token');
        // if there is a token, get the user roles and ID, and try to authenticate
        if (token) {
            this.user.role = this.decodeAccessToken().role
            this.user.userId = this.decodeAccessToken().userId
            // // Set the Authorization header with the token value
            // const headers = {
            //     'Content-Type': 'application/json',
            //     Authorization: `Bearer ${token}`
            // };
            // await fetch("http://localhost:8090/api/auth/authenticate", {headers})
            //     .then((response) => response.json())
            //     .then((data) => {
            //         this.user.authenticated = data;
            //         console.log(data);
            //     })
            //     .catch(err => console.log(err.message));
            return this.user.authenticated; //returns a boolean value
        } else {
            // there is no token, we cannot authenticate
            return false;
        }
    },

    hasAnyOf: function (roles) {
        return roles.include(this.user.role)
        // if (this.user.role)
        //     return this.user.role === roles;
    },

    logout: function () {
        localStorage.removeItem('access_token');
        this.user = {role: "", email: "", authenticated: false, userId: ""};
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