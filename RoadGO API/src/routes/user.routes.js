const { Router } = require('express');
const router = Router();

const { createNewUser, getUserByMail, authenticateUser } = require('../controllers/user.controllers');
const {getUsers} = require('../controllers/user.controllers');

router.post('/user', createNewUser);
router.post('/authenticate', authenticateUser);

router.get('/users', getUsers);
router.get('/users/:mail',getUserByMail);

module.exports = router;