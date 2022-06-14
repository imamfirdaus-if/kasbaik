const db = require("../model/model");
const Helper = require("../middleware/helper");
const { where } = require("sequelize");
const { get } = require("../router/router");
const { users } = require("../model/model");
const dbBorrower = db.borrower;
const dbMitra = db.mitra;
const dbProfile = db.profileUser;
const dbProfileMitra = db.profileMitra
const dbCredit = db.credit
const dbUser= db.users;
const dbPayment = db.payment;
const dbUserPayment = db.userPayment;
const Op = require('sequelize').Op;

const listAkunUser = async (req, res) => {
    try {
        const user = await dbUser.findAll({where: {role: 'user'}})
        let data = []
        let proms = new Promise((resolve, reject) => {
            user.forEach(  async (p) => {
                const dataProfile = await dbProfile.findOne({where: {id_user : p.id_user}})
                const peminjaman = await dbBorrower.findAll({where: {id_user : p.id_user, status : {[Op.not]: ["pending", "rejected"]}}})
                data1 = {
                    createdAt: p.createdAt,
                    email: p.email,
                    id_user: p.id_user,
                    password: p.password,
                    phone: p.phone,
                    role: p.role,
                    updatedAt: p.updatedAt,
                    username: p.username,
                    profile: dataProfile,
                    peminjaman : peminjaman.length,
                }
                data.push(data1)
            })
            
            setTimeout(() => {
                resolve()
            },500) 
        })   
        proms.then(() => {
            return res.status(200).send(data)
        }) 
        
        
    } catch (err) {
        console.log(err);
        return res.status(400).send({status : err})
    }
}

const listAkunMitra = async (req, res) => {
    try {
        const query= `SELECT * FROM users FULL OUTER JOIN profile_mitras ON users.id_user = profile_mitras.id_user WHERE role = 'mitra'`; 
        const User = await db.sequelize.query(query)
        const User1 = Helper.toObject(User[0])
        let temp=[]

        let proms = new Promise((resolve, reject) => {
            User1.forEach( async (p) => {
                const query0 = `SELECT * FROM mitras WHERE id_mitra = '${p.id_mitra}'`
                const hasil = await db.sequelize.query(query0)
                const hasil1 = Helper.toObject(hasil[0])
                const query1 = `SELECT DISTINCT id_user FROM mitras WHERE id_mitra = '${p.id_mitra}'`
                const hasiluser = await db.sequelize.query(query1)
                const hasiluser1 = Helper.toObject(hasiluser[0])
                const query2 = `SELECT * FROM payments WHERE id_mitra = '${p.id_mitra}'`
                const hasilpayment = await db.sequelize.query(query2)
                const hasilpayment1 = Helper.toObject(hasilpayment[0])
                data1 = {
                    createdAt: p.createdAt,
                    email: p.email,
                    foto_profile: p.foto_profile,
                    id_mitra: p.id_mitra,
                    id_user: p.id_user,
                    location_mitra: p.location_mitra,
                    partner_name: p.partner_name,
                    password: p.password,
                    phone: p.phone,
                    role: p.role,
                    updatedAt: p.updatedAt,
                    username: p.username,
                    peminjaman : hasil1,
                    totaluser : hasiluser1.length,
                    payment : hasilpayment1
                }
                console.log('temp di foreach');
                temp.push(data1)
            })
            setTimeout(() => {
                resolve()
            },500)
            
        })
        
        proms.then(() => {
            console.log('temp di proms' , temp);
            return res.status(200).send({mitra : temp})
            
        }) 
        
        
    } catch (err) {
        console.log(err);
        return res.status(400).send({status : err})
    }
}

const listDetailUser = async (req, res) => {
    try {
        const query= `SELECT * FROM profiles where id_user = '${req.params.id_user}'`; 
        const query1 = `SELECT * FROM messages where id_user = '${req.params.id_user}'`; 
        console.log(query);
        const User = await db.sequelize.query(query)
        const User1 = await db.sequelize.query(query1)
        const objek = Helper.toObject(User[0])
        const objek1 = Helper.toObject(User1[0])
        console.log(Helper.toObject(User[0][0]));
        return res.status(200).send({
            profiles: objek,
            messages : objek1, 
        })
        
    } catch (err) {
        console.log(err);
        return res.status(400).send({status : err})
    }
}

