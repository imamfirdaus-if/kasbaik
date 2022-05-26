
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
  
  generateToken(id, email, role) {
    const token = jwt.sign(
    {
      userId: id,
      email: email,
      role: role
    },
      SECRET, { expiresIn: '1d' }
    )
    return token;
  },

  toObject(data) {
    return JSON.parse(JSON.stringify(data));
  },

  creditMaker(usia, pinjaman, tenor, pemasukan, tanggungan, pinjaman_ke) {
    // check usia 
    usia > 64 ? usiaKat =1 : usiaKat=2; 

    // check econCombineKat
    econCombine = pemasukan - ((pinjaman/(tenor/4)) + (150000 * (tanggungan +2)))
    if (econCombine >= 2000000) {
      econCombineKat = 5
    }else if (econCombine >= 1500000) {
      econCombineKat = 4
    }else if (econCombine >=1000000){
      econCombineKat = 3
    }else if (econCombine >= 500000){
      econCombineKat = 2
    }else if (econCombine >=10000){
      econCombineKat = 1
    }else {
      econCombineKat = 0
    }
    
    //check pinjamanKeKat
    if (pinjaman_ke=0){
      pinjamanKeKat =0
    }
    else if(pinjaman_ke = 1){
      pinjamanKeKat =1
    } else if (pinjaman_ke === 1 || pinjaman_ke === 2){
      pinjamanKeKat =2
    } else {
      pinjamanKeKat =3
    }

    //check 
    
    return {usiaKat, econCombineKat, pinjamanKeKat}
  }
}



module.exports = Helper;