const { Router } = require('express');
const controller = require('./controllers');
const router = Router();

router.get('/', controller.getBorrower);
router.post('/', controller.createBorrower);

module.exports = router;
