const express = require('express')
const router = express.Router()
const userController = require('../controller/userControler')
const Auth = require('../middleware/auth')
const mainController = require('../controller/mainController')
const fileController = require('../controller/fileController')
const borrowerController = require('../controller/borrowerController')

router.get('/test', userController.index)
router.post('/signup', userController.signup_post)
router.post('/login', userController.login_post)
router.post('/logout', Auth.verifyToken, userController.logout_post)
router.post('/delete', Auth.verifyToken, userController.delete_post)
router.get('/home', Auth.verifyToken, mainController)
router.post("/upload", fileController.upload);
router.get("/files", fileController.getListFiles);
router.get("/files/:name", fileController.download);
router.post("/borrower", borrowerController.addBorrower);

module.exports = router
