import {Message} from 'element-ui'

export default {

    error: function (message) {
        Message.error({
            message: message,
            type: 'success'
        });
    },

    success: function (message) {
        Message.success({
            message: message,
            type: 'success'
        });
    },

    info: function (message) {
        Message.info({
            message: message,
            type: 'info'
        });
    },
    warning: function (message) {
        Message.warning({
            message: message,
            type: 'warning'
        });
    },

}
