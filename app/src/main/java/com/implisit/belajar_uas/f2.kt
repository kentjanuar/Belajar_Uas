package com.implisit.belajar_uas

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.Firebase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import com.google.gson.Gson

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [f2.newInstance] factory method to
 * create an instance of this fragment.
 */
class f2 : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var adapterFilm: adapterFilm
    private  var arFilm: MutableList<film> = mutableListOf()

    val db = Firebase.firestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_f2, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapterFilm = adapterFilm(arFilm)




        val _posterFilm = view.findViewById<EditText>(R.id.posterFilm)
        val _judulFilm = view.findViewById<EditText>(R.id.judulFilm)
        val _deskripsiFilm = view.findViewById<EditText>(R.id.deskripsiFilm)
        val _btnEdit = view.findViewById<Button>(R.id.btnEdit)
        val _cariEdit = view.findViewById<EditText>(R.id.cari)


        val buttonCreate = view.findViewById<Button>(R.id.tambahFilm)

        buttonCreate.setOnClickListener{
            val poster = _posterFilm.text.toString()
            val judul = _judulFilm.text.toString()
            val deskripsi = _deskripsiFilm.text.toString()

            val film = film(judul, deskripsi, poster)
            val sharedPreferences : SharedPreferences = requireActivity().getSharedPreferences("filmPrefs", Context.MODE_PRIVATE)
            val editor : SharedPreferences.Editor = sharedPreferences.edit()
            val gson = Gson()
            val json : String = gson.toJson(film)
            editor.putString(judul, json)
            editor.apply()

        tambahData(db, listOf(film))

        }

        _btnEdit.setOnClickListener {
            val poster = _posterFilm.text.toString()
            val judul = _judulFilm.text.toString()
            val deskripsi = _deskripsiFilm.text.toString()
            val cari = _cariEdit.text.toString()

            editData(db, cari ,judul, deskripsi, poster)
        }
    }

    fun tambahData(db: FirebaseFirestore, films: List<film>) {
        for (film in films) {
            val filmData = hashMapOf(
                "judul" to film.judul,
                "deskripsi" to film.deskripsi,
                "poster" to film.poster
            )

            db.collection("film")
                .document(film.judul)
                .set(filmData)
//                .add(filmData)

                .addOnSuccessListener {
                    arFilm.add(film)
                    adapterFilm.notifyDataSetChanged()
                }
        }


    }

    fun editData(db: FirebaseFirestore,cari: String ,judul: String, deskripsi: String, poster: String) {
        val filmData = hashMapOf(
            "judul" to judul,
            "deskripsi" to deskripsi,
            "poster" to poster
        )

        db.collection("film")
            .document(cari)
            .set(filmData)
            .addOnSuccessListener {
                val updatedFilm = film(judul, deskripsi, poster)
                arFilm.add(updatedFilm)
                adapterFilm.notifyDataSetChanged()
            }
    }




    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment f2.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            f2().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}