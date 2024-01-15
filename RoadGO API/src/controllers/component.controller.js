const Component = require('../model/Component');
const userCtrl = { };



userCtrl.createComponent = async (req, res) => {
    const { id,name,brand, kilometers, lifespan, type } = req.body;
    const newComponent = new Component({id, name, brand, kilometers, lifespan, type });
    await newComponent.save();
    res.send(newComponent);
};

userCtrl.getComponents = async (req, res) => {
    Component.find((err, components) => {
      if (err) {
        res.send(err);
      }
      res.json(components);
    });
};

userCtrl.updateComponent = async (req, res) => {
  const { id, name, brand, kilometers, lifespan, type } = req.body;
  const updatedComponent = await Component.findOneAndUpdate(
      { id },
      { name, brand, kilometers, lifespan, type },
      { new: true }
  );
  if (!updatedComponent) {
      res.status(404).send({ message: 'Component not found' });
  } else {
      res.send(updatedComponent);
  }
};

userCtrl.deleteComponent = async (req, res) => {
  const { id } = req.params;
  const deletedComponent = await Component.findOneAndDelete({ id: req.params.id });
  res.send({ message: 'Component deleted' });
};

module.exports = userCtrl;