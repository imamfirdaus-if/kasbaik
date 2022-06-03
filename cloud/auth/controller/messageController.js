
const db = require("../model/model"); 
const Helper = require('../middleware/helper')
const updateMess = async (req, res, next) => {
    try {
        console.log(req.id);
        data0 = {
            has_read :  true
        }
        const updateMess = await db.message.update( data0 ,{where : {id_user: req.id , id_message: req.params.id_message}})
        const lihatMess = await db.message.findOne({where : {id_user: req.id , id_message: req.params.id_message}})
        return res.status(200).send({hasilUpdate : updateMess , message : lihatMess})
    } catch (err) {
        console.log(err);
         return res.status(400).send({status : err})
    }
}

const showAllMess = async (req, res, next) => {
    try {
        const AllMess = await db.message.findAll({where : {id_user: req.id} ,order : [["createdAt", "DESC"]]})
        return res.status(200).send({message : AllMess})
    } catch (err) {
        console.log(err);
        return res.status(400).send({status : err})
    }
}

const createMessagetoMitra = async (req, res, next) => {
    try {
        const seePayment = await db.borrower.findOne({where : {id_user : req.id, status : 'payment'}})
        console.log(Helper.toObject(seePayment));
    } catch (err) {
        console.log(err);
        return res.status(400).send({status : err})
    }
}

const seeMessagetoMitra = async (req, res) => {
    try {
        
    } catch (err) {
        console.log(err);
        return res.status(400).send({status : err})
    }
}
module.exports = {
    updateMess,
    showAllMess,
    createMessagetoMitra,
    seeMessagetoMitra
}