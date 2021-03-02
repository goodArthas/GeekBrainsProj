package ru.geekbrainsproj.view


import android.Manifest
import android.content.ContentResolver
import android.content.pm.PackageManager
import android.database.Cursor
import android.os.Bundle
import android.provider.ContactsContract
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import ru.geekbrainsproj.PhoneBookRecyclerAdapter
import ru.geekbrainsproj.databinding.PhoneBookActivityBinding


private const val REQUEST_CODE = 665

class MyPhoneBookActivity : AppCompatActivity() {

    lateinit var binding: PhoneBookActivityBinding
    lateinit var recyclerAdapter: PhoneBookRecyclerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = PhoneBookActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initRecycler()

        if (requestPermissionToReadPhoneBook()) {
            getAllContactFromPhoneBook()
        } else {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_CONTACTS), REQUEST_CODE)
        }

    }

    private fun requestPermissionToReadPhoneBook(): Boolean {
        return ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS) == PackageManager.PERMISSION_GRANTED
    }

    private fun initRecycler() {
        recyclerAdapter = PhoneBookRecyclerAdapter(listOfNotNull())
        binding.recyclerV.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.recyclerV.adapter = recyclerAdapter
    }

    private fun getAllContactFromPhoneBook() {

        val pairList = mutableListOf<Pair<String, String>>()

        val cr: ContentResolver = contentResolver
        val cur: Cursor? = cr.query(ContactsContract.Contacts.CONTENT_URI,
                null, null, null, null)

        if (cur != null && cur.count > 0) {
            while (cur.moveToNext()) {
                val id = cur.getString(cur.getColumnIndex(ContactsContract.Contacts._ID))
                val name = cur.getString(cur.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME))

                if (Integer.parseInt(cur.getString(cur.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))) > 0) {

                    val pCur: Cursor? = cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
                            arrayOf(id), null)

                    while (pCur!!.moveToNext()) {
                        val phone = pCur.getString(
                                pCur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))
                        pairList.add(Pair(name, phone))
                    }
                    pCur.close()

                }

            }
        }

        recyclerAdapter.setData(pairList)
        cur?.close()

    }


    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == REQUEST_CODE && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            getAllContactFromPhoneBook()
        } else {
            Toast.makeText(this, "Нужны пермиссионсы", Toast.LENGTH_SHORT).show()
        }

    }

}