package com.fahim.mevronrider.views.activity

import android.Manifest
import android.annotation.TargetApi
import android.app.KeyguardManager
import android.content.Context
import android.content.pm.PackageManager
import android.hardware.fingerprint.FingerprintManager
import android.os.Build
import android.os.Bundle
import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyPermanentlyInvalidatedException
import android.security.keystore.KeyProperties
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.fahim.mevronrider.R
import com.fahim.mevronrider.utils.FingerPrintHelper
import java.io.IOException
import java.security.*
import java.security.cert.CertificateException
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.NoSuchPaddingException
import javax.crypto.SecretKey

class FingerprintActivity : AppCompatActivity() {


    private var mFingerprintImage: ImageView? = null
    private var mParaLabel: TextView? = null

    private var fingerprintManager: FingerprintManager? = null
    private var keyguardManager: KeyguardManager? = null

    private var keyStore: KeyStore? = null
    private var cipher: Cipher? = null
    private val KEY_NAME = "AndroidKey"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_finger_auth)


        mFingerprintImage = findViewById<ImageView>(R.id.fingerprintImage)
        mParaLabel = findViewById<TextView>(R.id.paraLabel)

        // Check 1: Android version should be greater or equal to Marshmallow
        // Check 2: Device has Fingerprint Scanner
        // Check 3: Have permission to use fingerprint scanner in the app
        // Check 4: Lock screen is secured with atleast 1 type of lock
        // Check 5: Atleast 1 Fingerprint is registered

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            fingerprintManager = getSystemService(Context.FINGERPRINT_SERVICE) as FingerprintManager
            keyguardManager = getSystemService(Context.KEYGUARD_SERVICE) as KeyguardManager

            if (!fingerprintManager!!.isHardwareDetected) {

                mParaLabel!!.text = "Fingerprint Scanner not detected in Device"

            } else if (ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.USE_FINGERPRINT
                ) != PackageManager.PERMISSION_GRANTED
            ) {

                mParaLabel!!.text = "Permission not granted to use Fingerprint Scanner"

            } else if (!keyguardManager!!.isKeyguardSecure) {

                mParaLabel!!.text = "Add Lock to your Phone in Settings"

            } else if (!fingerprintManager!!.hasEnrolledFingerprints()) {

                mParaLabel!!.text = "You should add atleast 1 Fingerprint to use this Feature"

            } else {

                mParaLabel!!.text = "Place your Finger on Scanner to Access the App."

                generateKey()

                if (cipherInit()) {

                    val cryptoObject = FingerprintManager.CryptoObject(cipher!!)
                    val fingerprintHandler = FingerPrintHelper(this)
                    fingerprintHandler.startAuth(fingerprintManager, cryptoObject)

                }
            }

        }

    }

    @TargetApi(Build.VERSION_CODES.M)
    private fun generateKey() {

        try {

            keyStore = KeyStore.getInstance("AndroidKeyStore")
            val keyGenerator = KeyGenerator.getInstance(KeyProperties.KEY_ALGORITHM_AES, "AndroidKeyStore")

            keyStore!!.load(
                null
            )
            keyGenerator.init(
                KeyGenParameterSpec.Builder(
                    KEY_NAME,
                    KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT
                )
                    .setBlockModes(KeyProperties.BLOCK_MODE_CBC)
                    .setUserAuthenticationRequired(true)
                    .setEncryptionPaddings(
                        KeyProperties.ENCRYPTION_PADDING_PKCS7
                    )
                    .build()
            )
            keyGenerator.generateKey()

        } catch (e: KeyStoreException) {

            e.printStackTrace()

        } catch (e: IOException) {
            e.printStackTrace()
        } catch (e: CertificateException) {
            e.printStackTrace()
        } catch (e: NoSuchAlgorithmException) {
            e.printStackTrace()
        } catch (e: InvalidAlgorithmParameterException) {
            e.printStackTrace()
        } catch (e: NoSuchProviderException) {
            e.printStackTrace()
        }

    }

    @TargetApi(Build.VERSION_CODES.M)
    fun cipherInit(): Boolean {
        try {
            cipher =
                Cipher.getInstance(KeyProperties.KEY_ALGORITHM_AES + "/" + KeyProperties.BLOCK_MODE_CBC + "/" + KeyProperties.ENCRYPTION_PADDING_PKCS7)
        } catch (e: NoSuchAlgorithmException) {
            throw RuntimeException("Failed to get Cipher", e)
        } catch (e: NoSuchPaddingException) {
            throw RuntimeException("Failed to get Cipher", e)
        }


        try {

            keyStore!!.load(null)

            val key = keyStore!!.getKey(KEY_NAME, null) as SecretKey

            cipher!!.init(Cipher.ENCRYPT_MODE, key)

            return true

        } catch (e: KeyPermanentlyInvalidatedException) {
            return false
        } catch (e: KeyStoreException) {
            throw RuntimeException("Failed to init Cipher", e)
        } catch (e: CertificateException) {
            throw RuntimeException("Failed to init Cipher", e)
        } catch (e: UnrecoverableKeyException) {
            throw RuntimeException("Failed to init Cipher", e)
        } catch (e: IOException) {
            throw RuntimeException("Failed to init Cipher", e)
        } catch (e: NoSuchAlgorithmException) {
            throw RuntimeException("Failed to init Cipher", e)
        } catch (e: InvalidKeyException) {
            throw RuntimeException("Failed to init Cipher", e)
        }

    }

}