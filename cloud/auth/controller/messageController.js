
const db = require("../model/model"); 
const Helper = require('../middleware/helper')
const processFile = require("../middleware/upload");
const { format } = require("util");
const { Storage } = require("@google-cloud/storage");
const { where } = require("sequelize");
const Op = require('sequelize').Op;
//inisiasi storage client with credentials
const storage = new Storage({ keyFilename: "kasbaik-credentials.json" });
// const storage = new Storage();
const bucket= storage.bucket("nyoba_project_bucket");

const updateMess = async (req, res, next) => {
    try {
        console.log(req.id);
        data0 = {
            has_read :  true
        }
        if ( req.role == "user" ) {
            const updateMess = await db.message.update( data0 ,{where : {id_user: req.id , id_message: req.params.id_message}})
            const lihatMess = await db.message.findOne({where : {id_user: req.id , id_message: req.params.id_message}})
            return res.status(200).send({hasilUpdate : updateMess , message : lihatMess})
        } else if ( req.role == "mitra") {
            const result = await  db.profileMitra.findOne({where: {id_user: req.id} })
            const id_mitra = Helper.toObject(result).id_mitra
            const updateMess = await db.message.update(data0 , {where : {id_mitra: id_mitra, id_message : req.params.id_message}})
            const lihatMess = await db.message.findOne({where : {id_mitra: id_mitra , id_message: req.params.id_message}})
            return res.status(200).send({hasilUpdate : updateMess , message : lihatMess})
        }
        
    } catch (err) {
        console.log(err);
         return res.status(400).send({status : err})
    }
}

const showAllMess = async (req, res, next) => {
    try {
        if( req.role == 'user') {
            const AllMess = await db.message.findAll({where : {id_user: req.id} ,order : [["createdAt", "DESC"]]})
            return res.status(200).send({message : AllMess})
        } else if (req.role == 'mitra') {
            const result = await  db.profileMitra.findOne({where: {id_user: req.id} })
            const id_mitra = Helper.toObject(result).id_mitra
            const allMess = await db.message.findAll({where : {id_mitra: id_mitra , link_bukti : {[Op.not]: null}} ,order : [["createdAt", "DESC"]]})
            return res.status(200).send({message : allMess})
        }
        
    } catch (err) {
        console.log(err);
        return res.status(400).send({status : err})
    }
}

const createMessagetoMitra = async (req, res, next) => {
    try {
        await processFile(req, res);
        console.log(req.files[0].originalname);
        if (req.files === undefined) {
            res.status(400)
            throw "tidak ada file yang di upload";
        }
        let id_user = req.id;
        
        let changeName1 = req.files[0].originalname.replace("", `bukti_link${id_user}` )
        let changeName2 = changeName1.replace(" ", '-')
        const blob = bucket.file(changeName2);  
        
        const blobStream = blob.createWriteStream({
            resumable: false,
        });
        blobStream.on("error", (err) => {
            res.status(500).send({ message: err.message });
        });
        const publicUrl = `https://storage.googleapis.com/${bucket.name}/${blob.name}`
        blobStream.end(req.files[0].buffer);

        const seePayment = await db.borrower.findOne({where : {id_user : req.id, status : 'payment'}})
        const objek = Helper.toObject(seePayment)
        if (seePayment === null) {
            throw "tidak ada payment aktif"
        }
        data = {
            id_user : req.id,
            id_mitra : objek.id_mitra,
            id_borrower : objek.id_borrower,
            message : req.body.message,
            link_bukti : publicUrl,
            nominal : req.body.nominal
        }
        console.log(data);
        const resultSent = await db.message.create(data);
        return res.status(200).send({message : resultSent});
    } catch (err) {
        console.log(err);
        return res.status(400).send({message : err})
    }
}

const handleMessagebyMitra = async (req, res) => {
    try {
        
        let data0 = {
            has_read : true,
            isAccepted : req.body.isAccepted
        }
        const hasilUpdate = await db.message.update(data0, {where : {id_message : req.params.id_message}})
        const hasilFindOne = await db.message.findOne({where : {id_message : req.params.id_message}})
        let objek = Helper.toObject(hasilFindOne)
        let data1 = {
            id_user : objek.id_user,
            id_mitra : objek.id_mitra,
            id_borrower : objek.id_borrower,
            message : objek.message,
            has_read : objek.has_read,
            link_bukti : objek.link_bukti,
            nominal : objek.nominal,
            isAccepted : objek.isAccepted
        }
        const createMessage = await db.message.create(data1)
        return res.status(201).send({"hasil update" : hasilUpdate, "message" : createMessage })
    } catch (err) {
        console.log(err);
        return res.status(400).send({status : err})
    }
}

// const seeMessagetoMitra = async (req, res) => {
//     try {
//         const result = await db.profileMitra.findOne({where: {id_user: req.id}})
//         const getIdMitra = Helper.toObject(result).id_mitra
//         const tableMessage = await db.message.findAll({where: {id_mitra : getIdMitra}},{order : [["createdAt" , "DESC"] ]})
//         return res.status(200).send({
//             message : tableMessage
//         })
//     } catch (err) {
//         console.log(err);
//         return res.status(400).send({status : err})
//     }
// }

// const seeMessagetoMitraById = async (req, res) => {
//  try {
//     const result = await db.profileMitra.findOne({where: {id_user: req.id}})
//     const getIdMitra = Helper.toObject(result).id_mitra
//     const tableMessage = await db.message.findOne({where: {id_mitra : getIdMitra, id_message : req.params.id_message}})
//     return res.status(200).send({
//         message : tableMessage
//     })
//  } catch (err) {
//      console.log(err);
//      return res.status(400).send({status : err})
//  }
// }



module.exports = {
    updateMess,
    showAllMess,
    createMessagetoMitra,
    // seeMessagetoMitra,
    // seeMessagetoMitraById,
    handleMessagebyMitra
}