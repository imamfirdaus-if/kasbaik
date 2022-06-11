module.exports = (sequelize, Sequelize) => {
    const Mitra = sequelize.define("mitras", {
        id_user: {
            type: Sequelize.UUID
        },
        id_mitra: {
            type: Sequelize.UUID
        },
        id_borrower: {
            type: Sequelize.UUID
        },
        nama_lengkap: {
            type: Sequelize.STRING
        },
        usia: {
            type: Sequelize.INTEGER
        },
        phone: {
            type: Sequelize.STRING
        },
        gender : {
            type: Sequelize.ENUM("laki-laki", "perempuan"),
        },
        profesi: {
            type: Sequelize.STRING
        },
        credit_score: {
            type: Sequelize.INTEGER,   
        },
        credit_approval: {
            type: Sequelize.INTEGER,   
        },
        loan_amount: {
            type : Sequelize.INTEGER,
        },
        reason_borrower: {
            type: Sequelize.STRING
        },
        dependents_amount: {
            type: Sequelize.INTEGER
        },
        monthly_income: {
            type: Sequelize.INTEGER
        },
        status: {
            type: Sequelize.ENUM("pending", "accepted", "rejected", "payment", "done"),
        },
        tenor :{
            type: Sequelize.INTEGER,
            defaultValue: 60
        },
        pinjaman_ke : {
            type: Sequelize.INTEGER,
            defaultValue: 0
        },
        donasi: {
            type: Sequelize.INTEGER
        }
    });
    Mitra.removeAttribute('id');
    return Mitra;
  };