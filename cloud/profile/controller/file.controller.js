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
        if (!req.file) {
            return res.status(400).send({ message: "Please upload a file" });
        }

        //create a new blob in the bucket and upload file
        const blob = bucket.file(req.file.originalname);
        console.log(blob);
        const blobStream = blob.createWriteStream({
            resumable: false,
        });
        blobStream.on("error", (err) => {
            res.status(500).send({ message: err.message });
        });
        blobStream.on("finish", async (data) => {
            // membuat URL agar bisa diakses HTTP
            const publicUrl = format(
                `https://storage.googleapis.com/${bucket.name}/${blob.name}`
            );
            nama_lengkap = req.body.nama_lengkap;
            wa = req.body.wa;
            alamat_tinggal = req.body.alamat_tinggal;
            alamat_ktp = req.body.alamat_ktp;
            profesi = req.body.profesi;
            const query = `INSERT INTO profile(nama_lengkap, wa, alamat_tinggal, alamat_ktp, profesi, link_gambar) values ('${nama_lengkap}', '${wa}', '${alamat_tinggal}', '${alamat_ktp}', '${profesi}', '${publicUrl}');`
            db.query(query, (err, results) => {
                if (err) {
                    console.error(err.detail);
                    res.send(err.detail);
                    return;
                  } else {
                    console.log('data Gagal dimasukkan');
                    return;
                  }
            });
            try {
                // membuat file menjadi public
                await bucket.file(req.file.originalname).makePublic();
            } catch {
                return res.status(500).send({
                    message:
                        `Uploaded the file successfully: ${req.file.originalname}, but public access is denied!`,
                    url: publicUrl,
                });
            }
            res.status(200).send({
                message: "Uploaded the file successfully: " + req.file.originalname,
                url: publicUrl,
            });
        });
        blobStream.end(req.file.buffer);
    } catch (err) {
        if (err.code == "LIMIT_FILE_SIZE") {
            return res.status(500).send({
                message: "File size cannot be larger than 2MB"
            });
        }

        res.status(500).send({
            message: `Could not upload the file: ${req.file.originalname}. ${err}`,
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



