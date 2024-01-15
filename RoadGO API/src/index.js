const app = require('./server');
require('./database');

// starting the server
app.listen(app.get('port'), () => {
    console.log('Server on port', app.get('port'));
});