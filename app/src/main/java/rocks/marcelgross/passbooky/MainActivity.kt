package rocks.marcelgross.passbooky

import android.app.ActivityOptions
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Pair
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import rocks.marcelgross.passbooky.components.OnCardClickListener
import rocks.marcelgross.passbooky.components.adapter.PassListAdapter
import rocks.marcelgross.passbooky.pkpass.getPasses
import rocks.marcelgross.passbooky.pkpass.save

class MainActivity : AppCompatActivity(), OnCardClickListener {

    private lateinit var passList: RecyclerView
    private lateinit var passListAdapter: PassListAdapter

    override fun onClick(view: View, fileName: String) {
        val intent = Intent(this, CardActivity::class.java)
        intent.putExtra("fileName", fileName)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val options = ActivityOptions.makeSceneTransitionAnimation(
                this,
                Pair.create(view, "pass")
            )
            startActivity(intent, options.toBundle())
        } else {
            startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initRecyclerView()
    }

    override fun onResume() {
        super.onResume()

        passListAdapter.addPasses(getPasses(this))
    }

    private fun initRecyclerView() {
        val layoutManager = LinearLayoutManager(this)
        passList = findViewById(R.id.passList)
        val dividerItemDecoration = DividerItemDecoration(
            passList.context,
            layoutManager.orientation
        )
        passList.addItemDecoration(dividerItemDecoration)
        passListAdapter = PassListAdapter(this)
        passList.adapter = passListAdapter
        passList.setHasFixedSize(true)
        passList.layoutManager = layoutManager
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.save -> {
                iterateOverFiles()
                saveFiles()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun saveFiles() {
        val files = listOf("cbr-business-card.pkpass", "pass.pkpass", "cine.pkpass")
        for (file in files) {
            save(assets.open(file), this)
        }

        iterateOverFiles()
    }

    private fun iterateOverFiles() {
        for (s in getDir("passes", Context.MODE_PRIVATE).listFiles()) {
            android.util.Log.d("mgr12348", s.absolutePath)
            for (listFile in s.listFiles()) {
                android.util.Log.d("mgr12348", listFile.absolutePath)
            }
        }
    }
}
