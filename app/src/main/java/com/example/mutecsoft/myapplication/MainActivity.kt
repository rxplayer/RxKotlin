package com.example.mutecsoft.myapplication

import android.app.DatePickerDialog
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.jakewharton.rxbinding3.view.clicks
import com.jakewharton.rxbinding3.widget.textChanges
import io.reactivex.Observable
import io.reactivex.rxkotlin.toObservable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val inc = button1.clicks()
            .map { +1 }

        val dec = button2.clicks()
            .map { -1 }

        Observable.merge(inc, dec)
            .scan(0) { acc: Int, value: Int -> acc + value }
            .subscribe { text.text = it.toString() }
        

        val list = listOf("Alpha", "Beta", "Gamma", "Delta", "Epsilon")

        list.toObservable() // extension function for Iterables
            .subscribeOn(Schedulers.io())
            .filter { it.length >= 5 }
            .map { it -> "Delta" }
            .observeOn(Schedulers.newThread())
            .subscribe {
                edit.setText(it)
            }


        Observable.just(2, 30, 22, 5, 60, 1)
            .filter { x -> x > 10 }
            .subscribe { x-> Log.d("안녕","item :" + x) }


        val c = Calendar.getInstance()
        c.add(Calendar.YEAR, -18)
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)


        val datePickerBirth = DatePickerDialog(this/*this.requireContext()*/, DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->


        },year,month,day)

        button.setOnClickListener {
            datePickerBirth.updateDate(year,month,day)
            datePickerBirth.show()

        }
    }
}

