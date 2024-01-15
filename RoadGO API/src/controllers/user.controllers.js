const userCtrl = { };
const User = require('../model/User');

userCtrl.createNewUser = async (req, res) => {
    const { firstName, lastName, phone, gender, email, password, createdAt } = req.body;
    const newUser = new User({ firstName, lastName, phone, gender, email, password, createdAt });
    await newUser.save();
    res.send(newUser);
};

userCtrl.authenticateUser = async (req , res) =>{
    const { email, password } = req.body;
     
    User.findOne({email}, (err, user) =>{
        if(err){
            res.status(501).send('Error al autentificar el usuario');
        }else if(!user){
            console.log({email});
            res.status(502).send('El usuario no existe');
            
        }else{
            user.isCorrectPassword(password, (err, result)=>{
                if(err){
                    res.status(501).send('Error al autentificar ');
                }else if(result){
                    res.status(200).send('Usuario autentificado');
                }else{
                    res.status(503).send('Usuario y/o contraseña incorrecta ');
                }
            });
        }

    });

};

userCtrl.getUsers = async (req, res) => {
    User.find((err, users) => {
      if (err) {
        res.send(err);
      }
      res.json(users);
    });
};

userCtrl.getUserByMail = async (req, res) => {
    User.findOne(
        { _id: req.params.mail },
       
        (err, User) => {
          if (err) {
            res.send(err);
          } else res.json(User);
        }
    );    
};

module.exports = userCtrl;