const db = require('../model/model')
const Helper = require('../middleware/helper')
const dbUser = db.users
const dbProfile = db.profileUser
const dbBorrower = db.borrower
const dbProfileMitra = db.profileMitra
const dbMitra = db.mitra

const userData = async (req, res) => {
    try {
        await dbUser.findOne({where: {id_user : req.id}})
        .then(data => {
            return res.status(200).send(data)
        })

    } catch (err) {
        return res.status(403).send(err)
    }
}

const profileData = async (req, res) => {
    try {
        console.log(req.role);
        if (req.role === 'user') {
            await dbProfile.findOne({where: {id_user : req.id}})
            .then(data => {
                return res.status(200).send(data)
            })
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
        await dbBorrower.findAll({where: {id_user : req.id}})
        .then(data => {
            return res.status(200).send(data)
        })
    } catch (err) {
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
        const result = await dbProfileMitra.findOne({where: {id_user: req.id}})
        
        const getIdMitra = Helper.toObject(result).id_mitra

        await dbMitra.findAll({where: {id_mitra: getIdMitra}})
        .then(data => {
            return res.status(200).send(data)
        })
       
    } catch (err) {
        return res.status(403).send(err)
    }
}

module.exports = {
    userData,
    profileData,
    borrowerData,
    mitraProfileData,
    mitraData
}