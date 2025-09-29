import qs from 'qs'
import service from './axios'

export default {
    get: function (url, params) {
        return service.get(url, {params})
    },
    delete: function (url, params) {
        return service.delete(url, {params})
    },
    postJSON: function (url, params) {
        return service.post(url, params, {
            headers: {'Content-Type': 'application/json;charset=UTF-8'}
        })
    },
    postForm: function (url, params) {
        return service.post(url, qs.stringify(params), {
            headers: {'Content-Type': 'application/x-www-form-urlencoded;charset=utf-8'}
        })
    },
    putJSON: function (url, params) {
        return service.put(url, params, {
            headers: {'Content-Type': 'application/json;charset=UTF-8'}
        })
    },
    putForm: function (url, params) {
        return service.put(url, qs.stringify(params), {
            headers: {'Content-Type': 'application/x-www-form-urlencoded;charset=utf-8'}
        })
    },

    uploadFile: function (url, file, params) {
        const formData = new FormData()
        // 上传单个文件
        formData.append('file', file)
        // 添加其他参数到formData对象中
        for (let key in params) {
            formData.append(key, params[key]);
        }
        return service.post(url, formData, {
            headers: {'Content-type': 'multipart/form-data'}
        })
    },


    uploadMultiFile: function (url, files, params) {
        const formData = new FormData()
        // 上传多个文件
        Array.from(files).forEach(file => {
            formData.append('files', file)
        })
        // 添加其他参数到formData对象中
        for (let key in params) {
            formData.append(key, params[key]);
        }
        return service.post(url, formData, {
            headers: {'Content-type': 'multipart/form-data'}
        })
    },

    downloadFile: function (url, params) {
        //responseType: 'blob' 设置响应类型为二进制流
        service.get(url, {responseType: 'blob', params: params}).then(res => {
            const url = window.URL.createObjectURL(new Blob([res.data]))
            const link = document.createElement('a')
            link.style.display = 'none'
            link.href = url
            link.setAttribute('download', decodeURIComponent(res.headers['filename']))
            document.body.appendChild(link)
            link.click()
            document.body.removeChild(link)
            window.URL.revokeObjectURL(url)
        }).catch((err) => {
            console.log(err)
        })
    }
}

