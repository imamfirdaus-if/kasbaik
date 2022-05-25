const express = require('express')
const app =express()
require('dotenv').config()
const bodyParser = require('body-parser');
const db = require('./model/model')
const cookieParser = require('cookie-parser');
const userRouter = require('./router/router')
const User = db.users
app.use(express.json());
app.use(bodyParser.json());
app.use(cookieParser())
app.use(bodyParser.urlencoded({ extended: true}));


app.use('/', userRouter)
db.sequelize.sync().then(
  console.log("Syncing is complete")
);

app.get('/', async (req, res) => {
  try {
    console.log(req.query.name);
    res.send(`Welcome to API Page for Kasbaik Backend for Users ${req.query.name}`);
  } catch (error) {
    console.log(error);;
  }
});


PORT = process.env.PORT
app.listen(PORT || 8080, () => {console.log(`Application is running on ${PORT}!! `)})