const chat = require('../controller/chat')

function assignRoutes(app) {
   app.post("/chat/send",chat.send);
   app.post("/chat/fetch",chat.fetch);
}

module.exports = {
    assignRoutes
}