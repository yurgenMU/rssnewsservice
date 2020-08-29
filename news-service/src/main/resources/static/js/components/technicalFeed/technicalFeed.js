Vue.component("TechnicalFeedComponent", {
    template: "#technical-feed-template",

    props: {
        feed: Object,
        removeFunction: Function
    },

    methods: {

        deleteFeed: function () {
            this.removeFunction(this.feed);
        }
    }
});