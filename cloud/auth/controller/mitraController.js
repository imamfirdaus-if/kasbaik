const db = require('../model/model')
const dbProfileMitra = db.profileMitra

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

module.exports = {
    profileMitra_post
}