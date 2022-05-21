
const express = require('express')


const main_user = async (req, res, next) => {
    try {
        return res.status(200).send(`Berhasil masuk ke home page! Role anda adalah user`)
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