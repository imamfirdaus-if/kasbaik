const db = require('../model/model')
const Helper = require('../middleware/helper')
const dbUser = db.users
const dbProfile = db.profileUser
const dbBorrower = db.borrower
const dbProfileMitra = db.profileMitra
const dbMitra = db.mitra
const dbPayment = db.payment
const dbUserPayment = db.userPayment
const dbCredit = db.credit
const dbMessage = db.message
const moment = require('moment')
const { QueryTypes } = require('sequelize');
const Op = require('sequelize').Op;


const homeData = async (req, res) => {
    try {
        if(req.role === 'user'){
            const tableUser = await dbUser.findOne({where: {id_user : req.id}})
            const tableProfile = await dbProfile.findOne({where: {id_user : req.id}})
            const tableBorrowerPayment = await dbBorrower.findOne({where: {id_user : req.id, status : 'payment'}})
            const tableMessage = await dbMessage.findAll({where: {id_user : req.id},order : [["createdAt" , "DESC"] ]})
            return res.status(200).send({ 
                user : tableUser,
                profile : tableProfile,
                peminjaman : tableBorrowerPayment,
                message : tableMessage
            })
            
        } else if (req.role === 'mitra'){
            const tableUser = await dbUser.findOne({where: {id_user : req.id}})
            const tableProfile = await dbProfileMitra.findOne({where: {id_user : req.id}})
            // get id mitra
            const result = await db.profileMitra.findOne({where: {id_user: req.id}})
            const getIdMitra = Helper.toObject(result).id_mitra
            const tableMessage = await dbMessage.findAll({where: {id_mitra : getIdMitra ,link_bukti : {[Op.not]: null} }},{order : [["createdAt" , "DESC"] ]})
            return res.status(200).send({
                user : tableUser,
                profile : tableProfile,
                message : tableMessage
            })
        } else if (req.role === 'admin'){
            const tableUser = await dbUser.findOne({where: {id_user : req.id}})
            const tableProfile = await dbProfileMitra.findOne({where: {id_user : req.id}})
            return res.status(200).send({
                user : tableUser,
                profile : tableProfile,
                
            })
        }
        
    } catch (err) {
        console.log(err);
        return res.status(403).send(err)
    }
}


const profileData = async (req, res) => {
    try {
        console.log(req.role);
        if (req.role === 'user') {
            const hasilProfile = await dbProfile.findOne({where: {id_user : req.id}})
            const viewMessageNotif = await db.message.findAll ({where: {id_user : req.id , has_read : false}})
            return res.status(200).send({profile : hasilProfile , message : viewMessageNotif})
        } else if (req.role === 'mitra'){
            await dbProfileMitra.findOne({where: {id_user : req.id}})
            .then(data => {
                return res.status(200).send(data)
            })
        }
       
    } catch (err) {
        return res.status(403).send(err)
    }
}


const borrowerData = async (req, res) => {
    try {
        await dbBorrower.findAll({where: {id_user : req.id}, order : [["createdAt", "DESC"]]})
        .then(data => {
            return res.status(200).send(data)
        })
    } catch (err) {
        console.log(err);
        return res.status(403).send(err)
    }
}

const borrowerDatabyId = async (req, res) => {
    try {
        const hasil1 = await dbBorrower.findAll({where: {id_user : req.id , id_borrower : req.params.id_borrower}})
        const userPayments = await dbUserPayment.findOne({where: {id_borrower: req.params.id_borrower}})
        await dbPayment.findAll({where: {id_borrower: req.params.id_borrower}, order : [["createdAt" , "ASC"] ]})
            .then(data => {
            return res.status(200).send({pinjaman : hasil1, "payment history" : data, description : userPayments })
            })
    } catch (err) {
        console.log(err);
        return res.status(403).send(err)
    }
}

const mitraProfileData = async (req, res) => {
    try {
        await dbProfileMitra.findOne({where: {id_user : req.id}})
        .then(data => {
            return res.status(200).send(data)
        })
    } catch (err) {
        return res.status(403).send(err)
    }
}

const mitraData = async (req, res) => {
    try {
        console.log(req.params.id_borrower);
        const result = await dbProfileMitra.findOne({where: {id_user: req.id}})
        
        const getIdMitra = Helper.toObject(result).id_mitra

        await dbMitra.findAll({where: {id_mitra: getIdMitra}, order : [["createdAt" , "DESC"] ]})
        .then(data => {
            return res.status(200).send(data)
        })
       
    } catch (err) {
        return res.status(403).send(err)
    }
}

