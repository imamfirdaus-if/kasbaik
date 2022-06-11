module.exports = (sequelize, Sequelize) => {
    const Payment = sequelize.define("payments", {
        id_payment: {
            type: Sequelize.UUID,
            defaultValue: Sequelize.UUIDV4,
            allowNull: false,
            primaryKey: true
        },
        id_mitra: {
            type: Sequelize.UUID
        },
        id_borrower: {
            type: Sequelize.UUID
        },
        payment_method: {
            type: Sequelize.STRING
        },
        amount_payment: {
            type: Sequelize.INTEGER
        },
        payment_ke : {
            type: Sequelize.INTEGER
        }
    });
    
    return Payment;
  };