Vue.component("AddFeedComponent", {
    template: "#add-feed-template",

    props: {
        addToFeeds: Function,
    },

    data: function () {
        return {
            name: "",
            link: ""
        }
    },

    methods: {

        saveChanges: function () {
            let newFeed = {
                name: this.name,
                link: this.link
            };
            this.addToFeeds(newFeed);
            this.closeModal();
        },

        closeModal: function () {
            this.$refs.modalDialog.hideModalDialog();
        },
    }
});