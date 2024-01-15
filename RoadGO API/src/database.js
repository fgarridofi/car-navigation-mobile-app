const mongoose = require('mongoose');
//const MONGODB_URI = 'mongodb://localhost:27017/form';
const MONGODB_URI = 'mongodb+srv://fgarrido:admin@cluster0.q7pkver.mongodb.net/?retryWrites=true&w=majority';


mongoose.connect(MONGODB_URI, {
    useUnifiedTopology: true,
    useNewUrlParser: true
})
.then(db => console.log('Database is connected'))
.catch(err => console.log(err));