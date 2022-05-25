module.exports = (sequelize, Sequelize) => {
    const Profile = sequelize.define("profiles", {
      id_profile: {
        type: Sequelize.UUID,
        defaultValue: Sequelize.UUIDV4,
        allowNull: false,
        primaryKey: true
      },
      id_user: {
        type: Sequelize.UUID
      },
      nama_lengkap: {
        type: Sequelize.STRING
      },
      credit_score: {
        type: Sequelize.INTEGER,
        defaultValue: 0
      },
      phone: {
        type: Sequelize.STRING
      },
      usia: {
        type: Sequelize.INTEGER
      },
      gender : {
        type: Sequelize.ENUM("laki-laki", "perempuan"),
      },
      alamat_tinggal: {
        type: Sequelize.STRING
      },
      alamat_ktp: {
        type: Sequelize.STRING
      },
      profesi: {
        type: Sequelize.STRING
      },
      foto_diri: {
        type: Sequelize.STRING
      },
      foto_ktp: {
        type: Sequelize.STRING
      },
      foto_selfie: {
        type: Sequelize.STRING
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
  
    return Profile;
  };
  