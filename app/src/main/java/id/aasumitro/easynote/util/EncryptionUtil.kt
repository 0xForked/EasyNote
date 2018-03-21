package id.aasumitro.easynote.util

import android.annotation.SuppressLint
import android.util.Base64
import android.util.Log
import java.io.UnsupportedEncodingException
import java.security.InvalidKeyException
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import java.security.spec.InvalidKeySpecException
import javax.crypto.*
import javax.crypto.spec.DESKeySpec

/**
 * Created by Agus Adhi Sumitro on 20/02/2018.
 * https://asmith.my.id
 * aasumitro@gmail.com
 */

class EncryptionUtil {

    companion object { const val TAG = "PASSWORD" }

    fun md5(s: String): String {
        try {
            // Create MD5 Hash
            val digest= MessageDigest.getInstance("MD5")
            digest.update(s.toByteArray())
            val messageDigest=digest.digest()

            // Create Hex String
            val hexString=StringBuilder()
            for (i in messageDigest.indices)
                hexString.append(Integer.toHexString(0xFF and messageDigest[i].toInt()))
            return hexString.toString()

        } catch (e: NoSuchAlgorithmException) {
            Log.d(TAG, "Something is gone wrong calculating MD5", e)
        }

        return ""
    }


    @SuppressLint("GetInstance")
    fun encrypt(value: String, password: String): String {
        var encryptedValue=""
        try {
            val keySpec=DESKeySpec(password.toByteArray(charset("UTF8")))
            val keyFactory=SecretKeyFactory.getInstance("DES")
            val key=keyFactory.generateSecret(keySpec)
            val clearText=value.toByteArray(charset("UTF8"))
            // Cipher is not thread safe
            val cipher=Cipher.getInstance("DES")
            cipher.init(Cipher.ENCRYPT_MODE, key)
            encryptedValue = Base64.encodeToString(cipher.doFinal(clearText), Base64.DEFAULT)
            return encryptedValue
        } catch (e: InvalidKeyException) {
            Log.d(TAG, "Something is gone wrong encrypting", e)
        } catch (e: NoSuchPaddingException) {
            Log.d(TAG, "Something is gone wrong encrypting", e)
        } catch (e: InvalidKeySpecException) {
            Log.d(TAG, "Something is gone wrong encrypting", e)
        } catch (e: BadPaddingException) {
            Log.d(TAG, "Something is gone wrong encrypting", e)
        } catch (e: IllegalBlockSizeException) {
            Log.d(TAG, "Something is gone wrong encrypting", e)
        } catch (e: NoSuchAlgorithmException) {
            Log.d(TAG, "Something is gone wrong encrypting", e)
        } catch (e: UnsupportedEncodingException) {
            Log.d(TAG, "Something is gone wrong encrypting", e)
        }

        return encryptedValue
    }


    @SuppressLint("GetInstance")
    fun decrypt(value: String, password: String): String {
        val decryptedValue: String
        try {
            val keySpec = DESKeySpec(password.toByteArray(charset("UTF8")))
            val keyFactory = SecretKeyFactory.getInstance("DES")
            val key = keyFactory.generateSecret(keySpec)

            val encryptedPwdBytes = Base64.decode(value, Base64.DEFAULT)
            // cipher is not thread safe
            val cipher = Cipher.getInstance("DES")
            cipher.init(Cipher.DECRYPT_MODE, key)
            val decryptedValueBytes = cipher.doFinal(encryptedPwdBytes)

            decryptedValue=String(decryptedValueBytes)
        } catch (e: InvalidKeyException) {
            Log.e(TAG, "Error decrypting")
            return value
        } catch (e: UnsupportedEncodingException) {
            Log.e(TAG, "Error decrypting")
            return value
        } catch (e: InvalidKeySpecException) {
            Log.e(TAG, "Error decrypting")
            return value
        } catch (e: NoSuchAlgorithmException) {
            Log.e(TAG, "Error decrypting")
            return value
        } catch (e: BadPaddingException) {
            Log.e(TAG, "Error decrypting")
            return value
        } catch (e: NoSuchPaddingException) {
            Log.e(TAG, "Error decrypting")
            return value
        } catch (e: IllegalBlockSizeException) {
            Log.e(TAG, "Error decrypting")
            return value
        } catch (e: IllegalArgumentException) {
            Log.e(TAG, "Error decrypting: old notes were not encrypted but just masked to users")
            return value
        }

        return decryptedValue
    }
}