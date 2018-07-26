package gggroup.com.baron.paper

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.ramotion.paperonboarding.PaperOnboardingFragment
import com.ramotion.paperonboarding.PaperOnboardingPage
import gggroup.com.baron.R
import gggroup.com.baron.authentication.signin.SignInActivity
import kotlinx.android.synthetic.main.activity_paper_onboarding.*
import java.util.ArrayList

class PaperOnBoardingActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_paper_onboarding)

        val fragmentManager = supportFragmentManager

        val onBoardingFragment = PaperOnboardingFragment.newInstance(getDataForOnBoarding())

        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.add(R.id.fragment_container, onBoardingFragment)
        fragmentTransaction.commit()

        tv_skip.setOnClickListener {
            startActivity(Intent(this, SignInActivity::class.java))
        }

        onBoardingFragment.setOnRightOutListener {
            startActivity(Intent(this, SignInActivity::class.java))
        }

    }

    private fun getDataForOnBoarding(): ArrayList<PaperOnboardingPage>? {
        val scr1 = PaperOnboardingPage("Hotels", "All hotels and hostels are sorted by hospitality rating",
                Color.parseColor("#678FB4"), R.drawable.hotels, R.drawable.key)
        val scr2 = PaperOnboardingPage("Banks", "We carefully verify all banks before add them into the app",
                Color.parseColor("#65B0B4"), R.drawable.banks, R.drawable.wallet)
        val scr3 = PaperOnboardingPage("Stores", "All local stores are categorized for your convenience",
                Color.parseColor("#9B90BC"), R.drawable.stores, R.drawable.shopping_cart)

        val elements = ArrayList<PaperOnboardingPage>()
        elements.add(scr1)
        elements.add(scr2)
        elements.add(scr3)
        return elements
    }
}