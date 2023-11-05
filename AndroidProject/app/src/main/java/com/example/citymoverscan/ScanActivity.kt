package com.example.citymoverscan

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.core.app.ActivityCompat
import android.view.SurfaceHolder
import android.view.SurfaceView
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.util.forEach
import com.google.android.gms.vision.CameraSource
import com.google.android.gms.vision.Detector
import com.google.android.gms.vision.Detector.Detections
import com.google.android.gms.vision.barcode.Barcode
import com.google.android.gms.vision.barcode.BarcodeDetector
import java.io.IOException
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.logging.Handler


class ScanActivity : AppCompatActivity() {
    var surfaceView: SurfaceView? = null
    var txtBarcodeValue: TextView? = null
    var backButtonValue: Button?=null
    private var barcodeDetector: BarcodeDetector? = null
    private var cameraSource: CameraSource? = null

    private lateinit var resultTextView: TextView
    private val REQUEST_CAMERA_PERMISSION = 201
    private val handler = android.os.Handler()
    private val scanDuration = 5000L // 5 seconds
    private var isScanning = true

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scan)
        resultTextView = findViewById(R.id.textView2)

        surfaceView = findViewById(R.id.surfaceView)
        txtBarcodeValue = findViewById(R.id.textView2)
         backButtonValue = findViewById<View>(R.id.Inapoi) as Button?

        backButtonValue?.setOnClickListener {
            onBackPressed()
        }


        initScan()
    }
    private fun initScan(){
        barcodeDetector = BarcodeDetector.Builder(this)
            .setBarcodeFormats(Barcode.ALL_FORMATS)
            .build()

        cameraSource = CameraSource.Builder(this, barcodeDetector)
            .setRequestedPreviewSize(1920, 1080)
            .setAutoFocusEnabled(true) //you should add this feature
            .build()

        surfaceView!!.holder.addCallback(object : SurfaceHolder.Callback {
            override fun surfaceCreated(holder: SurfaceHolder) {
                try {
                    if (ActivityCompat.checkSelfPermission(
                            this@ScanActivity,android.Manifest.permission.CAMERA
                        ) === PackageManager.PERMISSION_GRANTED
                    ) {
                        cameraSource?.start(surfaceView!!.holder)
                    } else {
                        ActivityCompat.requestPermissions(
                            this@ScanActivity,
                            arrayOf<String>(android.Manifest.permission.CAMERA),
                            REQUEST_CAMERA_PERMISSION
                        )
                    }
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }

            override fun surfaceChanged(
                holder: SurfaceHolder,
                format: Int,
                width: Int,
                height: Int
            ) {
            }

            override fun surfaceDestroyed(holder: SurfaceHolder) {
                cameraSource?.stop()
            }
        })

        barcodeDetector?.setProcessor(object : Detector.Processor<Barcode> {
            override fun release() {
                Toast.makeText(
                    applicationContext,
                    "To prevent memory leaks barcode scanner has been stopped",
                    Toast.LENGTH_SHORT
                ).show()
            }

            @RequiresApi(Build.VERSION_CODES.O)
            override fun receiveDetections(detections: Detections<Barcode>) {
                val barcodes = detections.detectedItems
                if (barcodes.size() > 0 && isScanning) {
                    for (i in 0 until barcodes.size()) {
                        val barcode = barcodes.valueAt(i)
                        Log.d("Barcode", "Value: ${barcode.displayValue}")
                        runOnUiThread {
                            txtBarcodeValue?.text = barcode.displayValue
                        }

                        var datas = extractTicketData(barcode.displayValue)
                        val pattern = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")
                        val creationDateTime = LocalDateTime.parse(datas?.creationDate, pattern)
                        val expirationDateTime = LocalDateTime.parse(datas?.expirationDate, pattern)

                        if (expirationDateTime <= creationDateTime) {
                            runOnUiThread {
                                txtBarcodeValue?.text = ""
                                showResult(false)
                            }
                        } else {
                            runOnUiThread {
                                txtBarcodeValue?.text = ""
                                showResult(true)
                            }
                        }
                    }

                    // Stop scanning for scanDuration milliseconds
                    isScanning = false
                    handler.postDelayed({
                        isScanning = true
                    }, scanDuration)
                }
            }
        })

    }
    private fun showResult(isValid: Boolean) {
        resultTextView.text = if (isValid) "VALID" else "INVALID"
        resultTextView.setBackgroundColor(if (isValid) resources.getColor(android.R.color.holo_green_light) else resources.getColor(android.R.color.holo_red_light))

        resultTextView.textSize = 180f // Dimensiunea textului în sp
        resultTextView.setPadding(16, 0, 16, 8)

        // Opriți scanarea și configurați redirecționarea către pagina principală după 3 secunde
        isScanning = false
        handler.postDelayed({
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }, 5000) // 3 secunde (3000 milisecunde)
    }
    fun extractTicketData(ticketString: String): TicketData? {
        val ticketRegex = """Ticket Creation Date: (\d{2}/\d{2}/\d{4} \d{2}:\d{2})\nTicket Expiration Date: (\d{2}/\d{2}/\d{4} \d{2}:\d{2})\nValidity: ([A-Z]+)""".toRegex()
        val matchResult = ticketRegex.find(ticketString)

        return if (matchResult != null) {
            val creationDate = matchResult.groupValues[1]
            val expirationDate = matchResult.groupValues[2]
            val validity = matchResult.groupValues[3]
            TicketData(creationDate, expirationDate, validity)
        } else {
            null
        }
    }
    data class TicketData(val creationDate: String, val expirationDate: String, val validity: String)
}