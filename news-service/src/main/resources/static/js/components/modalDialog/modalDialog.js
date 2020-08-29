Vue.component("ModalDialog", {
    template: "#modal_dialog-template",

    data: function () {
        return {
            shown: false,
            widthParameter: this.width,
            topValue: {}
        }
    },

    props: {
        nested: {type: Boolean, default: false},
        onNestedToggle: {type: Function, default: function () {}},
        wrapperStyle: {type: Object, default: function () {return {}}}
    },

    methods: {
        showModalDialog: function () {

            this.shown = true;
            this.onNestedToggle(true);
            this.$emit('on-toggle-modal', this.shown);

            var that = this;
            this.$nextTick(function () {
                that.$children.forEach(function (child) {
                    if (typeof child.rendered === "function") child.rendered();
                })
            });
        },

        hideModalDialog: function () {
            this.shown = false;
            if (!this.nested) {
                this.onNestedToggle(false);
            }
            this.$emit('on-toggle-modal', this.shown);
        },

        setWidth: function (newWidth) {
            this.widthParameter = newWidth;
        }
    },

    computed: {
        hasFooterSlot: function () {
            return !!this.$slots['dialog-footer'];
        },

        hasSubtitleSlot: function () {
            return !!this.$slots['dialog-subtitle'];
        },

        hasButtonSlot: function () {
            return !!this.$slots['dialog-button'];
        }
    }
});