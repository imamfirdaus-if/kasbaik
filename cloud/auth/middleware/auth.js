const jwt = require('jsonwebtoken');


SECRET = process.env.SECRET
const Auth = {
    verifyToken(req, res, next){
        const token = req.cookies.jwt;
        console.log(token);
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
                req.role = decodedToken.role
                
                return next()
              }
            });
        } else {
          res.status(403).send('Youre not authenticated, please login first')
            console.log('Youre not authenticated');
        }
    
  }, 

  verifyTokenMitra(req, res, next){
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
            req.role = decodedToken.role
            if (req.role === 'mitra'){
              next()
            } else {
              return res.status(500).send('anda tidak dapat masuk ke daerah mitra')
            }
            
          }
        });
    } else {
      res.status(403).send('Youre not authenticated, please login first')
        console.log('Youre not authenticated');
    }

}, 

verifyTokenUser(req, res, next){
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
          req.role = decodedToken.role
        
          if (req.role === 'user'){
            next()
          }else {
            return res.status(500).send('anda tidak dapat masuk ke daerah user')
          }
          
        }
      });
  } else {
    res.status(403).send('Youre not authenticated, please login first')
      console.log('Youre not authenticated');
  }

}
}

module.exports = Auth;