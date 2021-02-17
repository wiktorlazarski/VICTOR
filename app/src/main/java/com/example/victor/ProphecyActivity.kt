package com.example.victor

import android.app.ProgressDialog
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Base64
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject
import java.io.ByteArrayOutputStream
import java.io.File


class ProphecyActivity : AppCompatActivity() {
    private var requestQueue: RequestQueue? = null
    private var currPhotoPath: String? = null
    private var progressDialog: ProgressDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_prophecy)

        requestQueue = Volley.newRequestQueue(this)

        val fileName = "photo"
        val storageDirectory = getExternalFilesDir(Environment.DIRECTORY_PICTURES)

        val imageFile = File.createTempFile(fileName, ".jpeg", storageDirectory)

        currPhotoPath = imageFile.absolutePath
        val imageUri = FileProvider.getUriForFile(
            this,
            "com.example.victor.fileprovider",
            imageFile
        )

        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri)
        startActivityForResult(cameraIntent, 1)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 1 && resultCode == RESULT_OK) {
            val bitmap = BitmapFactory.decodeFile(currPhotoPath)

            findViewById<ImageView>(R.id.captured_image_view).apply {
                setImageBitmap(bitmap)
            }

            val byteStream = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteStream)
            val imageBytes = byteStream.toByteArray()
            val imageString = Base64.encodeToString(imageBytes, Base64.DEFAULT)

            val jsonBody = JSONObject()
            jsonBody.put("image", imageString)

            val request = JsonObjectRequest(
                Request.Method.POST,
                "http://192.168.8.105:1234/caption",
                jsonBody,
                { response -> findViewById<TextView>(R.id.prophecy_view).apply {
                    text = response.get("caption").toString()
                    }
                    progressDialog?.dismiss()
                },
                { error -> findViewById<TextView>(R.id.prophecy_view).apply {
                    text = ""
                    }
                    progressDialog?.dismiss()
                    findViewById<TextView>(R.id.says_tv).apply {
                        text = resources.getString(R.string.sorry)
                    }}
            )

            progressDialog = ProgressDialog(this)
            progressDialog?.setMessage(resources.getString(R.string.thinking))
            progressDialog?.show()
            requestQueue?.add(request)
        }
        else {
            val startActivityIntent = Intent(this, MainActivity::class.java)
            startActivity(startActivityIntent)
        }
    }


}