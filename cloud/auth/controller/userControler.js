const express = require('express')
const db = require('../model/model')
const Helper = require('../middleware/helper');
const crypto = require('crypto')
const dbUser = db.users;
const dbProfile = db.profileUser;
const dbProfileMitra = db.profileMitra
// const Op = db.Sequelize.Op;


const index = async(req, res, next) => {
  res.send('message: berhasil masuk ke user controller' )
}
    
const signup_post = async(req, res, next) => {
  try {
    const resultHash = await Helper.hashPassword(req.body.password);
    const user = {
        username : req.body.username,
        password : resultHash,
        email : req.body.email,
        phone : req.body.phone,
        role : req.body.role,
    };
    
  
      if (!user.password || !user.email){
        return res.status(400).send({ message : 'email and password must be provided'});
      }
  
      if (!Helper.isValidEmail(user.email)) {
        return res.status(400).send({ message : 'Please enter a valid email address' });
      }
  
      await dbUser.findOne({where: {email: user.email}})
      .then(data => {
        if (data !== null) {
          return res.status(400).send({
            error: true,
            message: "email is already used"
          });
        } else {
          dbUser.create(user)
          .then(data => {
            console.log('Signup Success');
            console.log(data.toJSON());
            if (data.role === 'user') {
              dbProfile.create({ id_user: data.id_user, nama_lengkap: data.username, phone: data.phone })
              .then(data1 => {
              console.log('berhasil created dbProfile');
              res.status(201).send({user : data, profile: data1})
            })
            } else if (data.role === 'mitra') {
              dbProfileMitra.create({id_user : data.id_user, partner_name : data.username, phone : data.phone})
              .then(data1 => {
              console.log('berhaisil created dbProfileMitra');
              res.status(201).send({user : data, profile: data1})
              })
            }
            
          }) 
        }
      })
      
  } catch (err) {
    console.error(err.message);
    return res.status(500).send(err)
  }
}
    
const login_post= async(req, res, next) => {
  try {

    const user = {
      email : req.body.email,
      password : req.body.password,
    }
  
    if (!user.email|| !user.password) {
      return res.status(400).send({ message : 'email and password is provided'});
    }
  
    if (!Helper.isValidEmail(user.email)) {
      return res.status(400).send({ message : 'Please enter a valid email address' });
    }
    
    await dbUser.findOne({where: {email: user.email}})
      .then(data => {
        if (data === null) {
          return res.status(400).send({
            error: true,
            message: "your email is not registered"
          });
        } else {
        
        if(!Helper.comparePassword(data.dataValues.password, user.password)) {
          return res.status(400).send({ message : 'The credentials you provided is incorrect' })
        }
        
        console.log('berhasil login');
        const token = Helper.generateToken(data.dataValues.id_user, user.email, data.role );
        res.cookie('jwt', token);
        console.log({token});
     
        return res.status(200).json({
          user : data,
          token
        });

        }
      })
        
  } catch (err) {
    log.error(err.message);
    return res.status(500).send(err.message);
  }
  
}
    
const delete_post = async(req, res, next) => {
  console.log(req.id);
  id_user = req.id
  try {
    dbUser.destroy({where : {id_user: id_user}})
    .then (data => {
      if (data !== null) {
        const token = null
        res.cookie('jwt', token)
        console.log('Berhasil menghapus akun, silakan signup kembali');
        return res.status(200).send(`data ${req.email} berhasil dihapus`)
      }
    })
  } catch (err) {
    console.log(err.message);
    return res.status(500).send(err.message)
  }
}

//digunakan untuk mengupdate password dari users
const update_post = async(req, res, next) => {

}

const logout_post = async(req, res, next) => {
  try {
    const token = null
    res.cookie('jwt', token)
    console.log('berhasil logout');
    return res.status(200).send('berhasil logout dari aplikasi')
            
  } catch (err) {
    console.log(err.message);
    return res.status(500).send(err)
  }
}

module.exports = {
  index, 
  signup_post,
  login_post,
  delete_post,
  update_post,
  logout_post
};