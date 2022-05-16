
const bcrypt = require('bcrypt');
const jwt = require('jsonwebtoken');

SECRET = process.env.SECRET

const Helper = { 
  hashPassword(password) {
    return bcrypt.hashSync(password, bcrypt.genSaltSync(10))
  },
  
  comparePassword(hashPassword, password) {
    return bcrypt.compareSync(password, hashPassword);
  },
 
  isValidEmail(email) {
    return /^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/.test(email);
  },
  
  generateToken(id, email) {
    const token = jwt.sign(
    {
      userId: id,
      email: email
    },
      SECRET, { expiresIn: '10m' }
    )
    console.log(token);
    return token;
  }
}

module.exports = Helper;