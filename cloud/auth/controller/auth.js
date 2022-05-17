const jwt = require('jsonwebtoken');
const db = require('../db/db')

SECRET = process.env.SECRET
const Auth = {
    verifyToken(req, res, next){
        const token = req.cookies.jwt;
        
        if (token) {
            jwt.verify(token, process.env.SECRET, (err, decodedToken) => {
              if (err) {
                console.log(err.message);
                res.status(403).send(err.message);
              } else {
                console.log('berhasil ');
                console.log(decodedToken);
                req.id = decodedToken.userId
                req.email = decodedToken.email 
                next();
              }
            });
        } else {
            console.log('cannot loggin');
            res.redirect('/login');
        }
    
  }
}

module.exports = Auth;