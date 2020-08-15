Window.SERVICE_UTILS = {

    setRefreshTimeout: function (jwtObject, host, jwtTokenName, store) {
        var that = this;
        setTimeout(() => {
            let data = {
                refreshToken: jwtObject.refreshToken
            };
            let config = {
                headers: {
                    Authorization: 'Bearer ' + jwtObject.refreshToken
                },
                withCredentials: true
            };
            that.refreshToken(host, config, jwtTokenName, store);
        }, that.getTimeoutValue(jwtObject));
    },

    refreshToken: function (host, config, jwtTokenName, store) {
        var that = this;
        axios.post(host + '/oauth/refresh-token', undefined,
            config)
            .then(response => {
                let responseData = response.data;
                localStorage.setItem(jwtTokenName, JSON.stringify(responseData));
                that.setRefreshTimeout(responseData, jwtTokenName)
            })
            .catch(error => {
                console.log(error.response);
                store.dispatch('auth_error');
            });
    },

    getTimeoutValue: function (jwtObject) {
        let timeLeft = moment.duration(moment(jwtObject.expiration)
            .diff(moment()))
            .asMilliseconds();
        return timeLeft > 60000 ? timeLeft - 60000 : 0;
    },

    getJwtObject: function () {
        const jwtTokenName = btoa('jwtToken');
        const jwtToken = localStorage.getItem(jwtTokenName);
        if (jwtToken) {
            return JSON.parse(jwtToken);
        }
    }
}