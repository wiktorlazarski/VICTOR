package com.example.victor

import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import java.io.File

class ProphecyActivity : AppCompatActivity() {
    private var currPhotoPath: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_prophecy)

        val fileName = "photo"
        val storageDirectory = getExternalFilesDir(Environment.DIRECTORY_PICTURES)

        val imageFile = File.createTempFile(fileName, ".jpg", storageDirectory)

        currPhotoPath = imageFile.absolutePath
        val imageUri = FileProvider.getUriForFile(
            this,
            "com.example.victor.fileprovider",
            imageFile
        )

        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri)
        startActivityForResult(cameraIntent, 1)
        // Start camera intent

        // Send image to server

        // Receive caption and display
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 1 && resultCode == RESULT_OK) {
            val bitmap = BitmapFactory.decodeFile(currPhotoPath)

            findViewById<ImageView>(R.id.captured_image_view).apply {
                setImageBitmap(bitmap)
            }
        }
    }


}