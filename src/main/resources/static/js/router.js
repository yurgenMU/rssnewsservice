let host = 'https://maui-dev-gateway.wcms-nonprod.c.eu-de-2.cloud.sap';

let store = Vue.prototype.store;

let jwtTokenName = btoa('jwtToken');

let utils = Window.SERVICE_UTILS;

// function checkLogin() {
//     const jwtObject = utils.getJwtObject();
//     if (jwtObject) {
//         let config = {
//             headers: {
//                 Authorization: 'Bearer ' + jwtObject.accessToken
//             },
//             withCredentials: true
//         };
//         store.dispatch("showSpinner");
//         axios.get(host + '/users/getUser', config)
//             .then(response => {
//                 if (response.status === 200) {
//                     utils.setRefreshTimeout(jwtObject, host, jwtTokenName, store);
//                     store.dispatch('login', response.data);
//                     router.push(redirect || '/');
//                     store.dispatch("hideSpinner");
//                 }
//             })
//             .catch(error => {
//                 if (error.response.status === 401) {
//                     utils.refreshToken(host, {
//                         headers: {
//                             Authorization: 'Bearer ' + jwtObject.refreshToken
//                         },
//                         withCredentials: true
//                     }, jwtTokenName, store);
//                 }
//                 console.log(error.response);
//                 store.dispatch('processError');
//                 store.dispatch("hideSpinner");
//             });
//     }
// }
//
// checkLogin();


const LoginComponent = {

    template: '<div class="login-example__container">    ' +
        '    <login-component\n' +
        '            :source-url="\'/login\'"\n' +
        '            >\n' +
        '    </login-component></div>'
};

const RegistrationComponent = {

    template: '<div class="login-example__container">    ' +
        '    <registration-component\n' +
        '            :source-url="\'/register\'"\n' +
        '            >\n' +
        '    </registration-component></div>'
};

const NewsComponent = {

    template: '<div class="login-example__container">    ' +
        '    <news-component\n' +
        '            :source-url="\'/register\'"\n' +
        '            >\n' +
        '    </news-component></div>'
};

let redirect;
let router = new VueRouter({


    routes: [
        // {
        //     path: '/search', component: SearchComponent, meta: {
        //         requiresAuth: true
        //     }
        // },
        // {
        //     path: '/imageSearch', component: ImageSearchComponent, meta: {
        //         requiresAuth: true
        //     }
        // },
        // {
        //     path: '/secured/imageSearch', component: SecuredImageSearchComponent, meta: {
        //         requiresAuth: true,
        //         hasRole: ["MAUI test role", "MAUI admin role"]
        //     }
        // },
        {path: '/login', component: LoginComponent},
        {path: '/registration', component: RegistrationComponent},
    ]
});

router.beforeEach((to, from, next) => {
    let isRedirectAllowed = true;
    if (to.matched.some(record => record.meta.requiresAuth)) {
        redirect = to.fullPath !== '/' ? to : undefined;
        if (!store.getters.isLoggedIn) {
            router.push({query: {redirect: to}});
            next({
                path: '/login',
                query: {redirect: to.fullPath}
            })
        }
    }
    if (to.matched.some(record => record.meta.hasRole) && store.getters.getUser) {
        redirect = to.fullPath !== '/' ? to : undefined;
        const roles = to.matched.map(record => record.meta.hasRole[0]);
        isRedirectAllowed = isHasRole(roles, store.getters.getUser.roles);
    }
    if (isRedirectAllowed) {
        next()
    }
});

function isHasRole(sourceRoles, userSourceRolesMap) {
    if (!userSourceRolesMap) {
        return false;
    }

    let userRoles = userSourceRolesMap['MAUI'];
    if (!userRoles) {
        return false;
    }
    userRoles = userRoles.map(e => e.name);

    return sourceRoles.some(sourceRole => userRoles.includes(sourceRole));
}

Vue.prototype.router = router;



