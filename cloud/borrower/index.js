const express = require('express');
const borrowerRoutes = require('./src/borrowers/routes');

const app = express();
const port = 3000;

app.use(express.json());

app.get('/', (req, res) => {
  res.send('Hello World');
});

app.use('/api/v1/borrower', borrowerRoutes);

app.listen(port, () => {
  console.log(`Server is running on port ${port}`);
});
