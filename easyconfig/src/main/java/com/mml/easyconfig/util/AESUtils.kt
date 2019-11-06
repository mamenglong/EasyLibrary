package com.mml.easyconfig.util

/**
 * 项目名称：EasyUtils
 * Created by Long on 2019/4/11.
 * 修改时间：2019/4/11 13:08
 */
import java.io.UnsupportedEncodingException
import java.nio.charset.Charset
import javax.crypto.Cipher
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec

class AESUtils {
    companion object {
        //算法/工作模式/填充模式
        private val TRANSFORMATION = "AES/CBC/PKCS5Padding"
        private val ALGORITHM_NAME = "AES"

        var SecretKey = "mamenglong123456"
        var IvParameter = "123456mamenglong"

        @Throws(Exception::class)
        fun encrypt(iv: String = IvParameter, key: String = SecretKey, var2: ByteArray): ByteArray {
            val var3 = SecretKeySpec(key.toByteArray(), ALGORITHM_NAME)
            val var4 = Cipher.getInstance(TRANSFORMATION)
            val var5 = IvParameterSpec(iv.toByteArray())
            var4.init(Cipher.ENCRYPT_MODE, var3, var5)
            return Base64.encode(var4.doFinal(var2),Base64.DEFAULT)
        }

        @Throws(Exception::class)
        fun decrypt(iv: String = IvParameter, key: String = SecretKey, var2: ByteArray): ByteArray {
            val var2 = Base64.decode(var2,Base64.DEFAULT)
            val var3 = SecretKeySpec(key.toByteArray(), ALGORITHM_NAME)
            val var4 = Cipher.getInstance(TRANSFORMATION)
            val var5 = IvParameterSpec(iv.toByteArray())
            var4.init(Cipher.DECRYPT_MODE, var3, var5)
            return var4.doFinal(var2)
        }

        @Throws(Exception::class)
        fun encrypt(var2: String): String {
            val var3 = SecretKeySpec(SecretKey.toByteArray(), ALGORITHM_NAME)
            val var4 = Cipher.getInstance(TRANSFORMATION)
            val var5 = IvParameterSpec(IvParameter.toByteArray())
            var4.init(1, var3, var5)
            var result = var4.doFinal(var2.toByteArray())
            result = Base64.encode(result,Base64.DEFAULT)
            return bytesToString(result)
        }

        @Throws(Exception::class)
        fun decrypt(var2: String): String {
            val var2 = Base64.decode(var2,Base64.DEFAULT)
            val var3 = SecretKeySpec(SecretKey.toByteArray(), ALGORITHM_NAME)
            val var4 = Cipher.getInstance(TRANSFORMATION)
            val var5 = IvParameterSpec(IvParameter.toByteArray())
            var4.init(2, var3, var5)
            return bytesToString(var4.doFinal(var2))
        }

        /**
         * 字节数组转为字符串.
         */
        private fun bytesToString(bytes: ByteArray): String {
            var string: String? = null
            try {
                string = String(bytes, Charset.forName("utf-8"))
            } catch (e: UnsupportedEncodingException) {
                e.printStackTrace()
            }

            return string!!
        }
    }
}

