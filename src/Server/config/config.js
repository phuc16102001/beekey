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
    database: 'BEEKEY',
    multipleStatments: true,
    connectionLimit: 3
}

exports.constant = {
    GENDER: {
        MALE: 0,
        FEMALE: 1,
    },
    STATUS: {
        PENDING: 0,
        ACCEPTED: 1,
        DONE: 2
    },
    DEFAULT_COIN: 0
}
