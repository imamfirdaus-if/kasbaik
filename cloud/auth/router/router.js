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
// digunakan untuk melakukan signup / register 
router.post('/signup', userController.signup_post)

// digunakan untuk melakukan login
router.post('/login', userController.login_post)

// digunakan untuk melakukan logout dari aplikasi sehingga menghancurkan jwt
router.post('/logout', Auth.verifyToken, userController.logout_post)
// digunakan untuk memberikan response mengenai profile
router.get('/logout', Auth.verifyToken, showController.profileData)

// digunakan untuk menghapus akun
router.post('/delete', Auth.verifyToken, userController.delete_post)
router.get('/delete', Auth.verifyToken, showController.profileData)

router.get('/home', Auth.verifyToken, showController.userData)

//digunakan untuk melakukan update dari profile user
router.post("/profile", Auth.verifyTokenUser, fileController.profile_post);
router.get('/profile', Auth.verifyTokenUser , showController.profileData)

// router.get("/files", Auth.verifyToken, fileController.getListFiles);
// router.get("/files/:name", Auth.verifyToken, fileController.download);

// digunakan untuk membuat request peminjaman uang oleh peminjam
router.post("/borrower", Auth.verifyTokenUser, borrowerController.addBorrower);
router.get("/borrower", Auth.verifyTokenUser, showController.borrowerData)
router.put("/borrower", Auth.verifyTokenUser, borrowerController.updateBorrower)

// digunakan untuk mengupdate profile dari mitra
router.post("/mitraprof", Auth.verifyTokenMitra, mitraController.profileMitra_post )
router.get("/mitraprof", Auth.verifyTokenMitra, showController.mitraProfileData)

// router.get("/getdataborrower", Auth.verifyTokenMitra, mitraController.seeAllBorrowers);

// digunakan oleh mitra untuk melihat dan mengupdate semua list request peminjaman
router.post("/updatestatus", Auth.verifyTokenMitra, mitraController.editStatusBorrower);
router.get("/updatestatus", Auth.verifyTokenMitra, showController.mitraData);
// router.get("/updatestatus/:id_borrower", Auth.verifyTokenMitra, showController.mitraData);

// digunakan untuk menambahkan payment request oleh mitra
router.post("/payment", Auth.verifyTokenMitra, mitraController.createPayment)
router.get("/payment", Auth.verifyToken, showController.paymentData)


module.exports = router
