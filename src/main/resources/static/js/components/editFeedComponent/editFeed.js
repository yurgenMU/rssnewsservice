Vue.component("EditFeedComponent", {
    template: "#edit-feed-template",

    props: {
        sourceFeed: Object
    },

    data: function () {
        return {
            name: this.sourceFeed.name,
            link: this.sourceFeed.link
        }
    },

    methods: {

        saveChanges: function () {
            let accessToken = Window.SERVICE_UTILS.getJwtObject();
            let config = {
                headers: {
                    Authorization: 'Bearer ' + accessToken
                },
                withCredentials: true
            };
            let that = this;
            let name = this.name;
            let link = this.link;
            let newFeed = {
                id: this.sourceFeed.id,
                name: name,
                link: link
            };
            axios.post('/news/editFeed', newFeed, config)
                .then(response => {
                    if (response.status === 200) {
                        that.$emit('update:sourceFeed', newFeed);
                        that.closeModal();
                    }
                })
                .catch(error => {
                    that.$store.dispatch('processError');
                    that.$store.dispatch("hideSpinner");
                });
        },

        closeModal: function () {
            this.$refs.modalDialog.hideModalDialog();
        },
    }
});