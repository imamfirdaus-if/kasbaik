const processFile = require("../middleware/upload");
const { format } = require("util");
const { Storage } = require("@google-cloud/storage");
const db = require("../db/db");

//inisiasi storage client with credentials
const storage = new Storage({ keyFilename: "google-cloud-key.json" });
const bucket = storage.bucket("nyoba_project");


const upload = async (req, res) => {
            
    try {
        await processFile(req, res);
        
        var counter = 0;
        if (!req.files) {
            res.status(400).send('No file uploaded.');
            return;
        }

            nama_lengkap = req.body.nama_lengkap;
            wa = req.body.wa;
            alamat_tinggal = req.body.alamat_tinggal;
            alamat_ktp = req.body.alamat_ktp;
            profesi = req.body.profesi;
            let foto_diri;
            let foto_ktp;
            let foto_selfie;

        let proms = new Promise((resolve, reject) => {
           
            req.files.forEach ( (fil) => {
            const blob = bucket.file(fil.originalname);  
            console.log(fil);
            const blobStream = blob.createWriteStream({
                resumable: false,
            });
        
            blobStream.on("error", (err) => {
                res.status(500).send({ message: err.message });
            });
            console.log(blob.name);
            const publicUrl = `https://storage.googleapis.com/${bucket.name}/${blob.name}`
            if (counter === 0){
                foto_diri = publicUrl 
                console.log('sukses uplaod diri');
                
            } else if (counter ===1){
                foto_ktp = publicUrl
                console.log('sukses uplaod ktp');
                
            } else {
                foto_selfie = publicUrl
                console.log('sukses uplaod selfie');
                
                
            }
     
            blobStream.end(fil.buffer);
            counter+=1
                if (counter ===3){
                    resolve()
                    
                }
            
        })
        })
        
        proms.then(async (message) => {
            // Handle results
            const query = `INSERT INTO profile(nama_lengkap, wa, alamat_tinggal, alamat_ktp, profesi, foto_diri, foto_ktp, foto_selfie) values ('${nama_lengkap}', '${wa}', '${alamat_tinggal}', '${alamat_ktp}', '${profesi}', '${foto_diri}', '${foto_ktp}', '${foto_selfie}');`
                await db.query(query, (err, results) => {
                    if (err) {
                        console.error(err.message);
                        res.send(err.message);
                        return;
                    } else {
                        console.log('data berhasil dimasukkan');
                        return res.status(200).send(
                            `ini adalah foto diri ${foto_diri}
                            ini adalah foto ktp ${foto_ktp}
                            ini adalah foto selfie ${foto_selfie}`
                        );
                    }
                });
        })
        .catch(e => {
            console.error(e);
        })
        
        
        
    } catch (err) {
        console.log(err.message);
        if (err.code == "LIMIT_FILE_SIZE") {
            return res.status(500).send({
                message: "File size cannot be larger than 2MB"
            });
        }
        res.status(500).send({
            message: `Could not upload the file: ${req.files.originalname}. ${err.message}`,
        });
    }
};

const getListFiles = async (req, res) => {
    try {
        const [files] = await bucket.getFiles();
        let fileInfos = [];
        files.forEach((file) => {
            fileInfos.push({
                name: file.name,
                url: file.metadata.mediaLink,
            });
        });
        res.status(200).send(fileInfos);
    } catch (err) {
        console.log(err);
        res.status(500).send({
            message: "unable to read list of files!",
        });
    }
};

const download = async (req, res) => {
    try {
        const [metaData] = await bucket.file(req.params.name).getMetadata();
        res.redirect(metaData.mediaLink);
    } catch (err) {
        res.status(500).send({
            message: "Could not download the file. " + err,
        });
    }
};

module.exports = {
    upload,
    getListFiles,
    download,
}



