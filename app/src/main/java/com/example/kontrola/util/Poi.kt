package com.example.kontrola.util

import android.app.Application
import android.os.Environment
import androidx.fragment.app.activityViewModels
import com.example.kontrola.applog
import com.example.kontrola.database.AppDatabase
import com.example.kontrola.model.Error1
import com.example.kontrola.viewmodel.ViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.apache.poi.ss.usermodel.Row
import org.apache.poi.ss.usermodel.WorkbookFactory
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream

object Poi {

    object Positions {
        const val datum = 1
        const val posel = 2
        const val serijska = 3
        const val cee = 4
        const val razdelilec = 5
        const val kabli = 6
        const val razvodnica = 7
        const val finomontaza = 8
        const val kanal = 9
        const val opomba = 10
        const val firstRow = 6
    }

    fun export(application: Application) {
        val appDatabase = AppDatabase.getDatabase(application)
        var toExport: List<Error1>
        //var napaka: Error1

        val excelFileOriginal = File(application.filesDir,"tabela.xlsx")
        val outputFile = File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS),"kontrola.xlsx")

        val c = excelFileOriginal.copyTo(outputFile,true, DEFAULT_BUFFER_SIZE)


        val workBookStream = FileInputStream(excelFileOriginal)
        val myWorkBook = WorkbookFactory.create(workBookStream)

        val mySheet = myWorkBook.getSheetAt(0)

        applog(excelFileOriginal.toString())
        applog(outputFile.toString())
        GlobalScope.launch(Dispatchers.IO){
            toExport = appDatabase.errorDao().getExportedFalse(false)

            var rowIndex = Positions.firstRow

            for (napaka in toExport) {

                napaka.exported = true
                val currentRow = mySheet.getRow(rowIndex)

                val celDatum = currentRow.getCell(Positions.datum)
                val celPosel = currentRow.getCell(Positions.posel)
                val celSerijska = currentRow.getCell(Positions.serijska)
                val celCee = currentRow.getCell(Positions.cee)
                val celRazdelilec = currentRow.getCell(Positions.razdelilec)
                val celRazvodnica = currentRow.getCell(Positions.razvodnica)
                val celFinomontza = currentRow.getCell(Positions.finomontaza)
                val celKanal = currentRow.getCell(Positions.kanal)
                val celOpomba = currentRow.getCell(Positions.opomba)

                celDatum.setCellValue(napaka.datum)
                celPosel.setCellValue(napaka.posel)
                celSerijska.setCellValue(napaka.serijska)



                celOpomba.setCellValue(napaka.opomba)

                rowIndex++
                appDatabase.errorDao().update(napaka)
            }

            val fos = FileOutputStream(outputFile)
            myWorkBook.write(fos)
            fos.close()




        }

    }


}