const mitraDataById = async (req, res) => {
    try {
        const result = await dbProfileMitra.findOne({where: {id_user: req.id}})
        const getIdMitra = Helper.toObject(result).id_mitra
        const pinjaman = await dbMitra.findAll({where: {id_mitra: getIdMitra, id_borrower : req.params.id_borrower}})
        const allPayments = await dbPayment.findAll({where: {id_borrower: req.params.id_borrower}, order : [["createdAt" , "ASC"] ]})
        const userPayments = await dbUserPayment.findOne({where: {id_borrower: req.params.id_borrower}})
        return res.status(200).send({pinjaman : pinjaman , "payment history" : allPayments, description : userPayments})
    } catch (err) {
        console.log(err);
        return res.status(400).send({status : err})
    }
}

const paymentData = async (req, res) => {
    try {
        let getIdBorrower
        let getIdMitra
        //get id mitra 
        if (req.role === 'mitra') {
            const result = await dbProfileMitra.findOne({where: {id_user: req.id}})
            getIdMitra = Helper.toObject(result).id_mitra
            await dbMitra.findAll({where: {id_mitra: getIdMitra, status : 'payment'}})
            .then(async data => {
                return res.status(200).send(data)
            })
        } else if (req.role === 'user') {
            const result1 = await dbBorrower.findAll({where: {id_user : req.id}, order : [["createdAt" , "DESC"] ]})
            .then(data => {
            return res.status(200).send({pinjaman : data})
            })
        }
    } catch (err) {
        console.log(err);
        return res.status(400).send(err)
    }
}

const paymentDataById = async (req, res) => {
    try {
        console.log(req.params.id_borrower);
        console.log(req.role);
        if (req.role === 'mitra') {
            const allPayments = await dbPayment.findAll({where: {id_borrower: req.params.id_borrower}})
            await dbUserPayment.findOne({where: {id_borrower: req.params.id_borrower}})
            .then(data => {
                return res.status(200).send({payment : allPayments, "total payment" : Helper.toObject(data).total_payment})
            })
            
        } else if (req.role === 'user') {
            // const result1 = await dbBorrower.findOne({where: {id_user : req.id}})
            // console.log(Helper.toObject(result1));
            // getIdBorrower = Helper.toObject(result1).id_borrower
            await dbPayment.findAll({where: {id_borrower: req.params.id_borrower}})
            .then(data => {
            return res.status(200).send({payment : data})
            })
        }
    } catch (err) {
        console.log(err);
        return res.status(400).send({message: err})
    }
}

const buktiBayarMessage = async (req, res) => {
    try {
        const AllMess = await db.message.findAll({where : {id_user: req.id , link_bukti : {[Op.not]: null} } ,order : [["createdAt", "DESC"]]})
            return res.status(200).send({message : AllMess})
    } catch (err) {
        console.log(err);
        return res.status(400).send({message: err})
    }
}

const creditDataById = async (req, res) => {
    try {
        console.log(req.params.id_borrower);
        await dbCredit.findOne({where: {id_borrower: req.params.id_borrower}})
        .then(data => {
            return res.status(200).send({data})
        })
    } catch (err) {
        console.log(err);
        return res.status(400).send(err)
    }
}

const creditData= async (req, res) => {
    try {
        console.log(req.params.id_borrower);
        await dbCredit.findAll()
        .then(data => {
            return res.status(200).send({data})
        })
    } catch (err) {
        console.log(err);
        return res.status(400).send(err)
    }
}

const messageData = async (req, res) => {
    try {
        await db.message.findAll({where: {id_user : req.id, has_read : false}})
        .then(data => {
            return res.status(200).send(data)
        })
    } catch (err) {
        console.log(err);
        return res.status(400).send({status : err})
    }
}

const summarryMitra = async (req, res) => {
    try {
        const data1 = await dbMitra.findAll({where:{id_mitra: req.params.id_mitra}});
        const objek = Helper.toObject(data1);
        console.log(objek);
        let pending = 0;
        let accepted = 0;
        let borrower = 0;
        let totalPayment = 0;
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
        const data2 = await dbPayment.findAll({where:{id_mitra: req.params.id_mitra}});
        const getPayment = Helper.toObject(data2)
        getPayment.forEach(data => {
            totalPayment = totalPayment + data.amount_payment
        });
        console.log(pending, accepted, borrower, totalPayment);
        const data = {
            pending : pending,
            accepted : accepted,
            borrower : borrower,
            totalPayment : totalPayment
        }
        return res.status(200).send(data);
        
    } catch (err) {
        console.log(err);
        return res.status(400).send({status : err})
    }
}

// const userPaymentData = async (req, res) => {
//     try {   
//         await dbUserPayment.findOne({where: {id_user: req.id}})
//         .then(data => {
//             return res.status(200).send({user_payment : data})
//         })
//     } catch (err) {
//         console.log(err);
//         return res.status(400).send(err)
//     }
// }
module.exports = {
    homeData,
    profileData,
    borrowerData,
    borrowerDatabyId,
    mitraProfileData,
    mitraData,
    mitraDataById,
    paymentData,
    paymentDataById,
    buktiBayarMessage,
    // userPaymentData,
    creditData,
    creditDataById,
    messageData,
    summarryMitra,
}