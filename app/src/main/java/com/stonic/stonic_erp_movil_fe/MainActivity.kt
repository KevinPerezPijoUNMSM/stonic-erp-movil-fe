package com.stonic.stonic_erp_movil_fe

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController
        val appBarConfiguration = AppBarConfiguration(setOf(R.id.navigation_sales, R.id.navigation_statistics, R.id.navigation_clients, R.id.navigation_storage))
        setupActionBarWithNavController(navController, appBarConfiguration)

        val bottomNavigationView: BottomNavigationView = findViewById(R.id.bottom_navigation)
        bottomNavigationView.setupWithNavController(navController)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.navigation_sales -> {
                    toolbar.title = "Venta"
                    toolbar.menu.clear()
                    toolbar.inflateMenu(R.menu.menu_sales)
                }
                R.id.navigation_statistics -> {
                    toolbar.title = "Estadística"
                    toolbar.menu.clear()
                    toolbar.inflateMenu(R.menu.menu_statistics)
                }
                R.id.navigation_clients -> {
                    toolbar.title = "Clientes"
                    toolbar.menu.clear()
                    toolbar.inflateMenu(R.menu.menu_clients)
                }
                R.id.navigation_storage -> {
                    toolbar.title = "Almacén"
                    toolbar.menu.clear()
                    toolbar.inflateMenu(R.menu.menu_storages)
                }
                else -> {
                    toolbar.title = getString(R.string.app_name)
                    toolbar.menu.clear()
                }
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}