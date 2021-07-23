const logger = function(req,res,next) {
    const currentDateTime = new Date();
    const year = currentDateTime.getFullYear()
    const month = currentDateTime.getMonth()+1
    const date = currentDateTime.getDate()
    const hour = currentDateTime.getHours()
    const minute = currentDateTime.getMinutes()
    const second = currentDateTime.getSeconds()
    const now = `${year}-${month}-${date} ${hour}:${minute}:${second}`
    const method = req.method
    const url = req.url
    const logStr = `[${now}] ${method}:${url}`
    console.info(logStr)
    console.log("Request body")
    console.log(req.body)
    next()
}

module.exports = {
    logger
}