const express = require('express')
const router = express.Router()
const userController = require('../controller/userControler')
const Auth = require('../controller/auth')
const mainController = require('../controller/mainController')

router.get('/', userController.index)
router.post('/signup', userController.signup_post)
router.post('/login', userController.login_post)
router.post('/logout', Auth.verifyToken, userController.logout_post)
router.post('/delete', Auth.verifyToken, userController.delete_post)
router.get('/home', Auth.verifyToken, mainController)

module.exports = router
