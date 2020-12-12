package com.creeping.sarvsahayak.activities

import android.content.Context
import android.content.SharedPreferences
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.FrameLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle

import  androidx.appcompat.widget.Toolbar
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.FragmentManager
import com.creeping.sarvsahayak.AboutusFragment
import com.creeping.sarvsahayak.Design.ModalBottomSheet
import com.creeping.sarvsahayak.FrequentQuestionsFragment
import com.creeping.sarvsahayak.R
import com.creeping.sarvsahayak.fragments.DevelopersFragment
import com.creeping.sarvsahayak.fragments.HomeFragment
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.navigation.NavigationView
import org.w3c.dom.Text
import java.util.*

class HomeScreen : AppCompatActivity() {

    lateinit var drawerLayout: DrawerLayout
    lateinit var coordinatorLayout: CoordinatorLayout
    lateinit var toolbar: Toolbar
    lateinit var frameLayout: FrameLayout
    lateinit var navigationView: NavigationView

    //toolbar change language icon
    lateinit var changeLang: MenuItem
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //call
        loadLocale()

        setContentView(R.layout.activity_home_screen)

        //HOOK
        drawerLayout = findViewById(R.id.DrawerLayout)
        coordinatorLayout = findViewById(R.id.coordinatorLayout)
        toolbar = findViewById(R.id.Toolbar)
        frameLayout = findViewById(R.id.FrameLayout)
        navigationView = findViewById(R.id.NavigationView)

        toolbar()
        //click listner on change language icon


        callHomeByDefault()
        var actionBarDrawerToggle = ActionBarDrawerToggle(
            this@HomeScreen,
            drawerLayout,
            R.string.open_drawer,
            R.string.close_drawer
        )
        drawerLayout.addDrawerListener(actionBarDrawerToggle)
        actionBarDrawerToggle.syncState()


        //click listeners on menuitems
        navigationView.setNavigationItemSelectedListener {

            when (it.itemId) {
                R.id.MenuItemHome -> {
                       callHomeByDefault()
                    drawerLayout.closeDrawers()
                    Toast.makeText(this, "clicked on home", Toast.LENGTH_SHORT).show()
                }

                R.id.MenuItemProfile -> {
                    Toast.makeText(this, "clicked on User's Profile", Toast.LENGTH_SHORT).show()

                }
                R.id.MenuItemQuestions -> {

                    supportFragmentManager.beginTransaction()
                        .replace(
                            R.id.FrameLayout,FrequentQuestionsFragment()
                        )
                        .commit()
                    supportActionBar?.title="Any Questions"
                    drawerLayout.closeDrawers()
                }
                R.id.MenuItemAboutApp -> {
                    supportFragmentManager.beginTransaction()
                        .replace(
                            R.id.FrameLayout,AboutusFragment()
                        )
                       .commit()

                    supportActionBar?.title="About App"

                    drawerLayout.closeDrawers()

                }
                R.id.MenuItemDevelpers -> {

                    supportFragmentManager.beginTransaction()
                        .replace(
                            R.id.FrameLayout,
                            DevelopersFragment()
                        )
                        .commit()
                    supportActionBar?.title="App Developers"


                    drawerLayout.closeDrawers()
                }
            }
            return@setNavigationItemSelectedListener true
        }
    }






    //set up toolbar title

    private fun toolbar() {
        setSupportActionBar(toolbar)
        supportActionBar?.title = "SarvSahayak"
//        //to add hamburger icon
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
//
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        val id = item.itemId
        if (id == android.R.id.home) {
            drawerLayout.openDrawer(GravityCompat.START)
        }
        return super.onOptionsItemSelected(item)
    }
//onback click
    override fun onBackPressed() {
    val frag=supportFragmentManager.findFragmentById(R.id.FrameLayout)
    when(frag){
        !is HomeFragment->callHomeByDefault()
        else-> super.onBackPressed()
    }



    }

    override fun onNavigateUp(): Boolean {
        return super.onNavigateUp()
    }

    fun callHomeByDefault() {
        val fragment = HomeFragment()
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.FrameLayout, fragment)
        transaction.commit()
        supportActionBar?.title="SarvSahyak"

    }

    //used to add icons on toolbar

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.toobar_options, menu)

        //click listner on choose language menuitem in toolbar
        val changeLanguage = menu?.findItem(R.id.menuItemChangeLanguage)
        changeLanguage?.setOnMenuItemClickListener {
            Toast.makeText(this@HomeScreen, "Change lang", Toast.LENGTH_SHORT).show()

            //change language options
            val bottomSheetDialog: BottomSheetDialog = BottomSheetDialog(this)
            bottomSheetDialog.setContentView(R.layout.bottom_dialog)
            bottomSheetDialog.setCanceledOnTouchOutside(true)
            val selectLanguageText =
                bottomSheetDialog.findViewById<TextView>(R.id.txtSelectLanguageText)
            val englishLanguage = bottomSheetDialog.findViewById<TextView>(R.id.txtEnglish)
            val line = bottomSheetDialog.findViewById<View>(R.id.Line)
            val hindiLanguage = bottomSheetDialog.findViewById<TextView>(R.id.txtHindi)

            bottomSheetDialog.show()


            //click lostners
            hindiLanguage?.setOnClickListener {
                Toast.makeText(this, "hindi language", Toast.LENGTH_SHORT).show()
                steLocale("hi")
                recreate()
                bottomSheetDialog.dismiss()

            }
            englishLanguage?.setOnClickListener {
                Toast.makeText(this, "english language", Toast.LENGTH_SHORT).show()
                steLocale("en")
                recreate()
                bottomSheetDialog.dismiss()
            }


            return@setOnMenuItemClickListener true
        }

        return true
    }

    private fun steLocale(language: String) {
        println("caleed 1")
        val locale = Locale(language)
        Locale.setDefault(locale)

        val config: Configuration = Configuration()
        config.locale = locale
        baseContext.resources.updateConfiguration(config, baseContext.resources.displayMetrics)
        //save data
        val editor: SharedPreferences.Editor =
            getSharedPreferences("Settings", Context.MODE_PRIVATE).edit()
        editor.putString("My_Lang", language)
        editor.apply()
    }

    fun loadLocale() {
        println("caleed 2")
        val preferences: SharedPreferences = getSharedPreferences("Settings", Context.MODE_PRIVATE)
        val language: String? = preferences.getString("My_Lang", "")
        if (language != null) {
            steLocale(language)
        }


    }



}