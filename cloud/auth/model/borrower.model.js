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
        payment_id: {
            type: Sequelize.STRING
        },
        id_mitra: {
            type: Sequelize.STRING
        },
        status: {
            type: Sequelize.ENUM("pending", "accepted", "rejected"),
            defaultValue: "pending"
        },
    });
  
    return Borrower;
  };
  