package jp.toastkid.planning_poker

import android.content.DialogInterface
import android.content.pm.ActivityInfo
import android.content.res.Configuration
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.FragmentTransaction
import android.support.v4.content.ContextCompat
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*

/**
 * @author toastkidjp
 */
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initToolbar()

        initFragment()

        setOrientation()
    }

    private fun initToolbar() {
        val toolbar = findViewById(R.id.toolbar) as Toolbar
        toolbar.setTitle(R.string.app_name)
        toolbar.setTitleTextColor(ContextCompat.getColor(this, R.color.toolbar_text))
        toolbar.inflateMenu(R.menu.menu_main)
        toolbar.setOnMenuItemClickListener { item -> this@MainActivity.onOptionsItemSelected(item) }
        toolbar.setNavigationIcon(R.drawable.ic_close)
        toolbar.setNavigationOnClickListener { v ->
            Snackbar.make(v, "onClick", Snackbar.LENGTH_SHORT).show()
            finish()
        }
    }

    private fun initFragment() {
        val fragment = SelectionFragment()
        val transaction = supportFragmentManager.beginTransaction()
        transaction.add(R.id.fragment_container, fragment)
        transaction.commit()
    }

    private fun setOrientation() {

        when (resources.configuration.orientation) {
            Configuration.ORIENTATION_PORTRAIT ->
                requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

            Configuration.ORIENTATION_LANDSCAPE ->
                requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        }
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            R.id.action_help -> {
                AlertDialog.Builder(this)
                        .setTitle(R.string.menu_title_how2use)
                        .setMessage(R.string.description_how2use)
                        .setCancelable(true)
                        .setPositiveButton(R.string.close) { dialog, which -> dialog.dismiss() }
                        .show()
                return true
            }
            R.id.action_close -> {
                finish()
                return true
            }
            R.id.privacy_policy -> {
                AlertDialog.Builder(this)
                        .setTitle(R.string.title_privacy_policy)
                        .setMessage(R.string.message_privacy_policy)
                        .setCancelable(true)
                        .setPositiveButton(R.string.close) { dialog, which -> dialog.dismiss() }
                        .show()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

}
