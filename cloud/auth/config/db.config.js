module.exports = {
    
    HOST:"localhost",
    USER: "postgres",
    PASSWORD: "fadil123",
    DB: "database1",
    dialect: "postgresql",
    pool: {
      max: 5,
      min: 0,
      acquire: 30000,
      idle: 10000
    }
};