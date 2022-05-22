
const express = require('express')


const main_user = async (req, res, next) => {
    console.log(req.objek);
    try {
        return res.status(200).send(req.objek)
    } catch (error) {
        
    }
}

const main_mitra = async (req, res, next) => {
    try {
        return res.send(`Berhasil masuk ke home page! Role anda adalah mitra`)
    } catch (error) {
        
    }
}


module.exports = {
    main_user, main_mitra
}