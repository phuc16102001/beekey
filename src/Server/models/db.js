const config = require('./../config/config')
const mysql = require('mysql')
const conn = mysql.createConnection(config.database);

conn.connect(err=>{
    if (err) throw err;
    console.log("Connect successfully to database");
});

module.exports = conn