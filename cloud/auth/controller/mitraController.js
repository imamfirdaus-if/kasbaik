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
const storage = new Storage({ keyFilename: "google-key-cloud.json" });
// const storage = new Storage();
const bucket= storage.bucket("kasbaik-project");

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
        
        let changeName1 = req.files[0].originalname.replace("", `mitra_prof${id_user}` )
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
            let message = req.body.message
            
            //mendapatkan informasi tabel mitra berdasarkan id borrower
            const hasilMitra = await dbMitra.findOne({where: {id_borrower: req.params.id_borrower}})
            const objek = Helper.toObject(hasilMitra)
            const data1 = {
                status : req.body.status
            }
            // mengupdate status pada table mitras
            dbMitra.update(data1,{where: {id_borrower: req.params.id_borrower}})
            .then(async data2 => {
                // mengupdate status pada table borrower
                let updateBorrower = await dbBorrower.update(data1,{where: {id_borrower: req.params.id_borrower}})
                console.log(data2);
                //membuat table user_payments if status updated to 'payment'
                let total_payment;
                let createUserPay;
                let target_lunas = moment().add(objek.tenor, 'weeks').format('YYYY-MM-DD');
                console.log(req.body.message);
                if (message === undefined) {
                    message =""
                }
                let dataCreateUserPay = {
                    id_borrower : objek.id_borrower,
                    id_user : objek.id_user,
                    id_mitra : objek.id_mitra,
                    loan_amount : objek.loan_amount,
                    total_payment,
                    target_lunas,
                    message : `your status is changed to ${data1.status}. ` + message
                }
                if (data1.status === 'payment'){
                    createUserPay = await db.userPayment.create(dataCreateUserPay)
                }
                let createMessage = await db.message.create(dataCreateUserPay)
                return res.status(200).send({tabelmitra: data2, tabelborrower: updateBorrower, user_payment : createUserPay, message : createMessage});
            })
        })


    } catch (err) {
        console.log(err)
        return res.status(404).send(err);
    }
}

const createPaymentById = async (req, res) => {
    try {
        let isLunas = "belum lunas;"
        let payment_ke ;
        
        const result = await dbProfileMitra.findOne({where: {id_user: req.id}})
        const getIdMitra = Helper.toObject(result).id_mitra
        await dbMitra.findAll({where: {id_mitra: getIdMitra, status: 'payment'}})
        .then(async data => {
            console.log(Helper.toObject(data));
            const seeDbPayment = await dbPayment.findAll ({where: {id_borrower: req.params.id_borrower}})
            .then(data => {
                payment_ke = Helper.toObject(data).length + 1
            })
            
            dataPayment = {
                id_mitra: getIdMitra,
                payment_method: req.body.payment_method,
                amount_payment : req.body.amount_payment,
                payment_ke : payment_ke,
                id_borrower : req.params.id_borrower// ini nanti dipilih oleh mitra dari hasil click
            }
            if (data[0] === undefined){
                throw "data tidak ditemukan"
            }
            
            const lookUserPay = await dbUserPayment.findOne({where: {id_borrower: dataPayment.id_borrower}})
            const hasilPayment = await dbPayment.create(dataPayment).catch(err => {return res.send({error: err})} )
            //create message to user peminjam
            

            let tempTotalPay = lookUserPay.dataValues.total_payment + hasilPayment.amount_payment
            let objekxx = {
                total_payment: tempTotalPay
            }
            const updateUserPay = await dbUserPayment.update(objekxx,{where: {id_borrower: hasilPayment.id_borrower}})
            console.log(updateUserPay);
            console.log(tempTotalPay);
            let message =`Your ${payment_ke}th payment has been verified.` ;
            //untuk melihat apakah sudah lunas atau bel um
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
                isLunas = "Sudah Lunas"
                message = message + " Peminjaman kamu telah lunas, Selamat! "
            }
            let dataMessage = {
                id_user : Helper.toObject(lookUserPay.id_user),
                id_borrower : dataPayment.id_borrower,
                message : message
            }
            let createMessage = await db.message.create(dataMessage)
            return res.status(200).send({tablePayment: hasilPayment , status: isLunas, message: createMessage})
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
    createPaymentById,
}