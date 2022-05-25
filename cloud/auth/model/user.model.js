module.exports = (sequelize, Sequelize) => {
    const User = sequelize.define("users", {
      id_user: {
        type: Sequelize.UUID,
        defaultValue: Sequelize.UUIDV4,
        allowNull: false,
        primaryKey: true
      },
      username: {
        type: Sequelize.STRING
      },
      email: {
        type: Sequelize.STRING
      },
      password: {
        type: Sequelize.STRING
      },
      phone: {
        type: Sequelize.BIGINT,
      }, 
      role: {
        type: Sequelize.ENUM("user", "mitra", "admin"),
        defaultValue: ('user')
      },
      createdAt : {
        type : Sequelize.DATEONLY,
      }
    }, {
      updatedAt: false,
      freezeTableName: true,
    });
  
    return User;
  };
  