const listDetailMitra = async (req, res) => {
    try {
        const user = await dbUser.findOne({where : {id_user : req.params.id_user}});
        const profile = await dbProfileMitra.findOne({where: {id_user : req.params.id_user}});
        const objek = Helper.toObject(profile);
        console.log(objek);
        const mitra = await dbMitra.findAll({where: {id_mitra: objek.id_mitra}});
        const payment = await dbPayment.findAll({where : {id_mitra: objek.id_mitra}});
        const userPayment = await dbUserPayment.findAll({where : {id_mitra: objek.id_mitra}});
        return res.status(200).send({ 
            "User Mitra" : user,
            profile : objek,
            mitra : mitra,
            payment : payment,
            userPayment : userPayment,
        })
        
    } catch (err) {
        console.log(err);
        return res.status(400).send({status : err})
    }
}

const listBorrower = async (req, res) => {
    try {
        const query0= `SELECT * FROM user_payments FULL OUTER JOIN mitras ON mitras.id_borrower = user_payments.id_borrower FULL OUTER JOIN profiles ON mitras.id_user = profiles.id_user FULL OUTER JOIN users ON profiles.id_user = users.id_user WHERE mitras.id_borrower IS NOT NULL `; 
        const peminjaman = await db.sequelize.query(query0)
        const peminjaman1 = Helper.toObject(peminjaman[0])

        return res.status(200).send({peminjaman : peminjaman1})
        
    } catch (err) {
        console.log(err);
        return res.status(400).send({message: err})
    }
}


const listBorrowerPending = async (req, res) => {
    try {
        await dbMitra.findAll({where: {status : 'pending'}})
        .then(async data => {
            return res.status(200).send(data);
        })

    } catch (err) {
        console.log(err);
        return res.status(400).send({status : err})
    }
}

const listBorrowerAcc = async (req, res) => {
    try {
        await dbMitra.findAll({where: {status : { [Op.or] : ["accepted", "payment"]}}})
        .then(async data => {
            return res.status(200).send(data);
        })

    } catch (err) {
        console.log(err);
        return res.status(400).send({status : err})
    }
}

const listBorrowerHistory = async (req, res) => {
    try {
        await dbMitra.findAll({where: {status : 'done'}})
        .then(async data => {
            return res.status(200).send(data);
        })

    } catch (err) {
        console.log(err);
        return res.status(400).send({status : err})
    }
}


const listDetailBorrower = async (req, res) => {
    try {
        const query0= `SELECT * FROM user_payments FULL OUTER JOIN mitras ON mitras.id_borrower = user_payments.id_borrower FULL OUTER JOIN profiles ON mitras.id_user = profiles.id_user FULL OUTER JOIN users ON profiles.id_user = users.id_user WHERE mitras.id_borrower IS NOT NULL AND mitras.id_borrower = '${req.params.id_borrower}'`; 
        const peminjaman = await db.sequelize.query(query0)
        const peminjaman1 = Helper.toObject(peminjaman[0][0])
        
        const query1= `SELECT * FROM payments FULL OUTER JOIN mitras ON mitras.id_borrower = payments.id_borrower FULL OUTER JOIN user_payments ON mitras.id_borrower = user_payments.id_borrower WHERE payments.id_payment IS NOT NULL AND mitras.id_borrower = '${req.params.id_borrower}' `; 
        const pembayaran = await db.sequelize.query(query1)
        const pembayaran1 = Helper.toObject(pembayaran[0])
        return res.status(200).send({peminjaman : peminjaman1, pembayaran: pembayaran1});
        
    } catch (err) {
        console.log(err);
        return res.status(400).send({status : err})
    }
}

const listPayment = async (req, res) => {
    try {
        const data1 = await dbUserPayment.findAll();
        return res.status(200).send(data1);
        
    } catch (err) {
        console.log(err);
        return res.status(400).send({status : err})
    }
}

const listPaymentbyId = async (req, res) => {
    try {
        console.log(req.params.id_payment);
        const query1= `SELECT * FROM payments FULL OUTER JOIN mitras ON mitras.id_borrower = payments.id_borrower FULL OUTER JOIN user_payments ON mitras.id_borrower = user_payments.id_borrower WHERE payments.id_payment = '${req.params.id_payment}' `; 
        const pembayaran = await db.sequelize.query(query1)
        const pembayaran1 = Helper.toObject(pembayaran[0])

        const getPayment = await db.payment.findOne({where :{id_payment : req.params.id_payment ,order : [["createdAt" , "DESC"] ]}})
        let data = []
        let proms = new Promise( (resolve, reject) => {
            getPayment.forEach( async (p) => {
                totalPayment = totalPayment + p.amount_payment
                const mitras = await db.profileMitra.findOne({where: {id_mitra : p.id_mitra}})
                const users = await db.mitra.findOne({where: {id_borrower : p.id_borrower}})
                let data1 = {
                    "id_payment": p.id_payment,
                    "id_mitra": p.id_mitra,
                    "id_borrower": p.id_borrower,
                    "payment_method": p.payment_method,
                    "amount_payment": p.amount_payment,
                    "payment_ke": p.payment_ke,
                    "createdAt": p.createdAt,
                    "updatedAt": p.updatedAt,
                    user : users.nama_lengkap,
                    mitra : mitras
                }
                data.push(data1)
            });
            setTimeout(() => {
                resolve()
            },500)
        })
        proms.then(() => {
            return res.status(200).send({pembayaran : pembayaran1, payment : data })
            
        }) 
        
        
    } catch (err) {
        console.log(err);
        return res.status(400).send({status : err})
    }
}

