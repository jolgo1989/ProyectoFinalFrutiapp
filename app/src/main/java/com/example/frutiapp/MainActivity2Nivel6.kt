package com.example.frutiapp

import android.content.ContentValues
import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main_activity2_nivel1.*

class MainActivity2Nivel6 : AppCompatActivity() {
    var score = 0
    var numAleatorio_uno: Int = 0
    var numAleatorio_dos: Int = 0
    var resultado: Int = 0
    var vidas: Int = 3

    var nombre_jugador: String? = null
    lateinit var string_score: String
    lateinit var string_vidas: String

    lateinit var mp: MediaPlayer
    lateinit var mp_great: MediaPlayer
    lateinit var mp_bad: MediaPlayer

    //lateinit var imageViewsigno: ImageView

    val numero = arrayOf(
        "cero", "uno", "dos", "tres", "cuatro", "cinco", "seis", "siete", "ocho", "nueve"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_activity2_nivel6)

        nombre_jugador = intent.getStringExtra("jugador")
        textViewNombre.text = nombre_jugador.toString()

/* Metodo para recuperar el score */
        string_score = getIntent().getStringExtra("score");
        score = Integer.parseInt(string_score)
        textViewScore.text = ("Score: $score")

        string_vidas = intent.getStringExtra("vidas")
        vidas = Integer.parseInt(string_vidas)
        if (vidas == 3) {
            imageViewVidas.setImageResource(R.drawable.tresvidas)
        }
        if (vidas == 2) {
            imageViewVidas.setImageResource(R.drawable.dosvidas)
        }
        if (vidas == 1) {
            imageViewVidas.setImageResource(R.drawable.unavida)
        }


        //Metodo para mostrar icono en acction bar
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setIcon(R.mipmap.ic_launcher)

        // Metodo para iniciar cancion de bienvenida
        mp = MediaPlayer.create(this, R.raw.goats)
        mp.start()
        mp.isLooping

        //Metodo para indicar sonido great y bad
        mp_great = MediaPlayer.create(this, R.raw.wonderful)
        mp_bad = MediaPlayer.create(this, R.raw.bad)

        numAleAtorio()

// i have added toast
        Toast.makeText(this, "Nivel 6 - Sumas,Restas y multiplicaciones ", Toast.LENGTH_SHORT).show()

    }

    fun comparar(view: View) {
        val respuesta = editTextNumberResult.text.toString()
        if (respuesta != "") {

            val respuestaJugador = Integer.parseInt(respuesta)

            if (resultado == respuestaJugador) {
                mp_great.start()
                //Metodo para aunmentar el score
                score++
                textViewScore.text = ("Score: $score")
                //limpiar el campo
                editTextNumberResult.setText("")
                BaseDeDatos()


            } else {

                mp_bad.start()
                //Metodo para decrementar las vidas
                vidas--
                BaseDeDatos()


                when (vidas) {
                    3 -> imageViewVidas.setImageResource(R.drawable.unavida)

                    2 -> {
                        imageViewVidas.setImageResource(R.drawable.dosvidas)
                        Toast.makeText(this, "te quedan 2 manzanas", Toast.LENGTH_SHORT).show()
                    }

                    1 -> {
                        imageViewVidas.setImageResource(R.drawable.unavida)
                        Toast.makeText(this, "te quedan 1 manzanas", Toast.LENGTH_SHORT).show()
                    }

                    else -> {
                        Toast.makeText(this, "has perdido todas tus manzanas", Toast.LENGTH_SHORT)
                            .show()
                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)
                        finish()
                        mp.stop()
                        mp.release()

                    }

                }
                editTextNumberResult.setText("")
            }

            numAleAtorio()


        } else {
            Toast.makeText(this, "Debes escribir tu respuesta", Toast.LENGTH_SHORT).show()
        }
    }

    // Metodo para pasar al siguiente nivel
    fun numAleAtorio() {

        //Metodo para sumar,restar y multiplicar en la activity 6
        if (score <= 60) {
            numAleatorio_uno = (Math.random() * 10).toInt()
            numAleatorio_dos = (Math.random() * 10).toInt()

            if(numAleatorio_uno in 0..3){
                resultado = numAleatorio_uno + numAleatorio_dos;
                imageViewsigno.setImageResource(R.drawable.adicion);
            } else
            if (numAleatorio_uno in 4..7){
                resultado = numAleatorio_uno - numAleatorio_dos;
                imageViewsigno.setImageResource(R.drawable.resta);
            } else {

                resultado = numAleatorio_uno * numAleatorio_dos
                imageViewsigno.setImageResource(R.drawable.multiplicacion)

            }

            if (resultado >= 0) {
                //Metodo para cambiar las imagenes del imageview
                for (i in numero.indices) {
                    val id = resources.getIdentifier(numero[i], "drawable", packageName)
                    if (numAleatorio_uno == i) {
                        imageViewnumber1.setImageResource(id)
                    }
                    if (numAleatorio_dos == i) {
                        imageViewnumber2.setImageResource(id)
                    }
                }
            } else {
                numAleAtorio()
            }


        } else {
            val intent = Intent(this, MainActivity::class.java)
            Toast.makeText(this, "Eres un genio", Toast.LENGTH_SHORT).show()
            startActivity(intent)
            finish()
            mp.stop()
            mp.release()
        }
    }

    fun BaseDeDatos() {
        val admin = AdminSQLiteOpenHelper(this, "BD", null, 1)
        val BD = admin.writableDatabase
        val consulta = BD.rawQuery(
            "select * from puntaje where score = (select max(score) from puntaje)",
            null
        )
        if (consulta.moveToFirst()) {
            val tempNombre = consulta.getString(0)
            val temScore = consulta.getString(1)
            val bestScore = temScore.toInt()

            if (score > bestScore) {
                val modificacion = ContentValues()
                modificacion.put("nombre", nombre_jugador)
                modificacion.put("score", score)
                BD.update("puntaje", modificacion, "score=$bestScore", null)

            }
            BD.close()
        } else {

            val insertar = ContentValues()
            insertar.put("nombre", nombre_jugador)
            insertar.put("score", score)
            BD.insert("puntaje", null, insertar)
            BD.close()
        }
    }

    override fun onResume() {
        super.onResume()
        mp.start()
    }

    override fun onPause() {
        super.onPause()
        mp.pause()
    }

    override fun onBackPressed() {

    }
}