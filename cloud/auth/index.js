const express = require('express')
const app =express()
require('dotenv').config()
const session = require('express-session')
const bodyParser = require('body-parser');
const db = require('./db/db')

const cookieParser = require('cookie-parser');
const jwt = require('jsonwebtoken')
const userRouter = require('./router/router')

app.use(
    session({
      secret: "ini contoh secret",
      saveUninitialized: false,
      resave: false,
      cookie: { maxAge:60*60*1000}
    })
);
app.use(express.json());
app.use(bodyParser.json());
app.use(cookieParser())
app.use(bodyParser.urlencoded({ extended: true}));
 
// app.use(express.static(__dirname + "/"));
// var temp;
app.use('/', userRouter)


app.get('/', async (req, res) => {
  try {
    res.send("Welcome to API Page for Kasbaik Backend");
  } catch (error) {
    console.log(error);;
  }
});



app.get('/getAllUsers', async (req, res) => {
  try {
    const query = `SELECT * FROM users ;`
    const results = await db.query(query)
    res.send(results.rows)
  } catch (error) {
    console.log(error);
  }
});



app.get('/logout', (req, res) => {
  req.session.destroy(() => {
     console.log("user logged out.")
  });
  res.redirect('/');
});


PORT = process.env.PORT
app.listen(PORT || 8080, () => {console.log(`Application is running on ${PORT}!! `)})