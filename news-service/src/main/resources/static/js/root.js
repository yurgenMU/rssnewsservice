document.addEventListener("DOMContentLoaded", () => {
    new Vue({
        el: '#news-app',

        router: Vue.prototype.router,
        store: Vue.prototype.store,
        data: () => ({}),

        computed: {
            isLoggedIn: function () {
                return this.$store.getters.isLoggedIn;
            },

            username: function () {
                return this.$store.getters.login;
            }

        },

        methods: {

            loadFeeds: function (context, afterLoadCallback) {
                let accessToken = Window.SERVICE_UTILS.getJwtObject();
                let config = {};
                let that = this;
                axios.get('/api/v1/news/all', config)
                    .then(response => {
                        if (response.status === 200) {

                            context.allFeeds = response.data;
                            context.chosenFeeds = response.data;

                            if (accessToken) {
                                that.$store.dispatch("login", );
                                config = {
                                    headers: {
                                        Authorization: 'Bearer ' + accessToken
                                    },
                                };

                                Window.SERVICE_UTILS.getChosenFeedsForUser(context, config, afterLoadCallback);
                            } else if (afterLoadCallback) {
                                afterLoadCallback();
                            }
                        }
                    })
                    .catch(error => {
                        that.$store.dispatch('processError');
                        that.$store.dispatch("hideSpinner");
                    });

            },

            logout: function () {
                localStorage.removeItem(btoa('jwtToken'));
                this.$store.dispatch("logout");
                this.$store.dispatch('setChosenFeeds', this.$store.getters.getAllFeeds);
                let loginPath = '/login';
                if (this.$route.path !== loginPath) {
                    this.$router.push(loginPath);
                }
            }
        }
    });
});