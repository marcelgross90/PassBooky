package rocks.marcelgross.passbooky

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import rocks.marcelgross.passbooky.customComponents.OnCardClickListener
import rocks.marcelgross.passbooky.customComponents.adapter.PassListAdapter
import rocks.marcelgross.passbooky.pkpass.PKPass

class MainActivity : AppCompatActivity(), OnCardClickListener {

    private lateinit var passList: RecyclerView
    private lateinit var passListAdapter: PassListAdapter

    private lateinit var passes: List<Pair<String, PKPass>>

    override fun onClick(fileName: String) {
        val intent = Intent(this, CardActivity::class.java)
        intent.putExtra("fileName", fileName)
        startActivity(intent)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        passes = getTempPasses(this)

        initRecyclerView()
        passListAdapter.addPasses(getTempPasses(this))
        passListAdapter.notifyDataSetChanged()
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
        val fiels = listOf("cbr-businesscard.pkpass", "pass.pkpass", "cine.pkpass")
        for (fiel in fiels) {
            val pass = savePass(assets.open(fiel), this)
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
