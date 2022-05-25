module.exports = (sequelize, Sequelize) => {
    const Payment = sequelize.define("payments", {
        id_payment: {
            type: Sequelize.UUID,
            defaultValue: Sequelize.UUIDV4,
            allowNull: false,
            primaryKey: true
        },
        id_mitra: {
            type: Sequelize.STRING
        },
        id_borrower: {
            type: Sequelize.STRING
        },
        payment_method: {
            type: Sequelize.STRING
        },
        amount_payment: {
            type: Sequelize.INTEGER
        },
        createdAt : {
          type: Sequelize.DATEONLY
        },
    });
    
    return Payment;
  };