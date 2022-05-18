const cors = require("cors");
const express = require("express");
const app = express();
const bodyParser = require('body-parser');
const db = require('./model/model')
let corsOptions = {
    origin: "http://localhost:8081",
};

app.use(bodyParser.json());
app.use(bodyParser.urlencoded({ extended: true}));

app.use(cors(corsOptions));
const initRoutes = require("./routes/index");
app.use(express.urlencoded({ extended: true }));

initRoutes(app);
app.get('/', async (req, res) => {
    try {
      res.send("Welcome to API Page for Kasbaik Backend for Profile");
    } catch (error) {
      console.log(error);;
    }
  });
db.sequelize.sync().then(
    console.log("Syncing is complete")
  );

const port = 8080;
app.listen(port, () => {
    console.log(`Running at localhost:${port}`);
})