module.exports = (sequelize, Sequelize) => {
    const Borrower = sequelize.define("borrowers", {
        id_borrower: {
            type: Sequelize.UUID,
            defaultValue: Sequelize.UUIDV4,
            allowNull: false,
            primaryKey: true
        },
        id_user: {
            type: Sequelize.STRING
        },
        id_mitra: {
            type: Sequelize.STRING
        },
        loan_amount: {
            type: Sequelize.INTEGER
        },
        reason_borrower: {
            type: Sequelize.STRING
        },
        monthly_income: {
            type: Sequelize.INTEGER
        },
        dependents_amount: {
            type: Sequelize.INTEGER
        },
        payment_method: {
            type: Sequelize.ENUM("cicil", "cash"),
            defaultValue: "cicil"
        },
        status: {
            type: Sequelize.ENUM("pending", "accepted", "rejected", "payment", "done"),
            defaultValue: "pending"
        },
        tenor :{
            type: Sequelize.INTEGER,
            defaultValue: 60
        },
        pinjaman_ke : {
            type: Sequelize.INTEGER,
            defaultValue: 0
        },
        telat : {
            type: Sequelize.STRING
        }, 
        donasi: {
            type: Sequelize.INTEGER
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
  
    return Borrower;
  };
  