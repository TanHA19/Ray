const ajax = function (url, param, type = 'POST') {
    return new Promise(function (resolve, reject) {
        $.ajax({
            type: type,
            url: url,
            data: param,
            dataType: 'json',
            success(res) {
                resolve(res)

            },
            error(res) {

                reject("发生错误")
                // reject(res.statusText)
            }
        })
    })

}


/**
 * 去重
 * @param arr
 * @returns {[]}
 */
function unique(arr) {
    if (!Array.isArray(arr)) {
        console.log('type error!')
        return
    }
    let array = [];
    for (let i = 0; i < arr.length; i++) {
        if (array.indexOf(arr[i]) === -1) {
            array.push(arr[i])
        }
    }
    return array;
}

function logout() {

    window.location = "/rayLogout"
}