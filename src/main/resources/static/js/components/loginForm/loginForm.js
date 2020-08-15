Vue.component("LoginComponent", {
    template: "#login-template",

    props: {
        sourceUrl: String,
    },

    data: function () {
        return {
            login: "",
            password: "",
            wrongCredentials: false
        }
    },

    methods: {

        signIn: function () {
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
                            localStorage.setItem(btoa('jwtToken'), JSON.stringify(response.data));

                            let config = {
                                headers: {
                                    Authorization: 'Bearer ' + response.data.accessToken
                                },
                                withCredentials: true
                            };

                            // axios.get('https://maui-dev-gateway.wcms-nonprod.c.eu-de-2.cloud.sap' + '/users/getUser', config)
                            //     .then(response => {
                            //         if (response.status === 200) {
                            //             that.$store.dispatch("login", response.data);
                            //             localStorage.setItem(btoa('login'), btoa(response.data.username));
                            //         }
                            //     })
                            //     .catch(error => {
                            //         that.$store.dispatch('processError');
                            //         that.$store.dispatch("hideSpinner");
                            //     });

                            this.$router.push(this.$route.query.redirect || '/')
                        }
                    })
                    .catch(error => {
                        console.log(error.response);
                        if (error.response.status === 404) {
                            that.wrongCredentials = true;
                        }
                    });
            }
        }
    }


})