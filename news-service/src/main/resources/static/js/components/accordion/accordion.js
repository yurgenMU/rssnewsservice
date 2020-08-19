Vue.component("Accordion", {
    template: "#accordion-template",

    props: {
        openTrigger: Object,
        openCondition: Boolean,
        initialState: {
            type: Boolean,
            default: true
        },
        headerClass: {type: String, default: "accordion_header"},
    },

    data: function () {
        return {
            open: this.initialState,
            accordionBody: {},
            maxHeight: ""
        }
    },

    watch: {
        openTrigger: function () {
            if (!this.open && this.openCondition) {
                this.toggle();
            }
        }
    },

    mounted: function () {
        this.accordionBody = this.$el.querySelector(".accordion_body");
    },

    methods: {
        toggle: function (flag) {
            if (flag === undefined) {
                flag = !this.open;
            }
            if (flag === this.open) {
                return;
            }
            this.open = flag;

            if (this.maxHeight === "") {
                this.maxHeight = this.accordionBody.clientHeight + 'px';
            }

            if (this.open) {
                (function (that) {
                    setTimeout(function () {
                        that.maxHeight = "";
                    }, 405);
                }(this));
            }
            this.$emit('accordion-toggled', this.open);
        }
    },

    computed: {
        style: function () {
            return {
                maxHeight: this.maxHeight
            }
        }
    }
})