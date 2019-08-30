package com.fahim.mevronrider.views.activity

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.media.ExifInterface
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.MotionEvent
import android.view.View
import android.view.View.OnTouchListener
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.facebook.share.internal.MessengerShareContentUtility.IMAGE_URL
import com.fahim.mevronrider.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_edit_profile.*
import kotlinx.android.synthetic.main.tb_cut.*
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*


class EditProfileActivity : AppCompatActivity() {


    lateinit var mAuth: FirebaseAuth
    private var mDDriverProfileReference: DatabaseReference? = null
    private var mDatabase: FirebaseDatabase? = null
    private var mProgressBar: ProgressBar? = null


    val REQUEST_TAKE_PHOTO = 1
    val PICK_IMAGE = 2
    var mCurrentPhotoPath: String? = null
    var bitmap: Bitmap? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)
        mAuth = FirebaseAuth.getInstance()
        mProgressBar = findViewById(R.id.progress_bar)










        btnSaveEdited.setOnClickListener {
            mDatabase = FirebaseDatabase.getInstance()

            mDDriverProfileReference = mDatabase!!.reference.child("Drivers").child(mAuth.currentUser!!.uid)
            System.out.println("user id is $mDDriverProfileReference")


            val myMap = mapOf<String, Any>(
                "firstName" to etEditName.text.toString(),
                "lastName" to etEditLastName.text.toString(),
                "ssn" to etEditSN.text.toString()
            )
            mDDriverProfileReference!!.updateChildren(myMap)


        }

        etEditName.setOnTouchListener(object : OnTouchListener {
            override fun onTouch(v: View, event: MotionEvent): Boolean {
                val DRAWABLE_RIGHT = 2
                if (event.action == MotionEvent.ACTION_UP) {
                    if (event.rawX >= etEditName.right - etEditName.compoundDrawables[DRAWABLE_RIGHT].bounds.width()) {
                        etEditName.setText("")
                        return true
                    }
                }
                return false
            }
        })









        etEditLastName.setOnTouchListener(object : OnTouchListener {
            override fun onTouch(v: View, event: MotionEvent): Boolean {
                val DRAWABLE_RIGHT = 2
                if (event.action == MotionEvent.ACTION_UP) {
                    if (event.rawX >= etEditLastName.right - etEditLastName.compoundDrawables[DRAWABLE_RIGHT].bounds.width()) {
                        etEditLastName.setText("")
                        return true
                    }
                }
                return false
            }
        })




        etEditEmail.setOnTouchListener(object : OnTouchListener {
            override fun onTouch(v: View, event: MotionEvent): Boolean {
                val DRAWABLE_RIGHT = 2
                if (event.action == MotionEvent.ACTION_UP) {
                    if (event.rawX >= etEditEmail.right - etEditEmail.compoundDrawables[DRAWABLE_RIGHT].bounds.width()) {
                        etEditEmail.setText("")
                        return true
                    }
                }
                return false
            }
        })





        etEditSN.setOnTouchListener(object : OnTouchListener {
            override fun onTouch(v: View, event: MotionEvent): Boolean {
                val DRAWABLE_RIGHT = 2
                if (event.action == MotionEvent.ACTION_UP) {
                    if (event.rawX >= etEditSN.right - etEditSN.compoundDrawables[DRAWABLE_RIGHT].bounds.width()) {
                        etEditSN.setText("")
                        return true
                    }
                }
                return false
            }
        })

        menuCut.setOnClickListener {
            val intent = Intent(
                applicationContext,
                com.fahim.mevronrider.views.activity.ProfileActivity::class.java
            )
            startActivity(intent)
        }

        ivUserImage.setOnClickListener {
            selectImage()
        }


    }


    override fun onBackPressed() {
        if (chooseImage.visibility == View.VISIBLE) {
            chooseImage.visibility = View.GONE
        } else {
            super.onBackPressed()
        }
    }


    private fun selectImage() {
        val items =
            arrayOf<CharSequence>("Take Photo", "Choose from Library", "Remove pic", "Cancel")
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Add Photo!")
        builder.setItems(items) { dialog, item ->
            if (items[item] == "Take Photo") {
                ActivityCompat.requestPermissions(
                    this, arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), 1
                )
            } else if (items[item] == "Choose from Library") {
                ActivityCompat.requestPermissions(
                    this, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), 2
                )
            } else if (items[item] == "Remove pic") {
                ivUserImage.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.icon_default_profile_photo))
            } else if (items[item] == "Cancel") {
                dialog.dismiss()
            }
        }
        builder.show()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<String>, grantResults: IntArray
    ) {
        when (requestCode) {
            1 -> {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    dispatchTakePictureIntent()
                } else Toast.makeText(
                    this, "Permission required to change the picture", Toast.LENGTH_SHORT
                ).show()
                return
            }
            2 -> {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    galleryAddPic()
                } else Toast.makeText(
                    this, "Permission required to change the picture", Toast.LENGTH_SHORT
                ).show()
                return
            }
        }
    }

    private fun dispatchTakePictureIntent() {
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(packageManager) != null) {
            // Create the File where the photo should go
            var photoFile: File? = null
            try {
                photoFile = createImageFile()
            } catch (ex: IOException) {
                ex.printStackTrace()
            }

            // Continue only if the File was successfully created
            if (photoFile != null) {
                val photoURI = FileProvider.getUriForFile(
                    this, "com.taxi.applligent.fileprovider", photoFile
                )
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO)
            }
        }
    }

    private fun galleryAddPic() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE)
    }

    @SuppressLint("SimpleDateFormat")
    @Throws(IOException::class)
    private fun createImageFile(): File {
        // Create an image file name
        // Create an image file name
        val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val imageFileName = "JPEG_" + timeStamp + "_"
        val storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        val image = File.createTempFile(
            imageFileName, /* prefix */
            ".jpg", /* suffix */
            storageDir      /* directory */
        )
        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.absolutePath
        return image
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == PICK_IMAGE) {
            try {
                if (data != null) {
                    val uri = data.data
                    bitmap = MediaStore.Images.Media.getBitmap(contentResolver, uri)
                    Glide.with(this).load(bitmap).apply(
                        RequestOptions().diskCacheStrategy(DiskCacheStrategy.NONE).circleCrop().override(
                            300, 300
                        )
                    ).into(ivUserImage)
                }
            } catch (e: IOException) {
                e.printStackTrace()
            }
        } else {
            if (resultCode == RESULT_OK) {
                val imgFile = File((mCurrentPhotoPath))
                if (imgFile.exists()) {
                    bitmap = BitmapFactory.decodeFile(imgFile.absolutePath)
                    var orientation = 0
                    try {
                        val exif = ExifInterface(imgFile.absolutePath)
                        orientation = exif.getAttributeInt(
                            ExifInterface.TAG_ORIENTATION,
                            ExifInterface.ORIENTATION_UNDEFINED
                        )
                    } catch (e: IOException) {
                        e.printStackTrace()
                    }

                    bitmap = rotateBitmap(bitmap!!, orientation)
                    Glide.with(this).load(bitmap).apply(
                        RequestOptions().diskCacheStrategy(DiskCacheStrategy.NONE).circleCrop().override(
                            300, 300
                        )
                    ).into(ivUserImage)
                    imgFile.delete()
                }
            }
        }
    }

    fun rotateBitmap(bitmap: Bitmap, orientation: Int): Bitmap? {
        val matrix = Matrix()
        when (orientation) {
            ExifInterface.ORIENTATION_NORMAL -> return bitmap
            ExifInterface.ORIENTATION_FLIP_HORIZONTAL -> matrix.setScale(-1f, 1f)
            ExifInterface.ORIENTATION_ROTATE_180 -> matrix.setRotate(180f)
            ExifInterface.ORIENTATION_FLIP_VERTICAL -> {
                matrix.setRotate(180f)
                matrix.postScale(-1f, 1f)
            }
            ExifInterface.ORIENTATION_TRANSPOSE -> {
                matrix.setRotate(90f)
                matrix.postScale(-1f, 1f)
            }
            ExifInterface.ORIENTATION_ROTATE_90 -> matrix.setRotate(90f)
            ExifInterface.ORIENTATION_TRANSVERSE -> {
                matrix.setRotate(-90f)
                matrix.postScale(-1f, 1f)
            }
            ExifInterface.ORIENTATION_ROTATE_270 -> matrix.setRotate(-90f)
            else -> return bitmap
        }
        try {
            val bmRotated = Bitmap.createBitmap(bitmap, 0, 0, bitmap.width, bitmap.height, matrix, true)
            bitmap.recycle()
            return bmRotated
        } catch (e: Throwable) {
            e.printStackTrace()
            return null
        }

    }

    fun loadImages(url: String, imageView: ImageView) {
        val text = IMAGE_URL + url
        Glide.with(this)
            .load(IMAGE_URL + url)
            .apply(RequestOptions().diskCacheStrategy(DiskCacheStrategy.NONE).circleCrop().override(300, 300))
            .into(imageView)
    }


}






