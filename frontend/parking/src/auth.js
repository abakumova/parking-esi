import jwt_decode from 'jwt-decode';

export default {
    user: {
        authenticated: false,
        role: '',
        username: '',
        userId: ''
    },
    authenticated: async function () {
        const token = localStorage.getItem('access_token');
        // if there is a token, get the user roles and ID, and try to authenticate
        if (token) {
            const decoded = jwt_decode(token);
            this.user.role = decoded.role;
            this.user.id = decoded.id;
            console.log('role ' + this.user.role);
            console.log('user ID ' + this.userId);
            // Set the Authorization header with the token value
            const headers = {
                'Content-Type': 'application/json',
                Authorization: `Bearer ${token}`
            };
            await fetch("http://localhost:8090/api/auth/authenticate", {headers})
                .then((response) => response.json())
                .then((data) => {
                    this.user.authenticated = data;
                    console.log(data);
                })
                .catch(err => console.log(err.message));
            return this.user.authenticated; //returns a boolean value
        } else {
            // there is no token, we cannot authenticate
            return false;
        }
    },

    hasAnyOf: function (roles) {
        if (this.user.role)
            return this.user.role == roles;
    },

    logout: function () {
        localStorage.removeItem('access_token');
        this.user = {role: "", username: "", authenticated: false, userId: ""};
    },
    getUserRole: function () {
        return this.user.role;
    },
    getToken: function() {
        return localStorage.getItem('access_token');
    },
    isAuthenticated: function () {
        const token = this.getToken();
        return token !== null;
    }
}