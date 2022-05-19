module.exports = (sequelize, Sequelize) => {
    const Borrower = sequelize.define("borrowers", {
        id: {
            allowNull: false,
            autoIncrement: true,
            primaryKey: true,
            type: Sequelize.INTEGER
        },
        loan: {
            type: Sequelize.INTEGER
        },
        reason_borrower: {
            type: Sequelize.STRING
        },
        dependents_amount: {
            type: Sequelize.INTEGER
        },
        payment_id: {
            type: Sequelize.STRING
        },
        status: {
            type: Sequelize.ENUM("pending", "accepted", "rejected"),
            defaultValue: "pending"
        },
    });
  
    return Borrower;
  };
  