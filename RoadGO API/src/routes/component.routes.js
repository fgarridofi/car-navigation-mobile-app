const { Router } = require('express');
const router = Router();

const {
  getComponents,
  createComponent,
  updateComponent,
  deleteComponent,
} = require('../controllers/component.controller');

router.get('/components',getComponents);
router.post('/componente',createComponent);
router.put('/component/:id', updateComponent);
router.delete('/component/:id', deleteComponent);



module.exports = router;