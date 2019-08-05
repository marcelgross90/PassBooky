package rocks.marcelgross.passbooky.database

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import rocks.marcelgross.passbooky.pkpass.PKPass
import rocks.marcelgross.passbooky.pkpass.getPassFields

const val PASSES = "passes"
const val PASSES_ID = "_id"
const val PASSES_BACKGROUND_COLOR = "background_color"
const val PASSES_PRIMARY_COLOR = "primary_color"
const val PASSES_LABEL_COLOR = "label_color"
const val PASSES_PRIMARY = "primary_text"
const val PASSES_SECONDARY = "secondary_text"
const val PASSES_TYPE = "type"

class Database {
    private lateinit var db: SQLiteDatabase

    fun open(context: Context) {
        db = OpenHelper(context).writableDatabase
    }

    fun getPasses(): Cursor? = db.rawQuery(
        """SELECT
            $PASSES_ID,
            $PASSES_BACKGROUND_COLOR,
            $PASSES_PRIMARY_COLOR,
            $PASSES_LABEL_COLOR,
            $PASSES_PRIMARY,
            $PASSES_SECONDARY,
            $PASSES_TYPE
            FROM $PASSES
            ORDER BY $PASSES_ID DESC
        """, null
    )

    fun insertPass(pkPass: PKPass): Long {
        val fields = getPassFields(pkPass) ?: return 0
        val content = pkPass.passContent
        val cv = ContentValues()
        cv.put(PASSES_BACKGROUND_COLOR, content.backgroundColor)
        cv.put(PASSES_PRIMARY_COLOR, content.foregroundColor)
        cv.put(PASSES_LABEL_COLOR, content.labelColor)
        cv.put(PASSES_PRIMARY, fields.primaryFields[0].value)
        cv.put(PASSES_SECONDARY, content.relevantDate.toString())
        cv.put(PASSES_TYPE, pkPass.getPassType().name)
        return db.insert(PASSES, null, cv)
    }

    fun removePass(id: Long) {
        db.delete(PASSES, "$PASSES_ID = ?", arrayOf("$id"))
    }

    private class OpenHelper(context: Context) :
        SQLiteOpenHelper(context, "passes.db", null, 1) {
        override fun onCreate(db: SQLiteDatabase) {
            createPasses(db)
        }

        override fun onUpgrade(
            db: SQLiteDatabase,
            oldVersion: Int,
            newVersion: Int
        ) {
        }
    }
}

private fun createPasses(db: SQLiteDatabase) {
    db.execSQL("DROP TABLE IF EXISTS $PASSES")
    db.execSQL(
        """CREATE TABLE $PASSES (
            $PASSES_ID INTEGER PRIMARY KEY AUTOINCREMENT,
            $PASSES_BACKGROUND_COLOR TEXT,
            $PASSES_PRIMARY_COLOR TEXT,
            $PASSES_LABEL_COLOR TEXT,
            $PASSES_PRIMARY TEXT NOT NULL,
            $PASSES_SECONDARY TEXT,
            $PASSES_TYPE TEXT
        )"""
    )
}
