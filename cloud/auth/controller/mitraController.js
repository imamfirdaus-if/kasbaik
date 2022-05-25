const db = require('../model/model')
const dbProfileMitra = db.profileMitra
const dbMitra = db.mitra
const dbBorrower = db.borrower
const dbPayment = db.payment
const Helper = require('../middleware/helper')
const processFile = require("../middleware/upload");
const { format } = require("util");
const { Storage } = require("@google-cloud/storage");
const { get } = require('http')
const { send } = require('process')
const Op = require('Sequelize').Op;

//inisiasi storage client with credentials
const storage = new Storage({ keyFilename: "google-cloud-key.json" });
// const storage = new Storage();
const bucket= storage.bucket("nyoba_project");

// this function is for updating the profile of mitra tables
const profileMitra_post = async (req, res, next) => {
    try {
        await processFile(req, res);
        console.log(req.files[0].originalname);
        if (req.files === undefined) {
            res.status(400)
            throw "tidak ada file yang di upload";
        }
        let id_user = req.id;
        
        const blob = bucket.file(req.files[0].originalname.replace("", `data ${id_user}` ));  
        const blobStream = blob.createWriteStream({
            resumable: false,
        });
        blobStream.on("error", (err) => {
            res.status(500).send({ message: err.message });
        });
        const publicUrl = `https://storage.googleapis.com/${bucket.name}/${blob.name}`
        blobStream.end(req.files[0].buffer);
        
        const profileMitra = {  
            partner_name : req.body.partner_name,
            location_mitra : req.body.location_mitra,
            phone : req.body.phone,
            foto_profile : publicUrl
        }

        await dbProfileMitra.update(profileMitra, {where: {id_user: req.id}})
        .then(data => {
            if (data[0] !== 0) {
                console.log("data berhasil dimasukkan");
            }else {
                console.log('data gagal diupdate');
                return res.status(404).send({ message: 'data gagal diupdate, user tidak ditemukan'})
            }
        })
        
        await dbProfileMitra.findAll({where: {id_user: req.id}})
            .then(data => {
            if (data[0] === undefined) {
                return res.status(404)
            }
            return res.status(200).send(data[0].toJSON())
            })
         
    } catch (err) {
        console.log(err);
        return res.status(403).send(err)
    }
}

const seeAllBorrowers = async (req, res) => {
    try {
        console.log(req.role);
        const result = await dbProfileMitra.findOne({where: {id_user: req.id}})
        const getIdMitra = Helper.toObject(result).id

        await dbMitra.findAll({where: {id_mitra: getIdMitra}})
        .then(data => {
            console.log(data);
            return res.status(200).send(data)
        })
        
    } catch (err) {
        return res.status(500).send(err)
    }
}

const editStatusBorrower = async (req, res) => {
    try {
        const result = await dbProfileMitra.findOne({where: {id_user: req.id}})
        const getIdMitra = Helper.toObject(result).id_mitra

        await dbMitra.findAll({where: {id_mitra: getIdMitra}})
        .then(data => {
            res.send(data)
            const getIdBorrower = Helper.toObject(data[0]).id_borrower // INI DIPILIH OLEH MITRA DARI DROPDOWN
            const data1 = {
                status : req.body.status
            }
            // mengupdate status pada table mitras
            dbMitra.update(data1,{where: {id_borrower: { [Op.or] : [req.body.id_borrower, getIdBorrower]}}})
            .then(data2 => {
                // mengupdate status pada table borrower
                dbBorrower.update(data1,{where: {id_borrower: req.body.id_borrower}})
                .then(data3 => {
                    return res.status(200).send({tabelmitra: data2, tabelborrower: data3});
                })
            })

            
        })


    } catch (err) {
        console.log(err.message)
        return res.status(404).send(err);
    }
}

const createPayment = async (req, res) => {
    try {
        const result = await dbProfileMitra.findOne({where: {id_user: req.id}})
        const getIdMitra = Helper.toObject(result).id_mitra
        await dbMitra.findAll({where: {id_mitra: getIdMitra, status: 'payment'}})
        .then(data => {
            console.log(Helper.toObject(data));
            const objek = Helper.toObject(data[1]) // INI DIPILIH OLEH MITRA
            dataPayment = {
                id_mitra: getIdMitra,
                payment_method: req.body.payment_method,
                amount_payment : req.body.amount_payment,
                id_borrower : req.body.id_borrower || objek.id_borrower// ini nanti dipilih oleh mitra dari hasil click
            }
            if (data[0] !== undefined){
                dbPayment.create(dataPayment)
                .then((data) =>{
                    return res.status(200).send({tablePayment: data})
                })
            }
        })
        
        // await dbPayment.create(dataPayment)
    } catch (err) {
        console.log(err);
        return res.status(400).send(err)
    }
}

module.exports = {
    profileMitra_post,
    seeAllBorrowers,
    editStatusBorrower,
    createPayment,
}