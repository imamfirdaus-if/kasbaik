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
        usiakat: {
            type: Sequelize.INTEGER,
        },
        econkat : {
            type: Sequelize.INTEGER,    
        },
        profesikat: {
            type: Sequelize.INTEGER,    
        },
        pinjamankat : {
            type: Sequelize.INTEGER,    
        },
        telatkat : {
            type: Sequelize.INTEGER,    
        },
        donasikat : {
            type: Sequelize.INTEGER,    
        },
        updatedAt: {
          type: Sequelize.DATEONLY
        },
        createdAt : {
          type: Sequelize.DATEONLY
        },
      }, {
        freezeTableName: true,
      });
      Credit.removeAttribute('id');
      return Credit;
}