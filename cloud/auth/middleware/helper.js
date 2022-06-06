
const bcrypt = require('bcrypt');
const jwt = require('jsonwebtoken');
const db = require('../model/model')
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

  creditMaker(usia, gender, pinjaman, tenor, pemasukan, tanggungan, pinjaman_ke, profesi, donasi) {
    // check usia 
    usia > 64 ? usiaKat =1 : usiaKat=2;

    usiastr = (usia-20)/80;
    
    if (gender === 'laki-laki'){
      genderKat = 1
    } else{
      genderKat = 0
    }

    pinjamanstr = (pinjaman-500000)/2500000;

    tenorstr = (tenor-3)/17;

    pemasukanstr = (pemasukan-1200000)/3800000

    tanggunganstr = (tanggungan/5);

    // check econCombineKat
    econCombine = pemasukan - ((pinjaman/(tenor/4)) + (150000 * (tanggungan +2)))
    if (econCombine >= 2000000) {
      econCombineKat = 5
    }else if (econCombine >= 1500000 && econCombine < 1999999) {
      econCombineKat = 4
    }else if (econCombine >=1000000 && econCombine < 14999999){
      econCombineKat = 3
    }else if (econCombine >= 500000 && econCombine < 999999){
      econCombineKat = 2
    }else if (econCombine >=10000 && econCombine < 499999){
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

    if (profesi === 'buruh'){
      profesistr = 0
    } else if(profesi === 'pengajar') {
      profesistr = 1
    } else if(profesi === 'pedagang') {
      profesistr = 2
    } else if(profesi === 'pekerja lepas') {
      profesistr = 3
    } else {
      profesistr = 4
    }
    profesistrindex = (profesistr/4)

    //check profesiKat
    profesi === 'buruh' ||'pengajar' || 'pns' || 'tni/polri', 'wiraswasta' ? profesiKat = 2 : profesiKat =1; 

    donasistr = (donasi/8)

    //check donasi
    if (donasi === 0){
      donasiKat= 0
    } else if (donasi <= 3){
      donasiKat =1
    } else if (donasi > 3 && donasi <= 5){
      donasiKat =2
    } else {
      donasiKat = 3
    }
    
    return {usiaKat, usiastr, genderKat, pinjamanstr, tenorstr, pemasukanstr, tanggunganstr, econCombineKat, pinjamanKeKat, profesistrindex, profesiKat, donasistr, donasiKat}
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
  },

  getIdMitra (id){
    const result = db.profileMitra.findOne({where: {id_user: id}})
    const getIdMitra = Helper.toObject(result).id_mitra
    return getIdMitra;
  }
}



module.exports = Helper;