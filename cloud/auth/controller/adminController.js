const db = require("../model/model");
const Helper = require("../middleware/helper");
const { where } = require("sequelize");
const { get } = require("../router/router");
const dbBorrower = db.borrower;
const dbMitra = db.mitra;
const dbProfile = db.profileUser;
const dbProfileMitra = db.profileMitra
const dbCredit = db.credit
const dbUser= db.users;
const dbPayment = db.payment;
const dbUserPayment = db.userPayment;
const Op = require('sequelize').Op;

const listAkunUser = async (req, res) => {
    try {
        const query= `SELECT * FROM users FULL OUTER JOIN profiles ON users.id_user = profiles.id_user WHERE role = 'user'`; 
        console.log(query);
        const User1 = await db.sequelize.query(query)
        const user = Helper.toObject(User1[0])
        console.log(user);
        return res.status(200).send(user)
        
    } catch (err) {
        console.log(err);
        return res.status(400).send({status : err})
    }
}

const listAkunMitra = async (req, res) => {
    try {
        const query= `SELECT * FROM users where role = 'mitra'`; 
        console.log(query);
        const User = await db.sequelize.query(query)
        console.log(Helper.toObject(User[0]));
        return res.status(200).send(Helper.toObject(User)[0])
        
    } catch (err) {
        console.log(err);
        return res.status(400).send({status : err})
    }
}

const listDetailUser = async (req, res) => {
    try {
        const query= `SELECT * FROM profiles where id_user = '${req.params.id_user}'`; 
        const query1 = `SELECT * FROM messages where id_user = '${req.params.id_user}'`; 
        console.log(query);
        const User = await db.sequelize.query(query)
        const User1 = await db.sequelize.query(query1)
        const objek = Helper.toObject(User[0])
        const objek1 = Helper.toObject(User1[0])
        console.log(Helper.toObject(User[0][0]));
        return res.status(200).send({
            profiles: objek,
            messages : objek1, 
        })
        
    } catch (err) {
        console.log(err);
        return res.status(400).send({status : err})
    }
}

const listDetailMitra = async (req, res) => {
    try {
        const profile = await dbProfileMitra.findOne({where: {id_user : req.params.id_user}});
        const objek = Helper.toObject(profile);
        console.log(objek);
        await dbMitra.findAll({where: {id_mitra : objek.id_mitra}})
        .then(async data => {
            return res.status(200).send({
                profiles : objek,
                borrower : data,
            });
        })
        
    } catch (err) {
        console.log(err);
        return res.status(400).send({status : err})
    }
}

const listBorrower = async (req, res) => {
    try {
        await dbMitra.findAll()
        .then(async data => {
            return res.status(200).send(data);
        })

        
    } catch (err) {
        console.log(err);
        return res.status(400).send({status : err})
    }
}


const listBorrowerPending = async (req, res) => {
    try {
        await dbMitra.findAll({where: {status : 'pending'}})
        .then(async data => {
            return res.status(200).send(data);
        })

    } catch (err) {
        console.log(err);
        return res.status(400).send({status : err})
    }
}

const listBorrowerAcc = async (req, res) => {
    try {
        await dbMitra.findAll({where: {status : { [Op.or] : ["accepted", "payment"]}}})
        .then(async data => {
            return res.status(200).send(data);
        })

    } catch (err) {
        console.log(err);
        return res.status(400).send({status : err})
    }
}

const listBorrowerHistory = async (req, res) => {
    try {
        await dbMitra.findAll({where: {status : 'done'}})
        .then(async data => {
            return res.status(200).send(data);
        })

    } catch (err) {
        console.log(err);
        return res.status(400).send({status : err})
    }
}


const listDetailBorrower = async (req, res) => {
    try {
        const data1 = await dbBorrower.findAll({where: {id_borrower : req.params.id_borrower}});
        return res.status(200).send(data1);
        
    } catch (err) {
        console.log(err);
        return res.status(400).send({status : err})
    }
}

const listPayment = async (req, res) => {
    try {
        const data1 = await dbUserPayment.findAll();
        return res.status(200).send(data1);
        
    } catch (err) {
        console.log(err);
        return res.status(400).send({status : err})
    }
}

const listPaymentbyId = async (req, res) => {
    try {
        const data1 = await dbPayment.findAll({where : {id_borrower : req.params.id_borrower}});
        const data2 = await dbUserPayment.findAll({where : {id_borrower : req.params.id_borrower}});
        return res.status(200).send({
            "Table Payment" : data1,
            description : data2,
        });
        
    } catch (err) {
        console.log(err);
        return res.status(400).send({status : err})
    }
}

const summarry = async (req, res) => {
    try {
        const data1 = await dbMitra.findAll();
        const objek = Helper.toObject(data1);
        console.log(objek);
        let pending = 0;
        let accepted = 0;
        let borrower = 0;
        for (let i = 0; i < objek.length; i++){
            if (objek[i].status === "pending"){
                pending++;
                borrower++;
            }
            else if (objek[i].status === "accepted" || objek[i].status === "payment"){
                accepted++;
                borrower++;
            }
        }
        console.log(pending, accepted, borrower);
        const data = {
            pending : pending,
            accepted : accepted,
            borrower : borrower,
        }
        return res.status(200).send(data);
        
    } catch (err) {
        console.log(err);
        return res.status(400).send({status : err})
    }
}

const jumlahTotal = async (req, res) => {
    try {
        let totalPayment = 0
        const getUser = await db.users.findAll({where : {role : 'user'}})
        const getMitra = await db.users.findAll({where : {role : 'mitra'}})
        const getBorrower = await db.borrower.findAll()
        const query= `SELECT * FROM mitras FULL OUTER JOIN payments ON payments.id_borrower = mitras.id_borrower FULL OUTER JOIN profile_mitras ON mitras.id_mitra = profile_mitras.id_mitra WHERE status = 'payment'`; 
        const getPayment = await db.sequelize.query(query)
        const getPayment1 = Helper.toObject(getPayment[0])
        // const getPayment = await db.payment.findAll()
        getPayment1.forEach(element => {
            totalPayment = totalPayment + element.amount_payment
        });
        return res.status(200).send({totaluser : getUser ,totalmitra : getMitra, totalborrower : getBorrower, totalpayment :totalPayment, payment :getPayment1})
        
    } catch (err) {
        console.log(err);
        return res.status(400).send({status : err})
    }
}

module.exports = {
    listAkunUser,
    listAkunMitra,
    listDetailUser,
    listDetailMitra,
    listBorrower,
    listBorrowerPending,
    listBorrowerAcc,
    listBorrowerHistory,
    listDetailBorrower,
    listPayment,
    listPaymentbyId,
    summarry,
    jumlahTotal,
}