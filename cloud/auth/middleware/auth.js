const jwt = require('jsonwebtoken');


SECRET = process.env.SECRET
const Auth = {
    verifyToken(req, res, next){
        const token = req.cookies.jwt;
        
        if (token) {
            jwt.verify(token, process.env.SECRET, (err, decodedToken) => {
              if (err) {
                console.log(err.message);
                res.status(403).send(`Failed to enter this session, ${err.message} and please try relogin`);
              } else {
                console.log('Autentikasi berhasil');
                console.log(decodedToken);
                req.id = decodedToken.userId
                req.email = decodedToken.email 
                next();
              }
            });
        } else {
          res.status(403).send('Youre not authenticated, please login first')
            console.log('Youre not authenticated');
        }
    
  }
}

module.exports = Auth;