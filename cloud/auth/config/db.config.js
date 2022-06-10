module.exports = {
    
    HOST:"localhost",
    USER: "postgres",
    PASSWORD: "273023",
    DB: "database3",

    dialect: "postgresql",
    pool: {
      max: 5,
      min: 0,
      acquire: 30000,
      idle: 10000
    }
};