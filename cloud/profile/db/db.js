const { Pool } = require('pg');

const pool = new Pool({
    user: "postgres",
    host:'localhost',
	password: "hudahuda",
	database: "database1",
    port: 5432,
});

pool.connect((err) =>{
    if (err) {
        console.error(err);
        return;
    }
    console.log('Database Connected ');
});

module.exports = pool;

module.exports = pool;