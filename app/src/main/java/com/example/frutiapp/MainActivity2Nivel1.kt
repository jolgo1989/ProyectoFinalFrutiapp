package com.example.frutiapp

import android.media.MediaPlayer
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main_activity2_nivel1.*

class MainActivity2Nivel1 : AppCompatActivity() {
    var score = 0
    var numAleatorio_uno: Int = 0
    var numAleatorio_dos: Int = 0
    var resultado: Int = 0
    var vidas: Int = 3

    //var nombre_jugador: String? = null
    var string_score: kotlin.String? = null
    var string_vidas: kotlin.String? = null

    lateinit var mp: MediaPlayer
    lateinit var mp_great: MediaPlayer
    lateinit var mp_bad: MediaPlayer

    val numero = arrayOf(
        "cero", "uno", "dos", "tres", "cuatro", "cinco", "seis", "siete", "ocho", "nueve"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_activity2_nivel1)

        val intent = intent
        val name = intent.getStringExtra("Clave")
        textViewNombre.text = ("Jugador: $name")

        //Metodo para mostrar icono en acction bar
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setIcon(R.mipmap.ic_launcher)

        // Metodo para iniciar cancion de bienvenida
        mp = MediaPlayer.create(this, R.raw.goats)
        mp.start()
        mp.isLooping

        //Metodo para indicar sonido great y bad
        mp_great = MediaPlayer.create(this,R.raw.wonderful)
        mp_bad = MediaPlayer.create(this,R.raw.bad)

// i have added toast
        Toast.makeText(this, "Nivel 1 - Sumas basicas", Toast.LENGTH_SHORT).show()

    }
}