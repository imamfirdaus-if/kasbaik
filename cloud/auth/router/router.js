const express = require('express')
const router = express.Router()
const userController = require('../controller/userControler')
const Auth = require('../middleware/auth')
const mainController = require('../controller/mainController')
const fileController = require('../controller/profileController')
const borrowerController = require('../controller/borrowerController')
const mitraController = require('../controller/mitraController')


router.get('/test', userController.index) 
router.post('/signup', userController.signup_post)
router.post('/login', userController.login_post)
router.post('/logout', Auth.verifyToken, userController.logout_post)
router.post('/delete', Auth.verifyToken, userController.delete_post)
router.get('/home', Auth.verifyToken, (req, res) => {
    console.log('mid');
},mainController.main_user, mainController.main_mitra)
router.post("/profile", Auth.verifyToken, fileController.profile_post);
router.get("/files", Auth.verifyToken, fileController.getListFiles);
router.get("/files/:name", Auth.verifyToken, fileController.download);
router.post("/borrower", Auth.verifyToken, borrowerController.addBorrower);
router.post("/mitraprof", Auth.verifyTokenMitra, mitraController.profileMitra_post )

module.exports = router
