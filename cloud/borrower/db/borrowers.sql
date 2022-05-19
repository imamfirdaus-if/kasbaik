CREATE DATABASE borrowers;

CREATE TABLE payment (
    id SERIAL PRIMARY KEY,
    payment_method VARCHAR(255) NOT NULL
);

INSERT INTO payment (payment_method) VALUES ('Cash'), ('Cicil');

CREATE TABLE partner (
    id SERIAL PRIMARY KEY,
    partner_name VARCHAR(255) NOT NULL
);

INSERT INTO partner (partner_name) VALUES ('Amanah'), ('DKM Darussalam'), ('Bank Sampah Digital'), ('Dreamdelion');

CREATE TYPE status as enum ('Accept', 'Reject', 'Pending');

CREATE TABLE borrower (
    id SERIAL PRIMARY KEY,
    loan_amount INTEGER,
    reason_borrower VARCHAR(255),
    dependents_amount INTEGER,
    payment_id SERIAL,
    FOREIGN KEY (payment_id) REFERENCES payment(id),
    partner_id SERIAL,
    FOREIGN KEY (partner_id) REFERENCES partner(id),
    status status DEFAULT 'Pending',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

INSERT INTO borrower (loan_amount, reason_borrower, dependents_amount, payment_id, partner_id, status) VALUES (100000, 'I need money', 2, 1, 1, 'Pending'); 

