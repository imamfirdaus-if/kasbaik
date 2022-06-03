module.exports = (sequelize, Sequelize) => {
    const Message = require= sequelize.define("messages", {
        id_message: {
            type: Sequelize.UUID,
            defaultValue: Sequelize.UUIDV4,
            allowNull: false,
            primaryKey: true
        },
        id_user: {
            type: Sequelize.STRING
        },
        id_borrower: {
            type: Sequelize.STRING,
        },
        message: {
            type: Sequelize.STRING
        },
        has_read: {
            type: Sequelize.BOOLEAN,
            defaultValue: false,
        }
        
    }, {
        freezeTableName: true,
      });
      Message.removeAttribute('id');
  
    return Message;
  };
  