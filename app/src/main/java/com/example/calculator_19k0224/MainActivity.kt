package com.example.calculator_19k0224

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView


class MainActivity : AppCompatActivity() {
    lateinit var workingstv :TextView
    lateinit var resultstv :TextView

    private var canaddoperation  = false
    private var candot  = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        workingstv=findViewById(R.id.workingstv)
        resultstv=findViewById(R.id.ResultsTV)

    }
    fun numberAction(view: View){
        if (view is Button)
        {
            if (view.text==".")
            {
                if(candot)
                {
                    workingstv.append(view.text)
                }
                candot=false

            }
            else
            {
                workingstv.append(view.text)
            }

            canaddoperation=true
        }
    }
    fun operatorAction(view: View){
        if (view is Button && canaddoperation)
        {
            workingstv.append(view.text)
            canaddoperation=false
            candot  = true
        }
    }
    fun allclearAction(view: View){
        workingstv.text=""
        resultstv.text=""
    }
    fun clearAction(view: View){
        val length=workingstv.length()
        if (length>0)
        {
            workingstv.text=workingstv.text.subSequence(0,length-1)
        }
    }
    fun equalsAction(view: View){
        resultstv.text=calculateresults()
    }
    private fun calculateresults(): String
    {
        val digitsoperator = digitsoperator()
        if(digitsoperator.isEmpty())
        {
            return ""
        }
        val timesDivision = timesDivisionCalculate(digitsoperator)
        if(timesDivision.isEmpty()) {
            return ""
        }
        val result =caladdsubresult(timesDivision)
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

    private fun timesDivisionCalculate(passedList: MutableList<Any>): MutableList<Any> {
        var list =passedList
        while (list.contains('x') || list.contains('/'))
        {
            list = caltimesDiv(list)
        }
        return list
    }

    private fun caltimesDiv(passedList: MutableList<Any>): MutableList<Any> {
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
        for(charachter in workingstv.text)
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