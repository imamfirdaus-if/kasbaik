const util = require("util");
const Multer = require("multer");
const maxSize = 2 * 1024 * 1024;
// var storage = Multer.diskStorage({
    
//     filename: (req, file, callback) => {
//       const match = ["image/png", "image/jpeg"];
//       if (match.indexOf(file.mimetype) === -1) {
//         var message = `${file.originalname} is invalid. Only accept png/jpeg.`;
//         return callback(message, null);
//       }
//       var filename = `${Date.now()}-bezkoder-${file.originalname}`;
//       callback(null, filename);
//     }
//   });
let processFile = Multer({
    storage: Multer.memoryStorage(),
    limits: { fileSize: maxSize },
    
}).any('file');
let processFileMiddleware = util.promisify(processFile);

module.exports = processFileMiddleware;