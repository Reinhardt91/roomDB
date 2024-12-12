package nit2x.paba.projectroom

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import nit2x.paba.projectroom.database.daftarBelanja
import nit2x.paba.projectroom.database.daftarBelanjaDB
import nit2x.paba.projectroom.helper.DataHelper

class TambahDaftar : AppCompatActivity() {
    var DB = daftarBelanjaDB.getDatabase(this)
    private lateinit var _etItem: EditText
    private lateinit var _etJumlah: EditText
    var tanggal = DataHelper.getCurrentDate()

    var iID : Int = 0
    var iAddEdit : Int = 0

    private lateinit var btnTambah: Button
    private lateinit var btnUpdate: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_tambahdaftar)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        _etItem = findViewById(R.id.etItem)
        _etJumlah = findViewById(R.id.etJumlah)
        btnTambah = findViewById<Button>(R.id.btnTambah)
        btnUpdate = findViewById<Button>(R.id.btnUpdate)
        iID = intent.getIntExtra("id", 0)
        iAddEdit = intent.getIntExtra("addEdit",0)

        if(iAddEdit == 0) {
            btnTambah.visibility= View.VISIBLE
            btnUpdate.visibility = View.GONE
            _etItem.isEnabled = true


        } else {
            btnTambah.visibility= View.GONE
            btnUpdate.visibility = View.VISIBLE
            _etItem.isEnabled = false

            CoroutineScope(Dispatchers.IO).async {
                val item = DB.fundafarBelanjaDAO().getItem(iID)
                _etItem.setText(item.item)
                _etJumlah.setText(item.jumlah)
            }
        }
        btnTambah.setOnClickListener {
            CoroutineScope(Dispatchers.IO).async {
                DB.fundafarBelanjaDAO().insert(
                    daftarBelanja(
                        tanggal = tanggal,
                        item = _etItem.text.toString(),
                        jumlah = _etJumlah.text.toString()
                    )
                )
            }
        }
        btnUpdate.setOnClickListener {
            CoroutineScope(Dispatchers.IO).async {
                DB.fundafarBelanjaDAO().update(
                    isi_tanggal = tanggal,
                    isi_item = _etItem.text.toString(),
                    isi_jumlah = _etJumlah.text.toString(),
                    pilihid = iID
                )
            }
        }
    }
}