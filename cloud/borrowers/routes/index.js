const express = require("express");
const router = express.Router();
const controller = require("../controller/controller");

let routes = (app) => {
    router.post("/borrower", controller.addBorrower);
    app.use(router);
}

module.exports = routes;