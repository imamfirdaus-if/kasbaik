module.exports = (sequelize, Sequelize) => {
    const Mitra = sequelize.define("mitras", {
        id: {
            allowNull: false,
            autoIncrement: true,
            primaryKey: true,
            type: Sequelize.INTEGER
        },
        name: {
            type: Sequelize.STRING
        },
        reason_borrower: {
            type: Sequelize.STRING
        },
        dependents_amount: {
            type: Sequelize.INTEGER
        },
        status: {
            type: Sequelize.STRING
        },
    });
  
    return Mitra;
  };