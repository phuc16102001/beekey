const config = require('./../config/config')
const mysql = require('mysql')
const conn = mysql.createPool(config.database);

console.log("Created pool for database")

module.exports = conn
