const express = require('express');
const morgan = require('morgan');

// Initializations
const app = express();

// Settings
app.set('port', process.env.PORT || 4000);

// Middlewares
app.use(morgan('dev'));
app.use(express.urlencoded( { extended : false} ));
app.use(express.json());

// Routes
app.use(require('./routes/user.routes'));
app.use(require('./routes/component.routes')); 
app.use(require('./routes/historial.routes')); 


module.exports = app;