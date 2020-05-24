package com.itis.mytask.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.arellomobile.mvp.MvpAppCompatActivity
import com.arellomobile.mvp.presenter.InjectPresenter
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.MobileAds
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.auth.FirebaseAuth
import com.itis.mytask.R
import com.itis.mytask.model.TaskDto
import com.itis.mytask.presenter.MainActivityPresenter
import com.itis.mytask.view.interfaces.MainActivityInterface
import com.itis.mytask.view.rv.RVAdapter
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : MvpAppCompatActivity(), MainActivityInterface {
    @InjectPresenter
    lateinit var presenter: MainActivityPresenter
    lateinit var mFirebaseAnalytics: FirebaseAnalytics
    lateinit var dialog: AddDialog
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        dialog = AddDialog()
        presenter.initializeAd(this)

        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this)
        fab_add.setOnClickListener {
            dialog.show(supportFragmentManager, "addDialog")
            val bundle = Bundle()
            bundle.putString(FirebaseAnalytics.Param.METHOD, "Add")
            mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.ADD_TO_CART, bundle)
        }

    }

    override fun setAd(adRequest: AdRequest) {
        adView.loadAd(adRequest)
    }

    override fun setText(text: String) {
        actionBar?.title = text
    }

    override fun setList(list: List<TaskDto>) {
        recycler.adapter = RVAdapter(list)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onResume() {
        presenter.checkList()
        super.onResume()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_logout -> {
                presenter.logout()
                startActivity(LoginActivity.createIntent(this))
            }
            R.id.menu_crash -> {
                throw RuntimeException("Lol bro u ded")
            }
        }
        return super.onOptionsItemSelected(item)
    }

    companion object {
        fun createIntent(context: Context) = Intent(context, MainActivity::class.java)
    }
}
