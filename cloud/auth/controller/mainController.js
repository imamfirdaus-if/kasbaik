
const express = require('express')


const main_user = async (req, res, next) => {
    
    try {
        return res.status(200).send(req.objek)
    } catch (error) {
        return res.status(400).send(error)
    }
}

const main_mitra = async (req, res, next) => {
    try {
        return res.send(`Berhasil masuk ke home page! Role anda adalah mitra`)
    } catch (error) {
        return res.status(400).send(error)
    }
}


module.exports = {
    main_user, main_mitra
}