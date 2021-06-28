var iterations = 31;
var RSA_puk_length = 1024;
var PEM_split = "-----";

/**
 * DES加密
 * @param message 明文
 * @param key	  令牌
 * @param iv	  向量
 * @returns {string} 密文
 */
function encryptByDES(message, key,iv){
    var keyHex = CryptoJS.enc.Utf8.parse(key);
    var encrypted = CryptoJS.DES.encrypt(message, keyHex, {
        iv:iv,
        mode: CryptoJS.mode.ECB,
        padding: CryptoJS.pad.Pkcs7
    });
    return encrypted.ciphertext.toString();
}

/**
 * DES解密
 * @param ciphertext 密文
 * @param key key 令牌
 * @param iv	  向量
 * @returns {string} 明文
 */
function decryptByDES(ciphertext, key,iv){
    var keyHex = CryptoJS.enc.Utf8.parse(key);
    var decrypted = CryptoJS.DES.decrypt({
        ciphertext: CryptoJS.enc.Hex.parse(ciphertext)
    }, keyHex, {
        iv:iv,
        mode: CryptoJS.mode.ECB,
        padding: CryptoJS.pad.Pkcs7
    });
    var result_value = decrypted.toString(CryptoJS.enc.Utf8);
    return result_value;
}

/**
 *
 * @param password 初始密码
 * @returns {*} des加密令牌、iv、salt
 */
function returnDesKey(password,saltValue = randomSalt(false,8)){
    var salt = CryptoJS.enc.Hex.parse(saltValue);
    // PBE according to PKCS#5 v1.5 (in other words: PBKDF1)
    var md5 = CryptoJS.algo.MD5.create();
    md5.update(password);
    md5.update(salt);
    var result = md5.finalize();
    md5.reset();
    for(var i = 1; i < iterations; i++) {
        md5.update(result);
        result = md5.finalize();
        md5.reset();
    }
    // splitting key and IV
    var key = CryptoJS.lib.WordArray.create(result.words.slice(0, 2));
    var iv = CryptoJS.lib.WordArray.create(result.words.slice(2, 4));
    var des = {};
    des.key = key;
    des.iv = iv ;
    des.salt = saltValue;
    return des;
}

/**
 *生成RSA密钥对
 * @param RSA_puk_length 公钥长度
 * @param num 密钥对生成依据
 * @returns {{}} RSA密钥对
 */
function generate_start_RSAKey(RSA_puk_length){
    // 生产密钥对
    var rsaKeypair = KEYUTIL.generateKeypair("RSA", RSA_puk_length);
    // 密钥对象获取pem格式的密钥
    var pub = KEYUTIL.getPEM(rsaKeypair.pubKeyObj);
    var prv = KEYUTIL.getPEM(rsaKeypair.prvKeyObj,'PKCS8PRV');
    var keyPair = {};
    keyPair.puk = pub;
    keyPair.prk = prv;
    return keyPair;
}

function extract_PEM_Key(key) {
    return key.split(PEM_split)[2];
}

/**
 * 使用公钥加密
 * @param message 明文
 * @param puk 公钥
 * @returns {*} 密文
 */
function encryptByRSA(message,puk){
    //加密
    var jsEncrypt = new JSEncrypt();
    jsEncrypt.setPublicKey(puk);
    var enc = jsEncrypt.encrypt(message);
    return enc;
}

/**
 * 使用公钥解密
 * @param encrytext
 * @param puk
 * @returns {null|undefined|PromiseLike<ArrayBuffer>|*}
 */
function decryptByRSAWithPuk(encrytext,puk){
    //使用公钥解密
    var jsDecrypt = new JSEncrypt();
    jsDecrypt.setPublicKey(puk);
    return jsDecrypt.decrypt(encrytext);
}

/**
 *使用私钥解密
 * @param encrytext 密文
 * @param prk 私钥
 * @returns {*} 明文
 */
