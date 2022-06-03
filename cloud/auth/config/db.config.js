module.exports = {
    // HOST: "34.101.231.106",
    HOST:"localhost",
    USER: "postgres",
    PASSWORD: "273023",
    DB: "database1",
    dialect: "postgresql",
    pool: {
      max: 5,
      min: 0,
      acquire: 30000,
      idle: 10000
    }
};