
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

  creditMaker(usia, pinjaman, tenor, pemasukan, tanggungan, pinjaman_ke, profesi, donasi) {
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

    //check profesiKat
    profesi === 'buruh' ||'pengajar' || 'pns' || 'tni/polri', 'wiraswasta' ? profesiKat = 1 : profesiKat =2; 

    //check donasi
    if (donasi >= 100000){
      donasiKat= 3
    } else if (donasi >= 50000){
      donasiKat =2
    } else if (donasi >=1000){
      donasiKat =1
    } else {
      donasiKat = 0
    }
    
    return {usiaKat, econCombineKat, pinjamanKeKat, profesiKat, donasiKat}
  },

  convertTelat (telat) {
    let telatKat;
    if (telat <=0){
      telatKat = 0
    } else if (telat <= 7){
      telatKat = 1
    } else if (telat <= 30){
      telatKat = 2
    } else {
      telatKat = 3
    }
    return telatKat;
  }
}



module.exports = Helper;