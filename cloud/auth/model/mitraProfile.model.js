module.exports = (sequelize, Sequelize) => {
    const Profile = sequelize.define("profile_mitras", {
      id_mitra: {
        type: Sequelize.UUID,
        defaultValue: Sequelize.UUIDV4,
        allowNull: false,
        primaryKey: true
      },
      id_user: {
        type: Sequelize.UUID,
      },
      partner_name: {
        type: Sequelize.STRING
      },
      location_mitra : {
        type: Sequelize.STRING
      },
      phone: {
        type: Sequelize.BIGINT
      },
      foto_profile: {
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
  