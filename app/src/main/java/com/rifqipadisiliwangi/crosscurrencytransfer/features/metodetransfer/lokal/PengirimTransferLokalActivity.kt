package com.rifqipadisiliwangi.crosscurrencytransfer.features.metodetransfer.lokal

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isVisible
import com.rifqipadisiliwangi.crosscurrencytransfer.data.sqlite.DB_class
import com.rifqipadisiliwangi.crosscurrencytransfer.databinding.ActivityPengirimTransferBinding
import com.rifqipadisiliwangi.crosscurrencytransfer.databinding.ActivityPengirimTransferLokalBinding
import com.rifqipadisiliwangi.crosscurrencytransfer.features.metodetransfer.internasional.PembayaranTransferActivity
import com.rifqipadisiliwangi.crosscurrencytransfer.features.metodetransfer.internasional.SuksesTransferActivity

class PengirimTransferLokalActivity : AppCompatActivity() {

    private lateinit var binding : ActivityPengirimTransferLokalBinding
    var digit_on_screen = StringBuilder()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPengirimTransferLokalBinding.inflate(layoutInflater)
        setContentView(binding.root)
        getPin()
        initializeButtons()

        binding.ivBack.setOnClickListener {
            startActivity(Intent(this, PembayaranTransferLokalActivity::class.java))
        }
    }


    @SuppressLint("Range")
    private fun getPin(){
        val dbhelp = DB_class(applicationContext)
        val db = dbhelp.readableDatabase
        binding.btnSend.setOnClickListener {
            val pin = binding.resultId.text.toString();
            val query="SELECT * FROM pintrans WHERE pin='"+pin+"'"
            val rs=db.rawQuery(query,null)
            if(rs.moveToFirst()){
                val getPinpin = rs.getString(rs.getColumnIndex("pin"))
                rs.close()
                startActivity(Intent(this, SuksesTransferLokalActivity::class.java))
            }
            else{
                val ad = AlertDialog.Builder(this)
                ad.setTitle("Message")
                ad.setMessage("Pin is incorrect!")
                ad.setPositiveButton("Ok", null)
                ad.show()
            }
        }
    }

    private fun initializeButtons() {
        functionalButtons()
        operationalButtons()
        numericalButtons()
    }

    private fun numericalButtons() {

        binding.oneBtn.setOnClickListener {
            appendToDigitOnScreen("1")
        }

        binding.twoBtn.setOnClickListener {
            appendToDigitOnScreen("2")
        }

        binding.threeBtn.setOnClickListener {
            appendToDigitOnScreen("3")
        }

        binding.fourBtn.setOnClickListener {
            appendToDigitOnScreen("4")
        }

        binding.fiveBtn.setOnClickListener {
            appendToDigitOnScreen("5")
        }

        binding.sixBtn.setOnClickListener {
            appendToDigitOnScreen("6")
        }

        binding.sevenBtn.setOnClickListener {
            appendToDigitOnScreen("7")
        }

        binding.eightBtn.setOnClickListener {
            appendToDigitOnScreen("8")
        }

        binding.nineBtn.setOnClickListener {
            appendToDigitOnScreen("9")
        }

        binding.zeroBtn.setOnClickListener {
            appendToDigitOnScreen("0")
        }


    }

    /**
     *  Insert the button been clicked onto the screen so user can see
     *  inputs for the button clicked
     */
    private fun appendToDigitOnScreen(digit: String) {

        // Add each digit to our string builder
        digit_on_screen.append(digit)

        // display it on the screen of our mobile app
        binding.resultId.text = digit_on_screen.toString()
    }

    /**
     *  Initialize the operation keys in our calculator like the
     *  addition key, subtraction key and the likes
     */
    private fun operationalButtons() {

    }

    /**
     * Function to assign operational sign to our math calculations
     */

    /**
     * Handles functional operations in out application like
     * clear button, backspace button and the clear everything button
     */
    private fun functionalButtons() {

        binding.backspaceBtn.setOnClickListener {
            clearDigit()
        }
    }

    /**
     *  This function performs our Math Operation which is then showed on the screen.
     */

    /**
     *  This function remove the last digit on the screen.
     */
    private fun clearDigit() {

        val length = digit_on_screen.length
        digit_on_screen.deleteCharAt(length - 1)
        binding.backspaceBtn.isVisible = length != 0
        binding.resultId.text = digit_on_screen.toString()

    }
}