
const express = require('express')

const Main = {

    main_get (req, res, next){
        try {
            return res.send('Berhasil masuk ke home page')
        } catch (error) {
            
        }
    }


}

module.exports = Main.main_get