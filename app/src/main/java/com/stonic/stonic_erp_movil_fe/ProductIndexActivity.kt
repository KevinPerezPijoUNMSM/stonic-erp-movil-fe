package com.stonic.stonic_erp_movil_fe

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.stonic.stonic_erp_movil_fe.model.Producto

class ProductIndexActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ProductAdapter
    private lateinit var searchBar: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_index)

        // Configurar Toolbar
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        // Manejar el evento de clic en el icono de retroceso del Toolbar
        toolbar.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        // Configurar el callback para el botón de retroceso del sistema
        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                finish() // Termina la actividad actual
            }
        })

        // Configurar RecyclerView
        recyclerView = findViewById(R.id.recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Simular lista de productos
        val products = listOf(
            Producto(1, "Producto 1", "2023-01-01", "2023-01-02", "COD001", 1, "500g", 1, "https://example.com/image1.jpg", "Descripción del Producto 1"),
            Producto(2, "Producto 2", "2023-01-03", "2023-01-04", "COD002", 2, "1L", 2, "https://example.com/image2.jpg", "Descripción del Producto 2"),
            Producto(3, "Producto 3", "2023-01-05", "2023-01-06", "COD003", 3, "250g", 3, "https://example.com/image3.jpg", "Descripción del Producto 3")
        )

        adapter = ProductAdapter(products)
        recyclerView.adapter = adapter

        // Configurar barra de búsqueda
        searchBar = findViewById(R.id.search_bar)
        searchBar.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {}

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                adapter.filter.filter(s)
            }
        })
    }
}