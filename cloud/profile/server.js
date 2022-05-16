const cors = require("cors");
const express = require("express");
const app = express();
const bodyParser = require('body-parser');
let corsOptions = {
    origin: "http://localhost:8081",
};

app.use(bodyParser.json());
app.use(bodyParser.urlencoded({ extended: true}));

app.use(cors(corsOptions));
const initRoutes = require("./routes/index");
app.use(express.urlencoded({ extended: true }));
initRoutes(app);
const port = 8080;
app.listen(port, () => {
    console.log(`Running at localhost:${port}`);
})