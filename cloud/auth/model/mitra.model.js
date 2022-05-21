module.exports = (sequelize, Sequelize) => {
    const Mitra = sequelize.define("mitras", {
        id_user: {
            type: Sequelize.STRING
        },
        id_mitra: {
            type: Sequelize.STRING
        },
        nama_lengkap: {
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
    Mitra.removeAttribute('id');
    return Mitra;
  };