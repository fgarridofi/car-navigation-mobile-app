const Historial = require('../model/Historial');
const userCtrl = { };



userCtrl.createHistorial = async (req, res) => {
    const { origen,destino, kilometers, tiempo, fecha } = req.body;
    console.log('Request body:', req.body);
    const newHistorial = new Historial({origen, destino, kilometers, tiempo, fecha });
    await newHistorial.save();
    res.send(newHistorial);
};

userCtrl.getHistorial = async (req, res) => {
    Historial.find((err, historial) => {
      if (err) {
        res.send(err);
      }
      res.json(historial);
    });
};

module.exports = userCtrl;
