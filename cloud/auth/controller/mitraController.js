const db = require('../model/model')
const dbProfileMitra = db.profileMitra
const dbMitra = db.mitra
const dbBorrower = db.borrower
const dbPayment = db.payment
const dbUserPayment = db.userPayment
const dbCredit = db.credit
const Helper = require('../middleware/helper')
const processFile = require("../middleware/upload");
const { format } = require("util");
const { Storage } = require("@google-cloud/storage");
const { get } = require('http')
const { send } = require('process')
const Op = require('sequelize').Op;
const moment = require('moment')

//inisiasi storage client with credentials
const storage = new Storage({ keyFilename: "kasbaik-credentials.json" });
// const storage = new Storage();
const bucket= storage.bucket("nyoba_project_bucket");

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
        return res.status(403).send({ message: err})
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
        .then(async data => {
            
            const getIdBorrower = req.body.id_borrower || Helper.toObject(data[0]).id_borrower // INI DIPILIH OLEH MITRA DARI DROPDOWN
            //mendapatkan informasi tabel mitra berdasarkan id borrower
            const hasilMitra = await dbMitra.findOne({where: {id_borrower: getIdBorrower}})
            const objek = Helper.toObject(hasilMitra)
            const data1 = {
                status : req.body.status
            }
            // mengupdate status pada table mitras
            dbMitra.update(data1,{where: {id_borrower: getIdBorrower}})
            .then(async data2 => {
                // mengupdate status pada table borrower
                let updateBorrower = await dbBorrower.update(data1,{where: {id_borrower: getIdBorrower}})
                console.log(data2);
                //membuat table user_payments if status updated to 'payment'
                let total_payment;
                let createUserPay;
                let target_lunas = moment().add(objek.tenor, 'weeks').format('YYYY-MM-DD');
                let dataCreateUserPay = {
                    id_borrower : objek.id_borrower,
                    id_mitra : objek.id_mitra,
                    id_user : objek.id_user,
                    loan_amount : objek.loan_amount,
                    total_payment,
                    target_lunas
                }
                if (data1.status === 'payment'){
                    createUserPay = await db.userPayment.create(dataCreateUserPay)
                }
                return res.status(200).send({tabelmitra: data2, tabelborrower: updateBorrower, user_payment : createUserPay});
            })

            
        })


    } catch (err) {
        console.log(err)
        return res.status(404).send(err);
    }
}

const createPayment = async (req, res) => {
    try {
        const result = await dbProfileMitra.findOne({where: {id_user: req.id}})
        const getIdMitra = Helper.toObject(result).id_mitra
        await dbMitra.findAll({where: {id_mitra: getIdMitra, status: 'payment'}})
        .then(async data => {
            console.log(Helper.toObject(data));
            // const objek = Helper.toObject(data[1]) // INI DIPILIH OLEH MITRA
            dataPayment = {
                id_mitra: getIdMitra,
                payment_method: req.body.payment_method,
                amount_payment : req.body.amount_payment,
                id_borrower : req.body.id_borrower || objek.id_borrower// ini nanti dipilih oleh mitra dari hasil click
            }
            if (data[0] === undefined){
                throw "data tidak ditemukan"
            }
            
            const lookUserPay = await dbUserPayment.findOne({where: {id_borrower: dataPayment.id_borrower}})
            
            await dbPayment.create(dataPayment)
            .then(async data1 => {
                let tempTotalPay = lookUserPay.dataValues.total_payment + data1.amount_payment
                let objekxx = {
                    total_payment: tempTotalPay
                }
                const updateUserPay = await dbUserPayment.update(objekxx,{where: {id_borrower: data1.id_borrower}})
                console.log(updateUserPay);
                console.log(tempTotalPay);
                
                res.status(200).send({tablePayment: data1})
            })
            const lookUserPay1 = await dbUserPayment.findOne({where: {id_borrower: dataPayment.id_borrower}})
            if (lookUserPay1.dataValues.total_payment >= lookUserPay1.dataValues.loan_amount){
                let status1 = {
                    status : 'done'
                }
                const now  = moment().format('YYYY-MM-DD HH')
                let diff = moment(now).diff(lookUserPay1.dataValues.createdAt, 'days');
                let dif = Helper.convertTelat(diff)
                let p = {
                    telatkat : dif
                }
                
                const c1= await dbMitra.update(status1, {where : {id_borrower : dataPayment.id_borrower}})
                const c2 =await dbBorrower.update(status1, {where : {id_borrower : dataPayment.id_borrower}})
                const c3 = await dbCredit.update(p, {where : {id_borrower : dataPayment.id_borrower}})
                
                console.log(diff, c1, c2, c3);
                console.log('lunas');
            }
            return 
        })
        
        // await dbPayment.create(dataPayment)
    } catch (err) {
        console.log(err);
        return res.status(400).send({ message : err})
    }
}

module.exports = {
    profileMitra_post,
    seeAllBorrowers,
    editStatusBorrower,
    createPayment,
}