function decryptByRSA(encrytext,prk){
    //使用私钥解密
    var decrypt = new JSEncrypt();
    decrypt.setPrivateKey(prk);
    return decrypt.decrypt(encrytext);
}

/**
 * 使用私钥加密
 * @param message
 * @param prk
 * @returns {*}
 */
function encryprByRSAWithprk(message,prk){
    //使用私钥加密
    var jsDecrypr = new JSEncrypt();
    jsDecrypr.setPrivateKey(prk);
    var dec = jsDecrypr.encrypt(message);
    return dec;
}

/**
 * 对RSA密钥对私钥加密 并返回最新密钥对
 * @param password 初始口令
 * @returns {{}}
 * puk 公钥（后端使用）
 * PEM_puk 标准pem格式公钥
 * PEM_prk 标准pem格式私钥
 */
function generateKeyByRSAAndPBE(password){
    //生成RSA密钥对
    var RSA_keypair = generate_start_RSAKey(RSA_puk_length);
    //获取RSA私钥
    var RSA_pem_prk = RSA_keypair.prk;//需要加密的数据 RSA私钥(pem格式)

    //依据密码生成DES口令
    var DES_key = returnDesKey(password);
    //对RSA私钥进行加密
    var security_pem_RSA_prk = encryptByDES(RSA_pem_prk,DES_key.key,DES_key.iv);

    var returnKeyPair = {};
    returnKeyPair.puk = extract_PEM_Key(RSA_keypair.puk);
    returnKeyPair.PEM_puk = RSA_keypair.puk;
    returnKeyPair.PEM_prk = security_pem_RSA_prk;
    returnKeyPair.salt = DES_key.salt;
    return returnKeyPair;
}


/**
 * RSA+PBE 解密
 * @param password 密码
 * @param encryptMessage 密文
 * @param RSA_prk pem格式 RSA密钥
 * @returns {*}
 */
function decryptByRSAAndPBE(password,encryptMessage,RSA_prk,saltValue){
    var des = returnDesKey(password,saltValue);
    var key = des.key;
    var iv = des.iv;
    var RSA_real_key = decryptByDES(RSA_prk,key,iv);
    var realMessage = decryptByRSA(encryptMessage,RSA_real_key);
    return  realMessage;
}

/**
 *
 * @param password 初始密码
 * @returns {*} des加密令牌、iv、salt
 */
function returnDesKey(password,saltValue = randomSalt(false,8)){
    var salt = CryptoJS.enc.Hex.parse(saltValue);
    // PBE according to PKCS#5 v1.5 (in other words: PBKDF1)
    var md5 = CryptoJS.algo.MD5.create();
    md5.update(password);
    md5.update(salt);
    var result = md5.finalize();
    md5.reset();
    for(var i = 1; i < iterations; i++) {
        md5.update(result);
        result = md5.finalize();
        md5.reset();
    }
    // splitting key and IV
    var key = CryptoJS.lib.WordArray.create(result.words.slice(0, 2));
    var iv = CryptoJS.lib.WordArray.create(result.words.slice(2, 4));
    var des = {};
    des.key = key;
    des.iv = iv ;
    des.salt = saltValue;
    return des;
}

/**
 * randomSalt 产生任意长度随机字母数字组合
 * randomFlag-是否任意长度 min-任意长度最小位[固定位数] max-任意长度最大位
 */
function randomSalt(randomFlag, min, max){
    var str = "",
        range = min,
        arr = ['0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'];

    // 随机产生
    if(randomFlag){
        range = Math.round(Math.random() * (max-min)) + min;
    }
    for(var i=0; i<range; i++){
        pos = Math.round(Math.random() * (arr.length-1));
        str += arr[pos];
    }
    return str;
}


/**
 * sha3进行密码加密
 */
function encryptPassword(password) {
    return  CryptoJS.SHA3(password).toString(CryptoJS.enc.Hex);
}

