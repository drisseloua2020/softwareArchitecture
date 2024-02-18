const { Pool } = require('pg');

const connectionString = process.env.POSTGRES_URI;
const database = process.env.POSTGRES_DATABASE;

const pool = new Pool({
  connectionString: connectionString,
  ssl: {
    rejectUnauthorized: false,
  },
});

let dbConnection;

module.exports = {
  connectToServer: function (callback) {
    pool.connect(function (err, client, done) {
      if (err || !client) {
        return callback(err);
      }

      dbConnection = client;
      console.log('Successfully connected to PostgreSQL.');

      return callback();
    });
  },

  getDb: function () {
    return dbConnection;
  },
};
