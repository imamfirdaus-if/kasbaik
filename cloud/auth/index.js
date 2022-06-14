const express = require('express')
const app =express()
require('dotenv').config()
const cors = require('cors')
const bodyParser = require('body-parser');
const db = require('./model/model')
const cookieParser = require('cookie-parser');
const userRouter = require('./router/router')
const User = db.users

var allowCrossDomain = function(req, res, next) {
  res.header('Access-Control-Allow-Origin', 'example.com');
  res.header('Access-Control-Allow-Methods', 'GET,PUT,POST,DELETE');
  res.header('Access-Control-Allow-Headers', 'Content-Type');

  next();
}
app.use(allowCrossDomain);
app.use(express.json());
app.use(bodyParser.json());
app.use(cookieParser())
app.use(bodyParser.urlencoded({ extended: true}));
app.use(cors())


app.use('/', userRouter)
db.sequelize.sync().then(
  console.log("Syncing is complete")
);

app.get('/', async (req, res) => {
  try {
    res.send(`Welcome to API Page for Kasbaik Backend for Users`);
  } catch (error) {
    console.log(error);;
  }
});


PORT = process.env.PORT || 8080
app.listen(PORT, () => {console.log(`Application is running on ${PORT}!! `)})
