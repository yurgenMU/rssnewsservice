Window.SERVICE_UTILS = {

    getChosenFeedsForUser: function (context, config, afterLoadCallback) {
        axios.get('/api/v1/news/custom', config)
            .then(customFeedResponse => {
                if (customFeedResponse.status === 200) {
                    let chosenFeeds = customFeedResponse.data;
                    if (chosenFeeds.length !== 0) {
                        context.chosenFeeds = chosenFeeds;
                    }
                    if (afterLoadCallback) {
                        afterLoadCallback();
                    }
                }
            })
            .catch(error => {
                context.$store.dispatch('processError');
                context.$store.dispatch("hideSpinner");
            });
    },

    getJwtObject: function () {
        const jwtTokenName = btoa('jwtToken');
        const jwtToken = localStorage.getItem(jwtTokenName);
        if (jwtToken) {
            return JSON.parse(atob(jwtToken)).accessToken;
        }
    },

    getUsername: function () {
        const jwtTokenName = btoa('jwtToken');
        const jwtToken = localStorage.getItem(jwtTokenName);
        if (jwtToken) {
            return JSON.parse(atob(jwtToken)).login;
        }
    }
}