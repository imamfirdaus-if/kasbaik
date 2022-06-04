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
        type: Sequelize.BIGINT
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
        type: Sequelize.ENUM('buruh', 'pengajar', 'pedagang', 'pekerja lepas', 'wirausaha', 'pns', 'tni/polri', 'wiraswasta')
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
  