module.exports = (sequelize, Sequelize) => {
    const Profile = sequelize.define("profile_mitras", {
      id_mitra: {
        type: Sequelize.UUID,
        defaultValue: Sequelize.UUIDV4,
        allowNull: false,
        primaryKey: true
      },
      id_user: {
        type: Sequelize.STRING
      },
      partner_name: {
        type: Sequelize.STRING
      },
      location_mitra : {
        type: Sequelize.STRING
      },
      phone: {
        type: Sequelize.STRING
      },
      
    }, {
        updatedAt: false,
        createdAt : false,
        freezeTableName: true,
      });
  
    return Profile;
  };
  