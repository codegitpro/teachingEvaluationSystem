import {MessageBox,Message} from 'element-ui'

export default {

    confirm: function (fun) {
       MessageBox.confirm('此操作将确认提交题目, 是否继续?', '提示', {
           confirmButtonText: '确定',
           cancelButtonText: '取消',
           type: 'info'
       }).then(() => {
           fun();
       }).catch(() => {
           Message({
               type: 'info',
               message: '已取消提交'
           });
       })
    },


}
