const express = require('express')
const db = require('../model/model')
const Helper = require('./helper');
const crypto = require('crypto')
const dbUser = db.users;
// const Op = db.Sequelize.Op;

const User = {

    async index (req, res, next){
        res.send('message: berhasil masuk ke user controller' )
    },

    async signup_post(req, res, next) {
        try {
          const resultHash = await Helper.hashPassword(req.body.password);
          const user = {
              username : req.body.username,
              password : resultHash,
              email : req.body.email,
              phone : req.body.phone,
          };
          
        
            if (!user.password || !user.email){
              return res.status(400).send({'message': 'email and password must be provided'});
            }
        
            if (!Helper.isValidEmail(user.email)) {
              return res.status(400).send({ 'message': 'Please enter a valid email address' });
            }
        
            await dbUser.findOne({where: {email: user.email}})
            .then(data => {
              if (data !== null) {
                return res.status(400).send({ status: "email is already used" });
              } else {
                dbUser.create(user)
                .then(data => {
                  console.log('Signup Success');
                  res.status(201).send(data)
                })
              }
            })
            .catch( err => {
              console.log(err.message);
            })
               
          } catch (err) {
            console.error(err.message);
          }
    },

    async login_post (req, res, next) {
        
        const user = {
          email : req.body.email,
          password : req.body.password,
        }

        if (!user.email|| !user.password) {
        return res.status(400).send({'message': 'email and password is provided'});
        }

        if (!Helper.isValidEmail(user.email)) {
        return res.status(400).send({ 'message': 'Please enter a valid email address' });
        }
        
        await dbUser.findOne({where: {email: user.email}})
            .then(data => {
              if (data === null) {
                return res.status(400).send({ status: "your email is not registered" });
              } else {
                try {
                  if(!Helper.comparePassword(data.dataValues.password, user.password)) {
                  return res.status(400).send({ 'message': 'The credentials you provided is incorrect' })
                  }
                  console.log('berhasil login');
                   const token = Helper.generateToken(data.dataValues.id, user.email);
                  res.cookie('jwt', token);
                  return res.status(200).send({ token });
                } catch (err) {
                  console.log(err.message);
                }
              }
            })
            .catch( err => {
              console.log(err.message);
              res.status(500).send(err.message)
            })
    },

    async delete_post (req, res, next) {

    },

    async update (req, res, next) {

    },

    async logout_post (req, res, next) {
        try {
            const token = null
            res.cookie('jwt', token)
            console.log('berhasil logout');
            console.log();
            return res.status(200).send('berhasil logout dari aplikasi')
            
        } catch (err) {
            console.log(err.message);
        }

    }





}
module.exports = User;