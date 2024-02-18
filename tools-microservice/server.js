// Loads the configuration from config.env to process.env
require('dotenv').config({ path: './.env.local' });
const express = require('express');
const cors = require('cors');
const { Pool } = require('pg');
const fs = require('fs');

const PORT = process.env.PORT || 5000;
const app = express();

app.use(cors());
app.use(express.json());
app.use(require('./routes/record'));

// Global error handling
app.use(function (err, _req, res) {
  console.error(err.stack);
  res.status(500).send('Something broke!');
});

const pool = new Pool({
  connectionString: process.env.POSTGRES_URI,
  ssl: {
    rejectUnauthorized: false, // Adjust based on your PostgreSQL server SSL settings
  },
});

const loadData = () => {
  const schemaSql = fs.readFileSync('./catalog_schema.sql', 'utf8');
  const dataSql = fs.readFileSync('./catalog_data.sql', 'utf8');

  // Run the schema SQL
  pool.query(schemaSql)
      .then(() => {
        console.log('Schema loaded successfully');

        // Run the data SQL
        return pool.query(dataSql);
      })
      .then(result => {
        console.log('Data loaded successfully');
      })
      .catch(error => {
        console.error('Error loading schema or data:', error);
      });
};

// Start the Express server after connecting to PostgreSQL
pool.connect((err, client, done) => {
  if (err) {
    console.error('Error connecting to PostgreSQL:', err);
    process.exit();
  }

  console.log('Connected to PostgreSQL');

  // Load schema and data
  loadData();

  // Release the client to the pool
  done();

  // Start the Express server
  app.listen(PORT, () => {
    console.log(`Server is running on port: ${PORT}`);
  });
});