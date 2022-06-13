module.exports = {
    
    HOST:"localhost",
    USER: "postgres",
    PASSWORD: "Postgretoomuch34",
    DB: "database2",

    dialect: "postgresql",
    pool: {
      max: 5,
      min: 0,
      acquire: 30000,
      idle: 10000
    }
};