const db = require("../model/model");
const Helper = require("../middleware/helper");
const dbBorrower = db.borrower;
const dbMitra = db.mitra;
const dbProfile = db.profileUser;
const dbProfileMitra = db.profileMitra
const dbCredit = db.credit
const dbUser= db.users;
const Op = require('sequelize').Op;

const listAkunUser = async (req, res) => {
    try {
        const query= `SELECT * FROM users where role = 'user'`; 
        console.log(query);
        const User = await db.sequelize.query(query)
        console.log(Helper.toObject(User[0]));
        return res.status(200).send(Helper.toObject(User)[0])
        
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
        const query= `SELECT * FROM profile_mitras where id_user = '${req.params.id_user}'`; 
        console.log(query);
        const User = await db.sequelize.query(query)
        console.log(Helper.toObject(User[0][0]));
        return res.status(200).send(Helper.toObject(User)[0][0])
        
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

module.exports = {
    listAkunUser,
    listAkunMitra,
    listDetailUser,
    listDetailMitra,
    listBorrowerPending,
    listBorrowerAcc,
    listBorrowerHistory,
    listDetailBorrower,
}