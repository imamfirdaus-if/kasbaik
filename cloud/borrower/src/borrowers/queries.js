const payment = "INSERT INTO payment (payment_method) VALUES ($1)";

const partner = "INSERT INTO partner (partner_name) VALUES ($1)";

const getBorrower = "SELECT * FROM borrower";

const createBorrower = "INSERT INTO borrower (loan_amount, reason_borrower, dependents_amount, payment_id, partner_id, status) VALUES ($1, $2, $3, $4, $5, $6) RETURNING *";

module.exports = {
    payment,
    partner,
    getBorrower,
    createBorrower
};