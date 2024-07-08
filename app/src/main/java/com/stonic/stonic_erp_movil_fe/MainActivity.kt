package com.stonic.stonic_erp_movil_fe

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {

    private lateinit var sharedPreferences: SharedPreferences
    private val PREFS_NAME = "prefs"
    private val KEY_SELECTED_ITEM = "selected_item"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Verificar si el usuario ya ha iniciado sesión
        val user = FirebaseAuth.getInstance().currentUser
        if (user == null) {
            // Si el usuario no ha iniciado sesión, redirigir a LoginActivity
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish() // Finalizar la actividad actual para que no esté en el stack de actividades
        }

        sharedPreferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE)

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false) // Desactivar el título del Toolbar

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController
        val appBarConfiguration = AppBarConfiguration(setOf(R.id.navigation_sales, R.id.navigation_statistics, R.id.navigation_clients, R.id.navigation_storage))
        setupActionBarWithNavController(navController, appBarConfiguration)

        val bottomNavigationView: BottomNavigationView = findViewById(R.id.bottom_navigation)
        bottomNavigationView.setupWithNavController(navController)

        // Restaurar la última selección del BottomNavigationView
        val lastSelectedItemId = sharedPreferences.getInt(KEY_SELECTED_ITEM, R.id.navigation_sales)
        bottomNavigationView.selectedItemId = lastSelectedItemId

        bottomNavigationView.setOnItemSelectedListener { item ->
            sharedPreferences.edit().putInt(KEY_SELECTED_ITEM, item.itemId).apply()
            navController.navigate(item.itemId)
            true
        }

        // Configurar el Toolbar según la última selección restaurada
        navController.addOnDestinationChangedListener { _, destination, _ ->
            configureToolbarForDestination(destination.id, toolbar)
        }

        // Navegar al destino inicial según la selección restaurada
        navController.navigate(lastSelectedItemId)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar_menu, menu)
        val searchItem: MenuItem? = menu?.findItem(R.id.action_search)
        val searchView = searchItem?.actionView as? SearchView
        searchView?.queryHint = "Buscar..."

        // Configurar el ancho del SearchView para que ocupe todo el espacio disponible
        val layoutParams = Toolbar.LayoutParams(Toolbar.LayoutParams.MATCH_PARENT, Toolbar.LayoutParams.WRAP_CONTENT)
        searchView?.layoutParams = layoutParams

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_add_product -> {
                val intent = Intent(this, ProductCreateActivity::class.java)
                startActivity(intent)
                true
            }
            R.id.action_supplier -> {
                val intent = Intent(this, ProductIndexActivity::class.java)
                startActivity(intent)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun configureToolbarForDestination(destinationId: Int, toolbar: Toolbar) {
        toolbar.menu.clear()
        toolbar.inflateMenu(R.menu.toolbar_menu)

        when (destinationId) {
            R.id.navigation_sales -> {
                showMenuItems(toolbar.menu,
                    R.id.action_menu,
                    R.id.action_search,
                    R.id.action_add_sale,
                    R.id.action_notifications)
            }
            R.id.navigation_statistics -> {
                showMenuItems(toolbar.menu,
                    R.id.action_menu,
                    R.id.action_search,
                    R.id.action_notifications)
            }
            R.id.navigation_clients -> {
                showMenuItems(toolbar.menu,
                    R.id.action_menu,
                    R.id.action_search,
                    R.id.action_notifications)
            }
            R.id.navigation_storage -> {
                showMenuItems(toolbar.menu,
                    R.id.action_menu,
                    R.id.action_search,
                    R.id.action_supplier,
                    R.id.action_add_product,
                    R.id.action_notifications)
            }
            else -> {
                toolbar.menu.clear()
            }
        }
    }

    private fun showMenuItems(menu: Menu, vararg visibleItems: Int) {
        for (i in 0 until menu.size()) {
            val item = menu.getItem(i)
            item.isVisible = visibleItems.contains(item.itemId)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}