const summarry = async (req, res) => {
    try {
        const data1 = await dbMitra.findAll();
        const objek = Helper.toObject(data1);
        console.log(objek);
        let pending = 0;
        let accepted = 0;
        let borrower = 0;
        for (let i = 0; i < objek.length; i++){
            if (objek[i].status === "pending"){
                pending++;
                borrower++;
            }
            else if (objek[i].status === "accepted" || objek[i].status === "payment"){
                accepted++;
                borrower++;
            }
        }
        console.log(pending, accepted, borrower);
        const data = {
            pending : pending,
            accepted : accepted,
            borrower : borrower,
        }
        return res.status(200).send(data);
        
    } catch (err) {
        console.log(err);
        return res.status(400).send({status : err})
    }
}

const summarryMitra = async (req, res) => {
    try {
        const data1 = await dbMitra.findAll({where:{id_mitra: req.params.id_mitra}});
        const objek = Helper.toObject(data1);
        console.log(objek);
        let pending = 0;
        let accepted = 0;
        let borrower = 0;
        let totalPayment = 0;
        for (let i = 0; i < objek.length; i++){
            if (objek[i].status === "pending"){
                pending++;
                borrower++;
            }
            else if (objek[i].status === "accepted" || objek[i].status === "payment"){
                accepted++;
                borrower++;
            }
        }
        const data2 = await dbPayment.findAll({where:{id_mitra: req.params.id_mitra}});
        const getPayment = Helper.toObject(data2)
        getPayment.forEach(data => {
            totalPayment = totalPayment + data.amount_payment
        });
        console.log(pending, accepted, borrower, totalPayment);
        const data = {
            pending : pending,
            accepted : accepted,
            borrower : borrower,
            totalPayment : totalPayment
        }
        return res.status(200).send(data);
        
    } catch (err) {
        console.log(err);
        return res.status(400).send({status : err})
    }
}

const jumlahTotal = async (req, res) => {
    try {
        let totalPayment = 0
        const getUser = await db.users.findAll({where : {role : 'user'}})
        const getMitra = await db.users.findAll({where : {role : 'mitra'}})
        const getBorrower = await db.borrower.findAll()
        // const query= `SELECT * FROM mitras FULL OUTER JOIN payments ON payments.id_borrower = mitras.id_borrower FULL OUTER JOIN profile_mitras ON mitras.id_mitra = profile_mitras.id_mitra WHERE id_payment IS NOT NULL ORDER BY pinjaman_ke DESC , payment_ke DESC `; 
        // const getPayment = await db.sequelize.query(query)
        // const getPayment1 = Helper.toObject(getPayment[0])
        const getPayment = await db.payment.findAll({order : [["createdAt" , "DESC"] ]})
        let data = []
        let proms = new Promise( (resolve, reject) => {
            getPayment.forEach( async (p) => {
                totalPayment = totalPayment + p.amount_payment
                const mitras = await db.profileMitra.findOne({where: {id_mitra : p.id_mitra}})
                const users = await db.mitra.findOne({where: {id_borrower : p.id_borrower}})
                let data1 = {
                    "id_payment": p.id_payment,
                    "id_mitra": p.id_mitra,
                    "id_borrower": p.id_borrower,
                    "payment_method": p.payment_method,
                    "amount_payment": p.amount_payment,
                    "payment_ke": p.payment_ke,
                    "createdAt": p.createdAt,
                    "updatedAt": p.updatedAt,
                    user : users.nama_lengkap,
                    mitra : mitras
                }
                data.push(data1)
            });
            setTimeout(() => {
                resolve()
            },500)
        })
        proms.then(() => {
            return res.status(200).send({totaluser : getUser ,totalmitra : getMitra, totalborrower : getBorrower, totalpayment :totalPayment, payment :data})
            
        }) 
        
        
    } catch (err) {
        console.log(err);
        return res.status(400).send({status : err})
    }
}

