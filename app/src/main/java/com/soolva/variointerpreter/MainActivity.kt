package com.soolva.variointerpreter

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager


import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import java.text.DecimalFormat
import kotlin.math.PI
import kotlin.math.acos

class MainActivity : AppCompatActivity(), SensorEventListener {



        private lateinit var tvX: TextView
        private lateinit var tvY: TextView
        private lateinit var tvZ: TextView
        private lateinit var tvCosTH_2: TextView
        private lateinit var tvThCalcDeg: TextView

        //ON stop save values
        private lateinit var tvXStop: TextView
        private lateinit var tvYStop: TextView
        private lateinit var tvZStop: TextView
        private lateinit var tvCosTH_2Stop: TextView
    private lateinit var tvThCalcDegStop: TextView

        //ON stop calculated values
        private lateinit var tvXCalc: TextView
        private lateinit var tvYCalc: TextView
        private lateinit var tvZCalc: TextView

    lateinit internal var btCalc:Button

    private var mSensorManager: SensorManager? = null
    private var mRotationVector: Sensor? = null

    lateinit internal var st: String
    lateinit internal var st2: String

    var df = DecimalFormat("#")

    //Sensor data read
    var s0: Float= 0F
    var s1: Float= 0F
    var s2: Float= 0F
    var s3: Float= 0F
    var s4: Float= 0F


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        loadElements()
        registerSensor()

        btCalc.setOnClickListener{
            updateStopValues()
            //calcVector()
        }
    }

    private fun updateStopValues() {
        tvXStop.text=s0.toString()
        tvYStop.text=s1.toString()
        tvZStop.text=s2.toString()
        tvCosTH_2Stop.text=s3.toString()
        tvThCalcDegStop.text=cosToDeg(s3)
    }

    private fun calcVector() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onResume() {
        super.onResume()
        mSensorManager!!.registerListener(this, mRotationVector, SensorManager.SENSOR_DELAY_GAME)

        }


    override fun onPause() {
        super.onPause()
        mSensorManager!!.unregisterListener(this, mRotationVector)

    }
    override fun onSensorChanged(sensorEvent: SensorEvent) {

        st2 = ""
        if (sensorEvent.sensor == mRotationVector) {

            s0 = sensorEvent.values[0]
            s1 = sensorEvent.values[1]
            s2 = sensorEvent.values[2]
            s3 = sensorEvent.values[3]


           updateTexts()

        }
    }

    private fun updateTexts() {

        tvX.text=s0.toString()
        tvY.text=s1.toString()
        tvZ.text=s2.toString()
        tvCosTH_2.text=s3.toString()



        tvThCalcDeg.text=cosToDeg(s3)
    }

    private fun cosToDeg(s3Value: Float): CharSequence? {
        var thDeg:Double= 0.0
        if(s2 >= 0){
            thDeg= 360 - (((acos(s3)*2)/PI )*180)}
        else {
            thDeg = (((acos(s3) * 2) / PI) * 180)
        }

        return df.format(thDeg)

    }


    fun registerSensor() {
        mSensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        mRotationVector = mSensorManager!!.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR)
    }

    private fun loadElements() {
        tvX = findViewById(R.id.textViewX) as TextView
        tvY = findViewById(R.id.textViewY) as TextView
        tvZ = findViewById(R.id.textViewZ) as TextView
        tvCosTH_2 = findViewById(R.id.textViewCosTH_2) as TextView
        tvThCalcDeg= findViewById(R.id.textViewTh_Calc_Deg) as TextView

        //Saved values after button click
        tvXStop = findViewById(R.id.textViewXStop)
        tvYStop = findViewById(R.id.textViewYStop)
        tvZStop = findViewById(R.id.textViewZStop)
        tvCosTH_2Stop = findViewById(R.id.textViewCosTH2Stop)
        tvThCalcDegStop = findViewById(R.id.textViewAngleStop)


        //Calculated values
        tvXCalc = findViewById(R.id.textViewXCalcVector)
        tvYCalc = findViewById(R.id.textViewYCalcVector)
        tvZCalc = findViewById(R.id.textViewZCalcVector)

        //Button
        btCalc = findViewById(R.id.buttonCalc)

    }

    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {

    }
}
