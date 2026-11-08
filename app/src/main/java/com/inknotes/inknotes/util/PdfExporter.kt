package com.inknotes.inknotes.util

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.graphics.pdf.PdfDocument
import android.os.Environment
import com.inknotes.inknotes.data.model.Page
import com.inknotes.inknotes.data.model.Stroke
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

class PdfExporter(private val context: Context) {

    fun exportNoteToPdf(
        pages: List<Page>,
        strokesByPage: Map<Long, List<Stroke>>,
        fileName: String = "note_${System.currentTimeMillis()}.pdf"
    ): File? {
        return try {
            val pdfDocument = PdfDocument()
            
            pages.forEach { page ->
                val strokes = strokesByPage[page.id] ?: emptyList()
                
                val pdfPage = pdfDocument.startPage(
                    PdfDocument.PageInfo.Builder(595, 842, 1).create()
                )
                
                drawPageOnPdf(pdfPage.canvas, page, strokes)
                
                pdfDocument.finishPage(pdfPage)
            }
            
            val file = File(getPdfDirectory(), fileName)
            FileOutputStream(file).use { outputStream ->
                pdfDocument.writeTo(outputStream)
            }
            
            pdfDocument.close()
            file
        } catch (e: IOException) {
            e.printStackTrace()
            null
        }
    }

    private fun drawPageOnPdf(
        canvas: Canvas,
        page: Page,
        strokes: List<Stroke>
    ) {
        val backgroundPaint = Paint().apply {
            color = Color.WHITE
            style = Paint.Style.FILL
        }
        canvas.drawRect(0f, 0f, 595f, 842f, backgroundPaint)
        
        strokes.forEach { stroke ->
            val path = Path()
            val points = stroke.points.chunked(2).mapIndexed { index, pair ->
                android.graphics.PointF(pair[0], pair[1])
            }
            
            if (points.isNotEmpty()) {
                path.moveTo(points[0].x, points[0].y)
                for (i in 1 until points.size) {
                    val prev = points[i - 1]
                    val curr = points[i]
                    path.quadTo(
                        (prev.x + curr.x) / 2,
                        (prev.y + curr.y) / 2,
                        curr.x,
                        curr.y
                    )
                }
            }
            
            val paint = Paint().apply {
                color = stroke.color
                strokeWidth = stroke.width
                style = Paint.Style.STROKE
                strokeCap = Paint.Cap.ROUND
                strokeJoin = Paint.Join.ROUND
                isAntiAlias = true
                isDither = true
            }
            
            canvas.drawPath(path, paint)
        }
    }

    private fun getPdfDirectory(): File {
        val downloadsDir = Environment.getExternalStoragePublicDirectory(
            Environment.DIRECTORY_DOWNLOADS
        )
        val inkNotesDir = File(downloadsDir, "InkNotes")
        if (!inkNotesDir.exists()) {
            inkNotesDir.mkdirs()
        }
        return inkNotesDir
    }

    fun sharePdf(file: File, context: Context) {
        val shareIntent = android.content.Intent().apply {
            action = android.content.Intent.ACTION_SEND
            type = "application/pdf"
            putExtra(android.content.Intent.EXTRA_STREAM, 
                android.net.Uri.fromFile(file))
        }
        context.startActivity(
            android.content.Intent.createChooser(
                shareIntent,
                "Share PDF"
            )
        )
    }
}