const express = require('express')
const router = express.Router()
const userController = require('../controller/userControler')
const Auth = require('../middleware/auth')
const mainController = require('../controller/mainController')
const fileController = require('../controller/profileController')
const borrowerController = require('../controller/borrowerController')
const mitraController = require('../controller/mitraController')
const showController = require('../controller/showController')

router.get('/test', userController.index) 
router.post('/signup', userController.signup_post)

router.post('/login', userController.login_post)
router.post('/logout', Auth.verifyToken, userController.logout_post)
router.get('/logout', Auth.verifyToken, showController.profileData)

router.post('/delete', Auth.verifyToken, userController.delete_post)
router.get('/delete', Auth.verifyToken, showController.profileData)

router.get('/home', Auth.verifyToken, showController.userData)

router.post("/profile", Auth.verifyTokenUser, fileController.profile_post);
router.get('/profile', Auth.verifyTokenUser , showController.profileData)

// router.get("/files", Auth.verifyToken, fileController.getListFiles);
// router.get("/files/:name", Auth.verifyToken, fileController.download);

router.post("/borrower", Auth.verifyTokenUser, borrowerController.addBorrower);
router.get("/borrower", Auth.verifyTokenUser, showController.borrowerData)

router.post("/mitraprof", Auth.verifyTokenMitra, mitraController.profileMitra_post )
router.get("/mitraprof", Auth.verifyTokenMitra, showController.mitraProfileData)

// router.get("/getdataborrower", Auth.verifyTokenMitra, mitraController.seeAllBorrowers);

router.post("/updatestatus", Auth.verifyTokenMitra, mitraController.editStatusBorrower);
router.get("/updatestatus", Auth.verifyTokenMitra, showController.mitraData);
module.exports = router
