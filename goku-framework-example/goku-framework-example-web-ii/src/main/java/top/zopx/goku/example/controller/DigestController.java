//package top.zopx.goku.example.controller;
//
//import org.springframework.web.bind.annotation.*;
//import top.zopx.goku.framework.tools.pass.crtpt.rsa.RSAUtil;
//import top.zopx.goku.framework.tools.pass.crtpt.rsa.RsaKey;
//import top.zopx.goku.framework.tools.pass.crtpt.sm2.SM2Util;
//import top.zopx.goku.framework.tools.pass.codec.sm3.SM3Util;
//import top.zopx.goku.framework.tools.pass.crtpt.sm4.SM4Util;
//import top.zopx.goku.framework.tools.entity.wrapper.R;
//import top.zopx.goku.framework.tools.exception.BusException;
//import top.zopx.goku.framework.tools.exception.IBus;
//
///**
// * 加密测试
// *
// * @author Mr.Xie
// * @email xiezhyan@126.com
// * @date 2022-07-23 12:33:44
// */
//@RestController
//@RequestMapping("/digest")
//public class DigestController {
////
////    @GetMapping("/createKeys")
////    public R<RsaKey> createKeys(String type) {
////        switch (type.toLowerCase()) {
////            case "rsa":
////                RsaKey rsaKey = RSAUtil.createKeys();
////                return R.result(
////                        rsaKey
////                );
////            case "sm2":
////                RsaKey sm2Key = SM2Util.createKeys();
////                return R.result(
////                        sm2Key
////                );
////            case "sm4":
////                String key = SM4Util.createKeys();
////                return R.result(
////                        new RsaKey(key, key)
////                );
////            default:
////                // nothing
////                throw new BusException("", IBus.ERROR_CODE, "");
////        }
////    }
//
//    public static class V {
//        private String type;
//        private String publicKey;
//        private String privateKey;
//
//        public String getType() {
//            return type;
//        }
//
//        public void setType(String type) {
//            this.type = type;
//        }
//
//        public String getPublicKey() {
//            return publicKey;
//        }
//
//        public void setPublicKey(String publicKey) {
//            this.publicKey = publicKey;
//        }
//
//        public String getPrivateKey() {
//            return privateKey;
//        }
//
//        public void setPrivateKey(String privateKey) {
//            this.privateKey = privateKey;
//        }
//    }
//
//    @PostMapping
//    public R<String> doDigest(@RequestBody V v) {
//
//        String type = v.getType();
//        String privateKey = v.getPrivateKey();
//        String publicKey = v.getPublicKey();
//
//        String data = "sadijflasjiefsadijflasjiefsadijflasjiefsadijflasjiefsadijflasjiefsadijflasjiefsadijflasjiefsadijflasjiefsadijflasjiefsadijflasjiefsadijflasjiefsadijflasjiefsadijflasjiefsadijflasjiefsadijflasjiefsadijflasjiefsadijflasjiefsadijflasjiefsadijflasjiefsadijflasjiefsadijflasjiefsadijflasjiefsadijflasjiefsadijflasjiefsadijflasjiefsadijflasjief" +
//                "sadijflasjiefsadijflasjiefsadijflasjiefsadijflasjiefsadijflasjiefsadijflasjiefsadijflasjiefsadijflasjiefsadijflasjiefsadijflasjiefsadijflasjiefsadijflasjiefsadijflasjiefsadijflasjiefsadijflasjiefsadijflasjiefsadijflasjiefsadijflasjiefsadijflasjiefsadijflasjiefsadijflasjiefsadijflasjiefsadijflasjiefsadijflasjiefsadijflasjiefsadijflasjief";
//
//        switch (type.toLowerCase()) {
//            case "rsa":
//                return R.result(
//                        RSAUtil.decryptByPrivateKey(
//                                RSAUtil.encryptByPublicKey(data, publicKey),
//                                privateKey
//                        )
//                );
//            case "sm2":
//                return R.result(
//                        SM2Util.decrypt(
//                                SM2Util.encrypt(data, publicKey),
//                                privateKey
//                        )
//                );
//            case "sm3":
//                return R.result(SM3Util.digest(data));
//            case "sm4":
//                String key = SM4Util.createKeys();
//                return R.result(
//                        SM4Util.decrypt(
//                                key,
//                                SM4Util.encrypt(key, data)
//                        )
//                );
//            default:
//                // nothing
//                throw new BusException("", IBus.ERROR_CODE, "");
//        }
//    }
//
//}
//
