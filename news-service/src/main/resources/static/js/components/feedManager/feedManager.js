Vue.component("FeedManagerComponent", {
    template: "#feed-manager-template",

    data: function () {
        return {
            open: false,
            allFeeds: []
        }
    },

    created: function () {
        this.loadFeedsTechnicalData();
    },


    methods: {

        deleteFeed: function (feed) {

        },

        loadFeedsTechnicalData: function () {
            let accessToken = Window.SERVICE_UTILS.getJwtObject();
            let config = {
                headers: {
                    Authorization: 'Bearer ' + accessToken
                },
                withCredentials: true
            };
            let that = this;
            if (accessToken) {
                axios.get('/api/v1/news/allFeeds', config)
                    .then(response => {
                        if (response.status === 200) {
                            this.allFeeds = response.data;
                        }
                    })
                    .catch(error => {
                        that.$store.dispatch('processError');
                        that.$store.dispatch("hideSpinner");
                    });
            }


        },

        addToFeeds: function (newFeed) {
            let accessToken = Window.SERVICE_UTILS.getJwtObject();
            let config = {
                headers: {
                    Authorization: 'Bearer ' + accessToken
                },
                withCredentials: true
            };
            let that = this;
            axios.post('/api/v1/news/addFeed', newFeed, config)
                .then(response => {
                    if (response.status === 200) {
                        that.allFeeds.push(newFeed);
                        that.closeModal();
                    }
                })
                .catch(error => {
                    that.$store.dispatch('processError');
                    that.$store.dispatch("hideSpinner");
                });
        },

        removeFeed: function (feedToRemove) {
            let accessToken = Window.SERVICE_UTILS.getJwtObject();
            let config = {
                headers: {
                    Authorization: 'Bearer ' + accessToken
                },
                withCredentials: true
            };
            let that = this;
            let url = '/api/v1/news/removeFeed/' + feedToRemove.id;
            axios.delete(url, config)
                .then(response => {
                    if (response.status === 200) {
                        that.allFeeds = that.allFeeds.filter(feed => feed !== feedToRemove);
                        that.closeModal();
                    }
                })
                .catch(error => {
                    that.$store.dispatch('processError');
                    that.$store.dispatch("hideSpinner");
                });
        }
    }
})