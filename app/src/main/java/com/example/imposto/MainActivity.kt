package com.example.imposto

import android.os.Bundle
import android.view.View
import android.widget.Toast
//import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.imposto.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), View.OnClickListener {
    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.buttonOne.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        if (v.id == R.id.button_one) {
            var salario: Float = 0F
            var gastos: Float = 0F
            var dependentes: Int = 0
            try {
                salario = binding.salario.text.toString().toFloat()
                gastos = binding.gastos.text.toString().toFloat()
                dependentes = binding.dependencias.text.toString().toInt()

                if (salario <= 0 || gastos < 0 || dependentes < 0) {
                    throw Exception()
                }
            } catch (e: Exception) {
                Toast.makeText(
                    this,
                    "Preencha todos os campos corretamente", Toast.LENGTH_SHORT
                ).show()
                return
            }
            var aliquota: Float = 0F
            var deducao: Float = 0F
            var base: Float = salario - (dependentes * 189.59F) - gastos
            // Quando eu clicar no nutao de calcualr
            if (base <= 2428.80) {
                binding.frase.text = "Não precisa pagar imposto!"
                return
            }
            if (base > 2428.80 && base <= 2826.65) {
                aliquota = 7.5F
                deducao = 182.16F
            }

            if (base > 2826.66 && base <= 3751.05) {
                aliquota = 15F
                deducao = 394.16F
            }

            if (base > 3751.06 && base <= 4664.68) {
                aliquota = 22.5F
                deducao = 675.49F
            }

            if (salario > 4664.68) {
                aliquota = 27.5F
                deducao = 908.73F
            }

            var IR = (base * (aliquota / 100)) - deducao
            binding.frase.text = "R$ %.2f".format(IR).toString().replace(".", ",")

        }

    }
}