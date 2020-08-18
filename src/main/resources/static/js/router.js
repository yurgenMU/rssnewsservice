let host = 'https://maui-dev-gateway.wcms-nonprod.c.eu-de-2.cloud.sap';

let store = Vue.prototype.store;

let jwtTokenName = btoa('jwtToken');

let utils = Window.SERVICE_UTILS;



function checkLogin() {
    if (localStorage.getItem(btoa('jwtToken'))) {
        store.dispatch("login", Window.SERVICE_UTILS.getUsername());
    }
}
//
checkLogin();


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

    template: '<div class="news__container">    ' +
        '    <news-component\n' +
        '            >\n' +
        '    </news-component></div>'
};

const EditNewsListComponent = {

    template: '<div class="news__container">    ' +
        '    <edit-user-feeds-component\>\n' +
        '    </edit-user-feeds-component></div>'
};

let redirect;
let router = new VueRouter({


    routes: [

        {
            path: '/news', component: NewsComponent, meta: {
                requiresAuth: false
            }
        },
        {
            path: '/customize', component: EditNewsListComponent, meta: {
                requiresAuth: true
            }
        },
        {
            path: '/registration', component: RegistrationComponent, meta: {
                requiresAuth: false
            }
        },
        {
            path: '/login', component: LoginComponent, meta: {
                requiresAuth: false
            }
        },
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

    if (isRedirectAllowed) {
        next()
    }
});

Vue.prototype.router = router;



