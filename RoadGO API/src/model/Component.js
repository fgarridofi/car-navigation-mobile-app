const { Schema, model } = require('mongoose');

const componentSchema = new Schema({
  id: { type: String, required: true, unique: true },
  name: { type: String, required: true },
  brand: { type: String, required: false },
  kilometers: { type: Number, required: true },
  lifespan: { type: Number, required: true },
  type: { type: String, required: true },
});

module.exports = model('component', componentSchema);