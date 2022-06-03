const db = require("../model/model");
const Helper = require("../middleware/helper");
const dbBorrower = db.borrower;
const dbMitra = db.mitra;
const dbProfile = db.profileUser;
const dbProfileMitra = db.profileMitra
const dbCredit = db.credit
const dbUser= db.users;
const Op = require('sequelize').Op;

const addBorrower = async (req, res, next) =>{
    
    try {
        let id_user = req.id;
        let pinjaman_ke; 
        //check peminjaman ke berapa
        await dbBorrower.findAll({where: {id_user:id_user , status: 'done' || 'pending'}})
        .then( data => {
            pinjaman_ke = data.length + 1;
        })
        const mitraList = await dbProfileMitra.findAll({attributes: ['id_mitra', 'partner_name']})
        console.log(Helper.toObject(mitraList));
        // code id mitra dibawah ini nantinya ditentukan dari hasil pilih oleh borrower
        let id_mitra = req.body.id_mitra || Helper.toObject(mitraList)[0].id_mitra; // INI DIPILIH OLEH USER UNTUK MENENTUKAN MITRA
        const data_borrower = {
            id_user: id_user,
            id_mitra ,
            loan_amount : req.body.loan_amount,
            reason_borrower : req.body.reason_borrower,
            monthly_income : req.body.monthly_income,
            dependents_amount : req.body.dependents_amount,
            id_payment : req.body.payment_id,
            payment_method : req.body.payment_method,
            tenor : req.body.tenor,
            pinjaman_ke ,
            telat : req.body.telat,
            donasi : req.body.donasi,
        }

        //check peminjaman aktif
        await dbBorrower.findAll({where: {id_user : id_user,status : { [Op.or] : ["pending", "accepted", "payment"]}}}) 
        .then ( async checkList => {
            console.log(checkList.length);
            if (checkList[0] !== undefined) {
                throw "you cannot request loan, please finish other transaction"
            } 
            const borrower = await dbBorrower.create(data_borrower);
            const profile = await dbProfile.findOne({where: {id_user: id_user}});
            const objek = Helper.toObject(profile);
            
            // make credit_score
            const creds = Helper.creditMaker(
                    objek.usia, 
                    borrower.loan_amount,
                    borrower.tenor,
                    borrower.monthly_income,
                    borrower.dependents_amount,
                    pinjaman_ke,
                    objek.profesi, 
                    borrower.donasi,
                )
            console.log(creds);
            const p = {
                id_borrower: borrower.id_borrower,
                id_user,
                id_mitra,
                usiakat : creds.usiaKat,
                econkat : creds.econCombineKat,
                profesikat : creds.profesiKat,
                pinjamankat : creds.pinjamanKeKat,
                donasikat : creds.donasiKat
            }
            console.log(p);
            
            if (borrower.toJSON()){
                let data1 = {
                    id_user,
                    id_mitra,
                    id_borrower: borrower.id_borrower,
                    nama_lengkap: objek.nama_lengkap,
                    usia : objek.usia,
                    phone : objek.phone,
                    gender : objek.gender,
                    profesi : objek.profesi,
                    credit_score : objek.credit_score,
                    loan_amount : borrower.loan_amount,
                    reason_borrower: borrower.reason_borrower,
                    monthly_income : borrower.monthly_income,
                    dependents_amount: borrower.dependents_amount,
                    status: borrower.status,
                    pinjaman_ke : borrower.pinjaman_ke,
                    telat : borrower.telat,
                    donasi : borrower.donasi,
                    tenor :borrower.tenor,
                }
                
                const hasilcreds = await dbCredit.create(p)
                const mitradata = await dbMitra.create(data1);
                return res.status(200).send({borrower, mitradata, hasilcreds});
            } else {
                throw err
            }
        })
    
    } catch (err) {
        console.log(err);
        return res.status(500).send({ message: err});
    }
}

const updateBorrower = async (req, res) => {
    try {
        let id_user = req.id;
        let data1 = {
            id_user,
            usia : req.body.usia,
            gender : req.body.gender,
            profesi : req.body.profesi,
            reason_borrower: req.body.reason_borrower,
            monthly_income : req.body.monthly_income,
            dependents_amount: req.body.dependents_amount,
            status: req.body.status,
            telat : req.body.telat,
            donasi : req.body.donasi,
        }

        await dbBorrower.findAll({where: {id_user: id_user , status : { [Op.or] : ["pending"]}}})
        .then(async data => {
            console.log(data[0].id_borrower);
            await dbBorrower.update(data1, {where: {id_borrower: data[0].id_borrower}})
                .then(async data2 => {
                    await dbMitra.update(data1,{where: {id_borrower: data[0].id_borrower}})
                    return res.status(200).send(data2)
            })
        })
        
    } catch (err) { 
        console.log(err);
        return res.status(400).send(err)
    }
}

module.exports = {
    addBorrower,
    updateBorrower
}

