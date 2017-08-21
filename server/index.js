const express = require('express')
const bodyParser = require('body-parser');

const app = express()
app.use(bodyParser.json({type: '*/*'}));

app.post('/login', function (req, res) {

  console.log(req.body);

  if ( req.body.username !== 'jake' || req.body.password !== 'secret' ) {
    res.status('404').json({error: 'invalid credentials'});
    return;
  }

  res.json({});
});

app.get('/profile', function(req, res) {

  res.json({
    firstName: 'Jake',
    lastName: 'Wharton',
    totalRides: 200
  });

});

app.listen(9000, function () {
  console.log('Example app listening on port 9000!')
});