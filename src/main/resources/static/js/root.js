document.addEventListener("DOMContentLoaded", () => {
    new Vue({
        el: '#news-app',

        router: Vue.prototype.router,
        store: Vue.prototype.store,
        data: () => ({

        }),

        computed: {
            isLoggedIn: function () {
                return this.$store.getters.isLoggedIn;
            }

        },

        methods: {
            hasRole: function (source, role) {
                const user = this.$store.getters.getUser;
                if(!user) {
                    return false;
                }
                const userRoles = user.roles;
                if (!userRoles) {
                    return false;
                }
                const sourceRoles = userRoles[source];
                return sourceRoles && sourceRoles.length && sourceRoles.some(e => e.name === role);
            }
        }
    });
});