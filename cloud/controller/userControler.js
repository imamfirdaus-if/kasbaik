const express = require('express')
const db = require('../db/db')
const Helper = require('./helper');
const crypto = require('crypto')

const User = {

    async index (req, res, next){
        res.send('message: berhasil masuk ke user controller' )
    },

    async signup_post(req, res, next) {
        try {
            temp = req.session;
            temp.username = req.body.username;
            temp.password = req.body.password;
            temp.email = req.body.email;
            temp.phone = req.body.phone;
        
            if (!temp.password || !temp.email ){
              return res.status(400).send({'message': 'email and password must be provided'});
            }
        
            if (!Helper.isValidEmail(temp.email)) {
              return res.status(400).send({ 'message': 'Please enter a valid email address' });
            }
        
            const checkUserQuery = `SELECT * FROM users WHERE email = '${temp.email}';`
            const result = await db.query(checkUserQuery)
            if (Array.isArray(result.rows) && result.rows.length) {
              return res.status(400).send({ status: "email is already used" });
            } 
            
            try { 
            const resultHash = await Helper.hashPassword(temp.password);
            const signupQuery = `INSERT INTO users(id, username, email, no_phone, password) VALUES($1, '${temp.username}','${temp.email}','${temp.phone}','${resultHash}');`
              
            db.query(signupQuery, [crypto.randomUUID()], (err, result) => {
              res.status(200).send({ status: "successfully signup"})
            }) 
            console.log('Signup Success');
            } catch (err) {
                console.error(err.message);
            }
               
          } catch (err) {
            console.error(err);
          }
    },

    async login_post (req, res, next) {
        temp = req.body;
        temp.email = req.body.email;
        temp.password = req.body.password;

        if (!temp.email|| !temp.password) {
        return res.status(400).send({'message': 'email and password is provided'});
        }

        if (!Helper.isValidEmail(req.body.email)) {
        return res.status(400).send({ 'message': 'Please enter a valid email address' });
        }

        

        
        const query = `SELECT * FROM users WHERE email = '${temp.email}';`;
        try {
        const { rows } = await db.query(query);
        if (!rows[0]) {
            return res.status(400).send({'message': 'The credentials you provided is incorrect'});
        }
        if(!Helper.comparePassword(rows[0].password, temp.password)) {
            return res.status(400).send({ 'message': 'The credentials you provided is incorrect' });
        }
        const token = Helper.generateToken(rows[0].id, rows[0].email);
        res.cookie('jwt', token);
        return res.status(200).send({ token });
        } catch(error) {
        return res.status(400).send(error)
        }

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