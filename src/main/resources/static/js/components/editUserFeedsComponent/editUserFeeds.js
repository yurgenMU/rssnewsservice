Vue.component("EditUserFeedsComponent", {
    template: "#edit-user-feeds-template",

    data: function () {
        return {
            chosenFeeds: [],
            allFeeds: [],
            selectedFeeds: [],
            unselectedFeeds: [],
        }
    },

    created: function () {
       this.$root.loadFeeds(this, this.afterFeedsLoad);
    },

    computed: {

        wrappedFeeds: function () {
            return this.allFeeds.map(function (feed) {
                return {selected: false, origin: feed};
            });
        },
    },

    methods: {

        afterFeedsLoad: function() {
            var that = this;
            // that.chosenFeeds = chosenFeeds;
            // that.allFeeds = allFeeds;
            var groupedFeeds = this.groupingBy(this.wrappedFeeds, feed =>
                that.chosenFeeds.some(chosenFeed =>
                    chosenFeed.id === feed.origin.id
                ) ? 'chosen' : 'available'
            );

            this.unselectedFeeds = groupedFeeds.available || [];
            this.selectedFeeds = groupedFeeds.chosen || [];
        },


        addToSelected: function () {
            var splitItems = this.groupingBy(this.unselectedFeeds, el =>
                el.selected ? 'selected' : 'not selected');

            var selectedItems = splitItems['selected'] || [];
            this.unselectedFeeds = splitItems['not selected'] || [];
            this.moveElements(selectedItems, this.selectedFeeds);
        },

        removeFromSelected: function () {
            var splitItems = this.groupingBy(this.selectedFeeds, el =>
                el.selected ? 'selected' : 'not selected');

            var selectedItems = splitItems['selected'] || [];
            this.selectedFeeds = splitItems['not selected'] || [];
            this.moveElements(selectedItems, this.unselectedFeeds);
        },

        moveElements: function (source, target) {
            for (var i = 0; i < source.length; i++) {
                var element = source[i];
                if (element.selected) {
                    source.splice(i, 1);
                    element.selected = false;
                    target.push(element);
                    i--;
                }
            }
            this.sort(target);
        },


        unselectAll: function () {
            this.unselectedFeeds.map(service =>
                service.selected = false
            );
            this.selectedFeeds.map(service =>
                service.selected = false
            )
        },

        sort: function (target) {
            target.sort((first, second) =>
                first.name - second.name
            );
        },

        groupingBy: function (array, keySupplier) {
            var result = {};
            for (var i = 0; i < array.length; i++) {
                var el = array[i];
                var key = keySupplier(el);
                if (!result[key]) {
                    result[key] = [el];
                    continue;
                }
                result[key].push(el);
            }
            return result;
        },

        saveChanges: function () {
            let accessToken = Window.SERVICE_UTILS.getJwtObject();
            let config = {
                headers: {
                    Authorization: 'Bearer ' + accessToken
                },
                withCredentials: true
            };
            let that = this;
            let feeds = that.selectedFeeds.map(feed => feed.origin);
            axios.post('/news/editFeed', feeds, config)
                .then(response => {
                    if (response.status === 200) {
                        that.chosenFeeds = that.selectedFeeds.length === 0 ? that.allFeeds : that.selectedFeeds;
                        that.$store.dispatch('setChosenFeeds', that.chosenFeeds);
                    }
                })
                .catch(error => {
                    that.$store.dispatch('processError');
                    that.$store.dispatch("hideSpinner");
                });

        }
    }
});