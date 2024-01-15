const { Router } = require('express');
const router = Router();

const {
  getHistorial,
  createHistorial,

} = require('../controllers/historial.controller');

router.get('/historial',getHistorial);
router.post('/historial',createHistorial);



module.exports = router;