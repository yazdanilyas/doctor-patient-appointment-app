package com.cybereast.modernqueue.patient.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.cybereast.modernqueue.R
import com.cybereast.modernqueue.databinding.ActivityPatientDashboardBinding
import com.cybereast.modernqueue.patient.fragments.bookingFragment.BookingFragmentP
import com.cybereast.modernqueue.patient.fragments.moreFragment.MoreFragmentP
import com.cybereast.modernqueue.patient.fragments.searchDoctorFragment.SearchDoctorFragment

class PatientDashboardActivity : AppCompatActivity() {
    private lateinit var mBinding: ActivityPatientDashboardBinding
    private var mFragment: Fragment? = null
    private val fragmentManager = supportFragmentManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityPatientDashboardBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

        val navController = findNavController(R.id.fragmentContainer)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(R.id.menu_search_doc_p, R.id.menu_bookings_p, R.id.menu_more_p)
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        mBinding.bottomNavPatient.setupWithNavController(navController)

//        setBottomNavigationSelector()
//        setUpActionBar("Search Doctor")
//        openFragment(SearchDoctorFragment.newInstance())
    }

    private fun setUpActionBar(title: String) {
        supportActionBar?.title = title
    }

    private fun setBottomNavigationSelector() {
        mBinding.bottomNavPatient.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.menu_search_doc_p -> {
                    setUpActionBar("Search Doctor")
                    openFragment(SearchDoctorFragment.newInstance())
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.menu_bookings_p -> {
                    setUpActionBar("My Bookings")
                    openFragment(BookingFragmentP.newInstance())
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.menu_more_p -> {
                    setUpActionBar("More")
                    openFragment(MoreFragmentP.newInstance())
                    return@setOnNavigationItemSelectedListener true
                }
                else -> {
                    return@setOnNavigationItemSelectedListener false
                }


            }

        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
//        if (mBinding.bottomNavPatient.selectedItemId == R.id.menu_search_doc) {
//            super.onBackPressed();
//        } else {
//            mBinding.bottomNavPatient.selectedItemId = R.id.menu_search_doc
//        }
    }


    private fun openBookingFragment() {
        if (mFragment != null && mFragment !is BookingFragmentP) {
            popUpAllFragmentIncludeThis(BookingFragmentP::class.java.name)
            openFragment(BookingFragmentP.newInstance())
        }
    }

    private fun openMoreFragment() {
        if (mFragment != null && mFragment !is MoreFragmentP) {
            popUpAllFragmentIncludeThis(MoreFragmentP::class.java.name)
            openFragment(MoreFragmentP.newInstance())
        }
    }

    private fun openFragment(fragment: Fragment) {
        ::mFragment.set(fragment)
//        replaceFragment(mBinding.container, fragment)

    }

    private fun replaceFragment(containerId: Int, fragment: Fragment) {
        supportFragmentManager.beginTransaction().replace(containerId, fragment)
//            .addToBackStack(fragment::class.java.name)
            .commit()
    }

    private fun popUpAllFragmentIncludeThis(pClassName: String?) {
        fragmentManager.popBackStack(pClassName, FragmentManager.POP_BACK_STACK_INCLUSIVE)
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.fragmentContainer)
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}