package com.example.calculator_19k0224

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView


class MainActivity : AppCompatActivity() {
    lateinit var workingview :TextView
    lateinit var resultview :TextView

    private var canaddoperation  = false
    private var candot  = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        workingview=findViewById(R.id.workingstv)
        resultview=findViewById(R.id.ResultsTV)

    }
    fun numberAction(view: View){
        if (view is Button)
        {
            if (view.text==".")
            {
                if(candot)
                {
                    workingview.append(view.text)
                }
                candot=false

            }
            else
            {
                workingview.append(view.text)
            }

            canaddoperation=true
        }
    }
    fun operatorAction(view: View){
        if (view is Button && canaddoperation)
        {
            workingview.append(view.text)
            canaddoperation=false
            candot  = true
        }
    }
    fun allclearAction(view: View){
        workingview.text=""
        resultview.text=""
    }
    fun clearAction(view: View){
        val length=workingview.length()
        if (length>0)
        {
            workingview.text=workingview.text.subSequence(0,length-1)
        }
    }

    fun equalsAction(view: View){
        resultview.text=calculateresults()
    }
    public fun calculateresults(): String
    {
        val digitsoperator = digitsoperator()
        if(digitsoperator.isEmpty())
        {
            return ""
        }
        val multiplyDivision = multiplyDivisionCalculate(digitsoperator)
        if(multiplyDivision.isEmpty()) {
            return ""
        }
        val result =caladdsubresult(multiplyDivision)
        return result.toString()
    }
    private fun caladdsubresult(passedList: MutableList<Any>): Float
    {
        var result = passedList[0] as Float
        for(i in passedList.indices)
        {
            if(passedList[i] is Char && i!=passedList.lastIndex )
            {
                val operator = passedList[i]
                val nextdigit = passedList[i+1] as Float
                if(operator=='+')
                {
                    result+=nextdigit
                }
                if(operator=='-')
                {
                    result-=nextdigit
                }
            }
        }
        return result
    }
    private fun multiplyDivisionCalculate(passedList: MutableList<Any>): MutableList<Any> {
        var list =passedList
        while (list.contains('x') || list.contains('/'))
        {
            list = calmulDiv(list)
        }
        return list
    }

    private fun calmulDiv(passedList: MutableList<Any>): MutableList<Any> {
        val newlist = mutableListOf<Any>()
        var restartindex=passedList.size
        for(i in passedList.indices)
        {
            if(passedList[i] is Char && i!=passedList.lastIndex && i< restartindex)
            {
                val operator = passedList[i]
                val prevdigit = passedList[i-1] as Float
                val nextdigit = passedList[i+1] as Float
                when(operator)
                {
                    'x'->
                    {
                        newlist.add(prevdigit * nextdigit)
                        restartindex =i+1
                    }
                    '/'->
                    {
                        newlist.add(prevdigit / nextdigit)
                        restartindex =i+1
                    }
                    else ->
                    {
                        newlist.add(prevdigit)
                        newlist.add(operator)
                    }
                }
            }
            if(i>restartindex)
                newlist.add(passedList[i])
        }
        return newlist
    }

    private fun digitsoperator(): MutableList<Any>
    {
        val list = mutableListOf<Any>()
        var currentdigit =""
        for(charachter in workingview.text)
        {
            if(charachter.isDigit() || charachter=='.')
            {
                currentdigit+=charachter
            }
            else
            {
                list.add(currentdigit.toFloat())
                currentdigit=""
                list.add(charachter)
            }
        }
        if(currentdigit!="")
        {
            list.add(currentdigit.toFloat())
        }
        return list
    }


}