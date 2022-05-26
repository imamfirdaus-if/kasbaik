module.exports = (sequelize, Sequelize) => {
    const UserPay = sequelize.define("user_payments", {
        id_borrower: {
          type: Sequelize.UUID,
        },
        id_user: {
          type: Sequelize.UUID
        },
        id_mitra: {
            type: Sequelize.UUID
        },
        loan_amount: {
            type: Sequelize.INTEGER,
        },
        total_payment : {
            type: Sequelize.INTEGER,
            defaultValue: 0,
        },
        target_lunas: {
            type: Sequelize.DATEONLY
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
      UserPay.removeAttribute('id');
      return UserPay;
}