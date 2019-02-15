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

        // 버튼 클릭 이벤트 예제////////////////////////////////////////////
        val inc = button1.clicks()
            .map { +1 }

        val dec = button2.clicks()
            .map { -1 }

        Observable.merge(inc, dec)
            .scan(0) { acc: Int, value: Int -> acc + value }
            .subscribe { text.text = it.toString() }
        
        // map 예제//////////////////////////////////////////////////////////
        val list = listOf("Alpha", "Beta", "Gamma", "Delta", "Epsilon")

        list.toObservable() // extension function for Iterables
            .subscribeOn(Schedulers.io())
            .filter { it.length >= 5 } // 시간순으로 들어온 데이터를 걸러주는 거
            .map { it -> "Delta" } // 원하는 값으로 변환하는 함수
            .observeOn(Schedulers.newThread())
            .subscribe {
                edit.setText(it)
            }

        edit.textChanges()
            .map { it -> it}
            .subscribe { text.setText(it) }

        val array : Array <String> = arrayOf ( "allow" , "bow" , "" , "dead" )
        Observable.just(array)
            .map { it-> it.size }
            . subscribe ( {
                println (it)
            }, {} )

        //flatMap//////////////////////////////////////////////////////////////
        val array1 : Array <String> = arrayOf ( "allow" , "bow" , "" , "dead" )
        array1.toObservable()
            .filter { it.length > 4 }
            .flatMap { i -> if ( i == "bow" ) Observable.just ( "fuck" ) else Observable.just (i.length) }
            .subscribe ( {
                println (it)
            } , {} )

        //filter //////////////////////////////////////////////////////////////

        //<비슷한 기능을 하는 함수> ///////////////////////////////////////////
        //first(default) : Objservable의 첫 번째 값을 필터함, 만약 값이 없으면 기본값을 리턴
//        last(default) : Objservable의 마지막 값을 필터함, 만약 값이 없으면 기본값을 리턴
//        take(N) : 최초 N개 값을 가져옴
//        takeLast(N) : 마지막 N개 값을 가져옴
//        skip(N) : 최초 N개 값을 건너뜀
//        skipLast(N) : 마지막 N개 값을 건너


        val array2 : Array <String> = arrayOf ( "allow" , "bow" , "" , "dead" )
        array2.toObservable()
            .filter { ! it.isEmpty ( ) }
            .subscribe ( {
                println (it)
            } , {} )


        Observable.just(2, 30, 22, 5, 60, 1)
            .filter { x -> x > 10 }
            .subscribe { x-> Log.d("안녕","item :" + x) }

        // skip //////////////////////////////////////////////////////////////
        val array3 : Array <String> = arrayOf ( "allow" , "bow" , "community" , "dead" )
        array3.toObservable()
            .skip ( 2 )
            .subscribe ( {
                println (it)
            }, {} )

        val array4 : Array <String> = arrayOf ( "allow" , "bow" , "" , "dead" )
        array4.toObservable()
            .take ( 2 )
            .subscribe ( {
                println (it)
            }, {} )







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

