const express = require('express');

const router = express.Router();

const db = require('../db/conn');

router.route('/tools').get(async function (_req, res) {
    const dbConnect = db.getDb();

    dbConnect
        .query('SELECT * FROM Tool')
        .then(result => {
            res.json(result.rows);
        })
        .catch(error => {
            res.status(400).send('Error fetching tools!');
        });
});

router.route('/toolCharges').get(async function (_req, res) {
    const dbConnect = db.getDb();

    dbConnect
        .query('SELECT * FROM ToolCharges')
        .then(result => {
            res.json(result.rows);
        })
        .catch(error => {
            res.status(400).send('Error fetching tool charges!');
        });
});

router.route('/tool/:toolCode').get(async function (req, res) {
    const toolCode = req.params.toolCode;
    const dbConnect = db.getDb();

    dbConnect
        .query('SELECT * FROM Tool WHERE ToolCode = $1', [toolCode])
        .then(result => {
            res.json(result.rows[0]);
        })
        .catch(error => {
            res.status(400).send('Error fetching tool by ToolCode!');
        });
});

router.route('/toolCharges/:toolType').get(async function (req, res) {
    const toolType = req.params.toolType;
    const dbConnect = db.getDb();

    dbConnect
        .query('SELECT * FROM ToolCharges WHERE ToolType = $1', [toolType])
        .then(result => {
            res.json(result.rows);
        })
        .catch(error => {
            res.status(400).send('Error fetching tool charges by ToolType!');
        });
});

module.exports = router;