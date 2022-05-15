const { Pool } = require('pg');

var config = {
    user: "postgres",
    // host:'34.123.115.142',
    host: 'localhost',
    password: "hudahuda",
    database: "database1",
    port: 5432,
    // ssl:{
    //     rejectUnauthorized: false,
    // }
}

const pool =  new Pool(config)
pool.connect((err) =>{
    if (err) {
        console.error(err);
        return;
    }
    console.log('Database Connected ');
});

module.exports = pool;