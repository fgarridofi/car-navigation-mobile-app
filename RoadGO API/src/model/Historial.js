const { Schema, model } = require('mongoose');

const historialSchema = new Schema({
  origen: { type: String, required: true },
  destino: { type: String, required: false },
  kilometers: { type: Number, required: true },
  tiempo: { type: Number, required: true },
  fecha: { type: String, required: true },
});

module.exports = model('historial', historialSchema);