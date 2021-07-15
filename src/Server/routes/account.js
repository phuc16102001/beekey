const account = require('../controller/account')

function assignRoutes(app) {
    app.post('/account/login',account.login);
    app.post('/account/signup',account.signup);
    app.get('/account/getInformation',account.getInformation);
    app.post('/account/changePassword',account.changePassword);
    app.post('/account/changeInformation',account.changeInformation)
    app.post('/account/topUp',account.topUp)
}

module.exports = {
    assignRoutes
}