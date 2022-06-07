module.exports = (sequelize, Sequelize) => {
    const Mitra = sequelize.define("mitras", {
        id_user: {
            type: Sequelize.STRING
        },
        id_mitra: {
            type: Sequelize.STRING
        },
        id_borrower: {
            type: Sequelize.STRING
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