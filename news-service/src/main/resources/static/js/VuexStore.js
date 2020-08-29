Vue.prototype.store = new Vuex.Store({
    state: {
        loggedIn: false,
        user: undefined,
        showSpinner: false,

    },
    mutations: {

        auth_success(state, user) {
            state.loggedIn = true;
            state.user = user
        },

        auth_error(state) {
            state.loggedIn = false;
        },

        logout(state) {
            state.loggedIn = false;
            state.user = undefined;
        },

        show_spinner(state) {
            state.showSpinner = true;
        },

        hide_spinner(state) {
            state.showSpinner = false;
        }
    },
    actions: {

        login({commit}, user) {
            commit('auth_success', user);
        },


        logout({commit}) {
            commit('logout');
        },

        processError({commit}) {
            commit('auth_error');
        },

        showSpinner({commit}) {
            commit('show_spinner');
        },

        hideSpinner({commit}) {
            commit('hide_spinner');
        },

    },

    getters: {
        isLoggedIn(state) {
            return state.loggedIn;
        },

        getUser(state) {
            return state.user;
        },

        showSpinner(state) {
            return state.showSpinner;
        },

        getChosenFeeds (state) {
            return state.chosenFeeds;
        },

        getAllFeeds (state) {
            return state.allFeeds;
        }
    }
})