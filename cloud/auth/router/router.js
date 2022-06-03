const express = require('express')
const router = express.Router()
const userController = require('../controller/userControler')
const Auth = require('../middleware/auth')
const mainController = require('../controller/mainController')
const fileController = require('../controller/profileController')
const borrowerController = require('../controller/borrowerController')
const mitraController = require('../controller/mitraController')
const showController = require('../controller/showController')
const messageController = require('../controller/messageController')


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

router.get('/home', Auth.verifyToken, showController.homeData)

//digunakan untuk mengupdate password
router.post('/updatepassword', Auth.verifyToken, userController.update_post)

//digunakan untuk melakukan update dari profile user
router.post("/profile", Auth.verifyTokenUser, fileController.profile_post);
router.get('/profile', Auth.verifyTokenUser , showController.profileData)

//get ALL message in user where has_read = false
router.get('/profile/message', Auth.verifyTokenUser , showController.messageData)

router.put('/profile/message/:id_message', Auth.verifyTokenUser , messageController.updateMess)

// digunakan untuk melihat message
router.get('/messages', Auth.verifyToken , messageController.showAllMess)
router.get('/messages/:id_message', Auth.verifyToken , messageController.updateMess) //for update mess has_read = true and see message

// digunakan untuk membuat request peminjaman uang oleh peminjam
router.get("/borrower", Auth.verifyTokenUser, showController.borrowerData) // melihat semua pinjama 
router.post("/borrower", Auth.verifyTokenUser, borrowerController.addBorrower); //menambah pinjaman
router.get("/borrower/:id_borrower", Auth.verifyTokenUser, showController.borrowerDatabyId) // melihat salah satu pinjaman
router.put("/borrower/:id_borrower", Auth.verifyTokenUser, borrowerController.updateBorrower) // mengupdate pinjaman
router.delete("/borrower/:id_borrower", Auth.verifyTokenUser, borrowerController.deleteBorrower) //menghapus pinjaman yang masih pending

// digunakan untuk mengupdate profile dari mitra
router.post("/mitraprof", Auth.verifyTokenMitra, mitraController.profileMitra_post )
router.get("/mitraprof", Auth.verifyTokenMitra, showController.mitraProfileData)

// digunakan oleh mitra untuk melihat dan mengupdate semua list request peminjaman
router.get("/updatestatus", Auth.verifyTokenMitra, showController.mitraData);
router.post("/updatestatus/:id_borrower", Auth.verifyTokenMitra, mitraController.editStatusBorrower);
// router.get("/updatestatus/:id_borrower", Auth.verifyTokenMitra, showController.mitraData);

// digunakan untuk menambahkan payment request oleh mitra
router.get("/payment", Auth.verifyTokenMitra, showController.paymentData) // untuk melihat daftar peminjam yg sedang payment
router.post("/payment/:id_borrower", Auth.verifyTokenMitra, mitraController.createPaymentById) //untuk menginput pembayaran dari satu peminjam
router.get("/payment/:id_borrower", Auth.verifyToken, showController.paymentDataById); // melihat semua pembayaran peminjam

router.get("/userpayment", Auth.verifyToken, showController.userPaymentData)    

//see credit
router.get("/credit/:id_borrower", Auth.verifyToken, showController.creditData )

router.get("/buktibayar", Auth.verifyTokenUser, messageController.seeMessagetoMitra)
router.post("/buktibayar", Auth.verifyTokenUser, messageController.createMessagetoMitra)

module.exports = router
