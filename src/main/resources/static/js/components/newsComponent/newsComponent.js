Vue.component("NewsComponent", {
    template: "#news-template",

    props: {},


    data: function () {
        return {
            open: false,
            chosenFeeds: [],
            allFeeds: []
        }
    },

    created: function () {
        this.$root.loadFeeds(this);
    },


    methods: {
        toggle: function () {
            this.open = !this.open;
        },

        afterFeedsLoad: function (chosenFeeds) {
            this.chosenFeeds = chosenFeeds;
        }
    },


});