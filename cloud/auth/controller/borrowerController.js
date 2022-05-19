const db = require("../model/model");
const dbBorrower = db.borrower;
const dbMitra = db.mitra;
const dbProfile = db.profile;

const addBorrower = async (req, res, next) =>{
    let id_user = req.id;
    const obj = {
        id: 1,
        nama: "suhe",
        npm: 1906355522
    }
    
    const data_borrower = {
        loan : req.body.loan,
        reason_borrower : req.body.reason,
        dependents_amount : req.body.dependents,
        payment_id : req.body.payment,
        status : "Pending",
    }

    // const borrower = await dbBorrower.create(data_borrower)
    // .then(data => {
    //     console.log(data);
    //     console.log(data.dataValues.id);
    //     if (data) {
    //         let data1 = {
    //             name: obj.nama,
    //             reason_borrower: data.dataValues.reason_borrower,
    //             dependents_amount: data.dataValues.dependents_amount,
    //             status: data.dataValues.status
    //         }
    //         console.log(data1);
    //         const mitra = await dbMitra.create(data1);
    //     }
    //     res.status(200).send(data)
    // })
    // return{
    //     data: borrower
    // }

    const borrower = await dbBorrower.create(data_borrower);
    console.log(borrower.toJSON());
    const profile = await dbProfile.findOne({where: {id_users: id_user}});
    // console.log("All users:", JSON.stringify(profile));
    const objek = JSON.parse(JSON.stringify(profile));

    if (borrower.toJSON()){
        let data1 = {
            name: objek.nama_lengkap,
            reason_borrower: borrower.reason_borrower,
            dependents_amount: borrower.dependents_amount,
            status: borrower.status
        }
        console.log(data1);
        const mitradata = await dbMitra.create(data1);
        return res.status(200).send(mitradata);
    }
    return res.status(200).send(borrower);
    
}

module.exports = {
    addBorrower
}

