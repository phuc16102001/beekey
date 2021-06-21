exports.server = {
    port: 8080,
    noTokenUrl: ['/account/login','/account/signup'],
    expTime: 60*60*24,
    secret: 'BTree'
}

exports.database = {
    host: 'localhost',
    user: 'root',
    password: 'Phuc16102001',
    database: 'BEEKEY'
}

exports.constant = {
    ADMIN_ID: 0,
    USER_ID: 1,
    MALE: 0,
    FEMALE: 1,
    DEFAULT_COIN: 0
}