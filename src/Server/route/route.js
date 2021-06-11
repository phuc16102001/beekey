
const accountRoute = require('./account')

function assignRoutes(app){
    app.post('/account/login',accountRoute.login);
    app.post('/account/signup',accountRoute.signup);
    app.get('/account/getInformation',accountRoute.getInformation);
}

module.exports = {
    assignRoutes
}