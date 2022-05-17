const pool = require('../../db/db');
const queries = require('./queries');

const getBorrower = (req, res) => {
    pool.query(queries.getBorrower, (err, results) => {
        if (err) {
            throw err;
        }
        res.status(200).json(results.rows);
    });
};

const createBorrower = (req, res) => {
    const {
        loan_amount,
        reason_borrower,
        dependents_amount,
        payment_id,
        partner_id,
    } = req.body;

    const status = "Pending";

   pool.query(queries.createBorrower, [loan_amount, reason_borrower, dependents_amount, payment_id, partner_id, status], (err, results) => {
        if (err) {
            throw err;
        }
        res.status(201).json({
            message: 'Borrower created successfully'
        });
    });
};

module.exports = {
    getBorrower,
    createBorrower
};