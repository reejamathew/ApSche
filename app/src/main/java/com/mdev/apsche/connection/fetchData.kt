package com.example.jsonexample

import android.os.AsyncTask
import com.mdev.apsche.MainActivity
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL

class fetchData  {
    var data = ""
    var dataParsed = ""
    var singleParsed = ""
    protected  fun doInBackground(vararg voids: Void): Void? {
        try {
            val url = URL("https:jsonkeeper.com/b/QD4L")
            //Create a connection first
            val httpURLConnection = url.openConnection() as HttpURLConnection
            //Create input stream to read data
            val inputStream = httpURLConnection.inputStream
            //Create buffer reader to read data from input stream
            val bufferedReader = BufferedReader(InputStreamReader(inputStream))
            var line: String? = ""
            while (line != null) {
                //Read lines
                line = bufferedReader.readLine()
                data = data + line
            }

//            //Parsing data
//            val JA = JSONArray(data)
//            for (i in 0 until JA.length()) {
//                val JO = JA[i] as JSONObject
//                singleParsed = """
//                    ID:${JO["id"]}
//                    Type:${JO["type"]}
//                    Name:${JO["name"]}
//                    PPU:${JO["ppu"]}
//                    Batters:${JO["batters"]}
//
//                    """.trimIndent()
//                dataParsed = dataParsed + singleParsed
//            }
//        } catch (e: MalformedURLException) {
//            e.printStackTrace()
//        } catch (e: IOException) {
//            e.printStackTrace()
        } catch (e: JSONException) {
            e.printStackTrace()
        }
            return null
        }


//    override fun onPostExecute(aVoid: Void?) {
//        super.onPostExecute(aVoid)
//        MainActivity.textView1.setText(dataParsed)
//    }
}