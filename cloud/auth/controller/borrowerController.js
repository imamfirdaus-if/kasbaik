const db = require("../model/model");
const Helper = require("../middleware/helper");
const dbBorrower = db.borrower;
const dbMitra = db.mitra;
const dbProfile = db.profileUser;
const dbProfileMitra = db.profileMitra
const dbUser= db.users;

const addBorrower = async (req, res, next) =>{
    
    try {
        let id_user = req.id;

        const mitraList = await dbProfileMitra.findAll({attributes: ['id_mitra', 'partner_name']})
        console.log(Helper.toObject(mitraList));
        // code id mitra dibawah ini nantinya ditentukan dari hasil pilih oleh borrower
        let id_mitra = Helper.toObject(mitraList)[1].id_mitra || req.body.id_mitra;
        const data_borrower = {
            id_user: id_user,
            loan_amount : req.body.loan_amount,
            reason_borrower : req.body.reason_borrower,
            monthly_income : req.body.monthly_income,
            dependents_amount : req.body.dependents_amount,
            id_payment : req.body.payment_id,
            id_mitra 
        }

        const borrower = await dbBorrower.create(data_borrower);
        const profile = await dbProfile.findOne({where: {id_user: id_user}});
        const objek = Helper.toObject(profile);
    
        if (borrower.toJSON()){
            let data1 = {
                id_user,
                id_mitra,
                id_borrower: borrower.id_borrower,
                nama_lengkap: objek.nama_lengkap,
                reason_borrower: borrower.reason_borrower,
                monthly_income : borrower.monthly_income,
                dependents_amount: borrower.dependents_amount,
                status: borrower.status
            }
            console.log(data1);
            const mitradata = await dbMitra.create(data1);
            return res.status(200).send(mitradata);
        } else {
            throw err
        }
        
    } catch (err) {
        console.log(err);
        return res.status(500).send(err);
    }
    
    
}
module.exports = {
    addBorrower
}