const homeAdmin = async (req, res) => {
    try {
        
        const tableUser = await dbUser.findOne({where: {id_user : req.params.id_user}})
        const tableProfile = await dbProfile.findOne({where: {id_user : req.params.id_user}})
        const query0= `SELECT * FROM user_payments FULL OUTER JOIN mitras ON mitras.id_borrower = user_payments.id_borrower WHERE mitras.id_user = '${req.params.id_user}'`; 
        const tableBorrowerPayment = await db.sequelize.query(query0)
        const tableBorrowerPayment1 = Helper.toObject(tableBorrowerPayment[0])

        const query3= `SELECT * FROM user_payments FULL OUTER JOIN mitras ON mitras.id_borrower = user_payments.id_borrower WHERE mitras.id_user = '${req.params.id_user}' AND status = 'payment'`; 
        const tableBorrowerPayment2 = await db.sequelize.query(query3)
        const tableBorrowerPayment3 = Helper.toObject(tableBorrowerPayment2[0])

        const tableMessage = await db.message.findAll({where: {id_user : req.params.id_user},order : [["createdAt" , "DESC"] ]})
        const query= `SELECT * FROM mitras FULL OUTER JOIN payments ON payments.id_borrower = mitras.id_borrower WHERE id_user = '${req.params.id_user}' AND id_payment IS NOT NULL ORDER BY pinjaman_ke DESC , payment_ke DESC `; 
        const paymentHistory = await db.sequelize.query(query)
        const paymentHistory1 = Helper.toObject(paymentHistory[0])
        return res.status(200).send({ 
            user : tableUser,
            profile : tableProfile,
            peminjaman : tableBorrowerPayment1,
            message : tableMessage,
            paymenthistory : paymentHistory1,
            aktif : tableBorrowerPayment3
        })
  
    } catch (err) {
        console.log(err);
        return res.status(403).send(err)
    }
}


const mitraAdmin = async (req, res) => {
    try {
        const query0= `SELECT * FROM users FULL OUTER JOIN profile_mitras ON users.id_user = profile_mitras.id_user WHERE id_mitra = '${req.params.id_mitra}'`; 
        const tableProfile = await db.sequelize.query(query0)
        const tableProfile1 = Helper.toObject(tableProfile[0])

        const query1= `SELECT * FROM user_payments FULL OUTER JOIN mitras ON mitras.id_borrower = user_payments.id_borrower WHERE mitras.id_mitra = '${req.params.id_mitra}'`; 
        const tableBorrowerPayment = await db.sequelize.query(query1)
        const tableBorrowerPayment1 = Helper.toObject(tableBorrowerPayment[0])

        const tableMessage = await db.message.findAll({where: {id_mitra : req.params.id_mitra},order : [["createdAt" , "DESC"] ]})
        const query= `SELECT * FROM mitras FULL OUTER JOIN payments ON payments.id_borrower = mitras.id_borrower WHERE mitras.id_mitra = '${req.params.id_mitra}' AND id_payment IS NOT NULL ORDER BY pinjaman_ke DESC , payment_ke DESC `; 
        const paymentHistory = await db.sequelize.query(query)
        const paymentHistory1 = Helper.toObject(paymentHistory[0])
        return res.status(200).send({ 

            profile : tableProfile1,
            peminjaman : tableBorrowerPayment1,
            message : tableMessage,
            paymenthistory : paymentHistory1
        })
  
    } catch (err) {
        console.log(err);
        return res.status(403).send(err)
    }
}

const paymentAdmin = async (req, res) => {
    try {
        const query0= `SELECT * FROM payments FULL OUTER JOIN mitras ON payments.id_borrower = mitras.id_borrower FULL OUTER JOIN profiles ON mitras.id_user = profiles.id_user WHERE payments.id_payment IS NOT NULL  `; 
        const tablePayment = await db.sequelize.query(query0)
        const tablePayment1= Helper.toObject(tablePayment[0])

        return res.status(200).send({payment : tablePayment1})
    } catch (err) {
        console.log(err);
        return res.status(400).send({status : err})
    }
}


module.exports = {
    listAkunUser,
    listAkunMitra,
    listDetailUser,
    listDetailMitra,
    listBorrower,
    listBorrowerPending,
    listBorrowerAcc,
    listBorrowerHistory,
    listDetailBorrower,
    listPayment,
    listPaymentbyId,
    summarry,
    jumlahTotal,
    homeAdmin,
    mitraAdmin,
    paymentAdmin,
}