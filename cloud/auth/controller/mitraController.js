const db = require('../model/model')
const dbProfileMitra = db.profileMitra
const dbMitra = db.mitra
const dbBorrower = db.borrower
const Helper = require('../middleware/helper')

// this function is for updating the profile of mitra tables
const profileMitra_post = async (req, res, next) => {
    
    const profileMitra = {  
    partner_name : req.body.partner_name,
    location_mitra : req.body.location_mitra,
    phone : req.body.phone,
    }

    try {
        await dbProfileMitra.update(profileMitra, {where: {id_user: req.id}})
        .then(data => {
            if (data[0] !== 0) {
                console.log("data berhasil dimasukkan");
            }else {
                console.log('data gagal diupdate');
                return res.status(404).send('gagal diupdate')
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
        const getIdMitra = Helper.toObject(result).id

        await dbMitra.findAll({where: {id_mitra: getIdMitra}})
        .then(data => {
            const data1 = {
                status : req.body.status
            }
            dbMitra.update(data1,{where: {id_borrower: req.body.id_borrower}})
            .then(data2 => {
                dbBorrower.update(data1,{where: {id_borrower: req.body.id_borrower}})
                .then(data3 => {
                    return res.status(200).send(data3)
                })
            })

            
        })


    } catch (err) {
        
    }
}

module.exports = {
    profileMitra_post,
    seeAllBorrowers,
    editStatusBorrower
}