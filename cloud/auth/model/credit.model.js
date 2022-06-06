module.exports = (sequelize, Sequelize) => {
    const Credit = sequelize.define("credits", {
        id_borrower: {
          type: Sequelize.UUID,
        },
        id_user: {
          type: Sequelize.UUID
        },
        id_mitra: {
            type: Sequelize.UUID
        },
        usiastr: {
            type: Sequelize.FLOAT,
        },
        usiakat: {
            type: Sequelize.INTEGER,
        },
        genderkat: {
            type: Sequelize.INTEGER,
        },
        econkat : {
            type: Sequelize.INTEGER,    
        },
        profesistr : {
            type: Sequelize.FLOAT,    
        },
        profesikat: {
            type: Sequelize.INTEGER,    
        },
        pinjamanstr : {
            type: Sequelize.FLOAT,    
        },
        pinjamankekat : {
            type: Sequelize.INTEGER,    
        },
        tenorstr : {
            type: Sequelize.FLOAT,    
        },
        pemasukanstr : {
            type: Sequelize.FLOAT,    
        },
        tanggunganstr : {
            type: Sequelize.FLOAT,    
        },
        telatkat : {
            type: Sequelize.INTEGER,    
        },
        donasistr : {
            type: Sequelize.FLOAT,    
        },
        donasikat : {
            type: Sequelize.INTEGER,    
        }
      }, {
        freezeTableName: true,
      });
      Credit.removeAttribute('id');
      return Credit;
}