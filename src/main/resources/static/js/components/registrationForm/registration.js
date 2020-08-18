Vue.component("RegistrationComponent", {
    template: "#registration-template",

    props: {
        sourceUrl: String,
    },

    data: function () {
        return {
            login: "",
            password: "",
            userExists: false
        }
    },

    methods: {

        register: function () {
            const login = this.login;
            const password = this.password;
            const url = this.sourceUrl;
            let that = this;
            if (url && url.length !== 0 && login.length !== 0 && password.length !== 0) {
                const credentials = {
                    login: login,
                    password: btoa(password)
                };
                axios.post(url, credentials, {withCredentials: true})
                    .then((response) => {
                        if (response.status === 200) {
                            localStorage.setItem(btoa('jwtToken'), btoa(JSON.stringify(response.data)));
                            that.$store.dispatch("login", login);
                            this.$router.push(this.$route.query.redirect || '/')
                        }
                    })
                    .catch(error => {
                        console.log(error.response);
                        if (error.response.status === 400) {
                            that.userExists = true;
                        }
                    });
            }
        }
    }
})