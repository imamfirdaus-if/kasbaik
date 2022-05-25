const dbConfig = require('../config/db.config')

const Sequelize = require("sequelize");
const sequelize = new Sequelize(dbConfig.DB, dbConfig.USER, dbConfig.PASSWORD, {
  host: dbConfig.HOST,
  dialect: dbConfig.dialect,
  operatorsAliases: 0,
  logging: 0,
  pool: {
    max: dbConfig.pool.max,
    min: dbConfig.pool.min,
    acquire: dbConfig.pool.acquire,
    idle: dbConfig.pool.idle
  }
})

sequelize
    .authenticate()
    .then(function () {
        console.log('Koneksi ke db telah  berhasil.');
    })
    .catch(function (err) {
        console.log('Tidak dapat melakukan koneksi ke db: ');
    });

const db = {};

db.Sequelize = Sequelize;
db.sequelize = sequelize;
db.users = require("./user.model")(sequelize, Sequelize);
db.profileUser = require("./profile.model")(sequelize, Sequelize);
db.borrower = require("./borrower.model")(sequelize, Sequelize);
db.mitra = require("./mitra.model")(sequelize, Sequelize);
db.profileMitra = require("./mitraProfile.model")(sequelize, Sequelize);
db.payment = require("./payment.model")(sequelize, Sequelize);

module.exports = db;
