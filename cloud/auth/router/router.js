const express = require('express')
const router = express.Router()
const userController = require('../controller/userControler')
const Auth = require('../middleware/auth')
const fileController = require('../controller/profileController')
const borrowerController = require('../controller/borrowerController')
const mitraController = require('../controller/mitraController')
const showController = require('../controller/showController')
const messageController = require('../controller/messageController')
const adminController = require('../controller/adminController')


// digunakan untuk melakukan signup / register 
router.post('/signup', userController.signup_post)

// digunakan untuk melakukan login
router.post('/login', userController.login_post)

// digunakan untuk melakukan logout dari aplikasi sehingga menghancurkan jwt
router.get('/logout', Auth.verifyToken, userController.logout_post)
// digunakan untuk memberikan response mengenai profile
// router.get('/logout', Auth.verifyToken, showController.profileData)

// digunakan untuk menghapus akun
router.post('/delete', Auth.verifyToken, userController.delete_post)
// digunakan untuk memberikan response mengenai profile yang akan dihapus
router.get('/delete', Auth.verifyToken, showController.profileData)

//digunakan sebagai home page yang berisi respon table user, profile, peminjaman aktif dan pesan
router.get('/home', Auth.verifyToken, showController.homeData)

//digunakan untuk mengupdate password
router.post('/updatepassword', Auth.verifyToken, userController.update_post)

//digunakan untuk melakukan update dari profile user
router.post("/profile", Auth.verifyTokenUser, fileController.profile_post);
// untuk mendapatkan response dari profile
router.get('/profile', Auth.verifyTokenUser , showController.profileData)


// digunakan untuk membuat request peminjaman uang oleh peminjam
router.get("/borrower", Auth.verifyTokenUser, showController.borrowerData) // melihat semua pinjama 
router.post("/borrower", Auth.verifyTokenUser, borrowerController.addBorrower); //menambah pinjaman
router.get("/borrower/:id_borrower", Auth.verifyTokenUser, showController.borrowerDatabyId) // melihat salah satu pinjaman
router.put("/borrower/:id_borrower", Auth.verifyTokenUser, borrowerController.updateBorrower) // mengupdate pinjaman status still pending
router.delete("/borrower/:id_borrower", Auth.verifyTokenUser, borrowerController.deleteBorrower) //menghapus pinjaman yang masih pending

// digunakan untuk mengupdate profile dari mitra
router.post("/mitraprof", Auth.verifyTokenMitra, mitraController.profileMitra_post )
router.get("/mitraprof", Auth.verifyTokenMitra, showController.mitraProfileData)

// digunakan oleh mitra untuk melihat dan mengupdate semua list request peminjaman
router.get("/updatestatus", Auth.verifyTokenMitra, showController.mitraData);
router.post("/updatestatus/:id_borrower", Auth.verifyTokenMitra, mitraController.editStatusBorrower);
router.get("/updatestatus/:id_borrower", Auth.verifyTokenMitra, showController.mitraDataById);

// digunakan untuk menambahkan payment request oleh mitra
router.get("/payment", Auth.verifyToken, showController.paymentData) // untuk melihat daftar peminjam yg sedang payment
router.post("/payment/:id_borrower", Auth.verifyTokenMitra, mitraController.createPaymentById) //untuk menginput pembayaran dari satu peminjam
router.get("/payment/:id_borrower", Auth.verifyToken, showController.paymentDataById); // melihat semua pembayaran peminjam

//see credit
router.get("/credit", Auth.verifyToken, showController.creditData )
router.get("/credit/:id_borrower", Auth.verifyToken, showController.creditDataById )

// digunakan untuk melihat message
router.get('/messages', Auth.verifyToken , messageController.showAllMess)
router.get('/messages/:id_message', Auth.verifyToken , messageController.updateMess) //for update mess has_read = true and see message

//digunakan untuk memberikan feedback message dari peminjam ke mitra sehingga peminjam tau message bukti payment accepted atau rejected
router.post('/messages/:id_message', Auth.verifyTokenMitra , messageController.handleMessagebyMitra)

// digunakan untuk melihat history input bukti
router.get("/buktibayar", Auth.verifyTokenUser, showController.buktiBayarMessage)
// digunakan untuk memberikan bukti bayar ke mitra sebagai message
router.post("/buktibayar", Auth.verifyTokenUser, messageController.createMessagetoMitra) // post untuk memberikan bukti bayar

//summary mitra
router.get("/summaryMitra/:id_mitra", Auth.verifyTokenMitra, showController.summarryMitra)

//role admin


router.get("/listAkunUser/:id_user", Auth.verifyTokenAdmin, adminController.listDetailUser)
router.get("/listAkunMitra/:id_user", Auth.verifyTokenAdmin, adminController.listDetailMitra)
router.get("/listBorrowerPending", Auth.verifyTokenAdmin, adminController.listBorrowerPending)
router.get("/listBorrowerAcc", Auth.verifyTokenAdmin, adminController.listBorrowerAcc)
router.get("/listBorrowerHistory", Auth.verifyTokenAdmin, adminController.listBorrowerHistory)

router.get("/listPayment", Auth.verifyTokenAdmin, adminController.listPayment)

router.get("/summary", Auth.verifyTokenAdmin, adminController.summarry)

router.get("/listAkunUser", Auth.verifyTokenAdmin, adminController.listAkunUser)
router.get("/listAkunMitra", Auth.verifyTokenAdmin, adminController.listAkunMitra)
router.get("/listBorrower", Auth.verifyTokenAdmin, adminController.listBorrower)
router.get("/jumlahtotal", Auth.verifyTokenAdmin, adminController.jumlahTotal)
router.get('/useradmin/:id_user', Auth.verifyToken, adminController.homeAdmin)
router.get('/mitraadmin/:id_mitra', Auth.verifyToken, adminController.mitraAdmin)
router.get('/paymentadmin', Auth.verifyToken, adminController.paymentAdmin)
router.get("/listBorrower/:id_borrower", Auth.verifyTokenAdmin, adminController.listDetailBorrower)
router.get("/listPayment/:id_payment", Auth.verifyTokenAdmin, adminController.listPaymentbyId)
//get ALL message in user where has_read = false
// router.get('/profile/message', Auth.verifyTokenUser , showController.messageData)
// router.put('/profile/message/:id_message', Auth.verifyTokenUser , messageController.updateMess)

// untuk melihat pembayaran aktif dan melihat total paymentnya 
// router.get("/userpayment", Auth.verifyToken, showController.userPaymentData)  // sudah ada didalam /borrower/:id_borrower"  

// router.get("/buktibayar/:id_message", Auth.verifyTokenMitra, messageController.updateMess) // ini digantikan oleh /message/:id_message

// router.get("/buktibayar", Auth.verifyTokenMitra, messageController.showAllMess) // ini digantikan oleh /message
module.exports = router
