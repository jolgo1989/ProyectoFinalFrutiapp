package com.example.frutiapp

import android.app.Activity
import android.content.Intent
import android.database.Cursor
import android.media.MediaPlayer
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private var numeroAleatorio = ((Math.random() * 10) + 1).toInt()
    lateinit var mp: MediaPlayer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Metodo para motrar el icono en el acctionbar
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setIcon(R.mipmap.ic_launcher)

        //Metodo para que la imagen del MainActivity se aleatoria

        val id: Int

        if (numeroAleatorio == 0 || numeroAleatorio == 10) {
            id = resources.getIdentifier("mango", "drawable", packageName)
            imageViewPersonaje.setImageResource(id)
        } else
            if (numeroAleatorio == 1 || numeroAleatorio == 9) {
                id = resources.getIdentifier("fresa", "drawable", packageName)
                imageViewPersonaje.setImageResource(id)
            } else
                if (numeroAleatorio == 2 || numeroAleatorio == 8) {
                    id = resources.getIdentifier("manzana", "drawable", packageName)
                    imageViewPersonaje.setImageResource(id)
                } else
                    if (numeroAleatorio == 3 || numeroAleatorio == 7) {
                        id = resources.getIdentifier("sandia", "drawable", packageName)
                        imageViewPersonaje.setImageResource(id)
                    } else
                        if (numeroAleatorio == 4 || numeroAleatorio == 5 || numeroAleatorio == 6) {
                            id = resources.getIdentifier("uva", "drawable", packageName)
                            imageViewPersonaje.setImageResource(id)
                        }

        //Metodo para llamar la base de datos
        val admin = AdminSQLiteOpenHelper(this, "BD",null,1)
        //Metodo para abrir la base de datos
        val BD = admin.writableDatabase
        //Metodo para consultar
        val consulta = BD.rawQuery(
            "select * from puntaje where score = (select max(score) from puntaje)",null
        )
        //Metodo para consultar si existen datos
        if (consulta.moveToFirst()){
            val tempNombre = consulta.getString(0)
            val temScore = consulta.getString(1)
            textViewScore.text = ("Record: $tempNombre de $temScore")
            //Metodo para cerrar la base de datos
            BD.close()
        }else{
            BD.close()
        }



// Metodo para iniciar cancion de bienvenida
        mp = MediaPlayer.create(this, R.raw.alphabet_song)
        mp.start()
        mp.isLooping
    }

    // Metodo para que el usuario ingrese el nombre e inicie el juego
    fun onButtonClicked(view: View) {
        val nombre = editTextName.text.toString()

        if (nombre != "") {
            //Metodo para detener la cación una vez se ingrese el nombre
            mp.stop()
            mp.release()

            val intent = Intent(this, MainActivity2Nivel1::class.java)
            intent.putExtra("Clave", nombre)
            startActivity(intent)
        } else {
            Toast.makeText(this, "Debe escribir su nombre", Toast.LENGTH_SHORT).show()

            //metodo para que el teclado se active si el usuario no ingresa su nombre
            editTextName.requestFocus()
            val imm = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.showSoftInput(editTextName, InputMethodManager.SHOW_IMPLICIT)

        }

    }
    //metodo para controlar el boton atras del telefono
    override fun onBackPressed() {
        Toast.makeText(this, "Metodo para controlar el boton", Toast.LENGTH_SHORT).show()

    }

}
