package nl.saxion.ein1b2;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbAdapter {
	private DatabaseHelper dbHelper;
	private SQLiteDatabase mydb;
	private final Context con;
	
	public DbAdapter(Context c){
		this.con = c;
	}
	
	public DbAdapter open() throws SQLException {
		dbHelper = new DatabaseHelper(con);
		mydb = dbHelper.getWritableDatabase();
		return this;
	}
	
	public void close() {
		if(mydb != null){
			dbHelper.close();
		}
	}
	
	public void insertVakkenpakket(String naam, CustomDate startdatum, CustomDate einddatum){
		ContentValues values = new ContentValues();
		values.put("naam", naam);
		values.put("startdatum", startdatum.toStringForDB());
		values.put("einddatum", einddatum.toStringForDB());
		long newPeriode = mydb.insert("periodes", null, values);
	}
	
	
	
	public static class DatabaseHelper extends SQLiteOpenHelper {
		private static final String SET_PRAGMA = "PRAGMA foreign_keys = 1;";
		private static final String CREATE_TBL_PERIODE = "CREATE TABLE periode ("
				+ "id INTEGER PRIMARY KEY AUTOINCREMENT"
				+ ", naam varchar(255) NOT NULL"
				+ ", startdatum DATETIME NOT NULL"
				+ ", einddatum DATETIME);";
		
		private static final String CREATE_TBL_VAK = "CREATE TABLE vak ("
				+ "id INTEGER PRIMARY KEY AUTOINCREMENT"
				+ ", periode_id INTEGER NOT NULL"
				+ ", naam VARCHAR(255) NOT NULL"
				+ ", doelcijfer double"
				+ ", iscijfer TINYINT"
				+ ", FOREIGN KEY(periode_id) REFERENCES periode(id));";
				
		private static final String CREATE_TBL_TOETS = "CREATE TABLE toets ("
				+ "id INTEGER PRIMARY KEY AUTOINCREMENT"
				+ ", vak_id INTEGER NOT NULL"
				+ ", toetstype_id INTEGER NOT NULL"
				+ ", beschrijving VARCHAR(255)"
				+ ", datumtijd DATETIME"				
				+ ", cijfer DOUBLE"				
				+ ", FOREIGN KEY(vak_id) REFERENCES vak(id)"
				+ ", FOREIGN KEY(toetstype_id) REFERENCES toetstype(id));";
		
		private static final String CREATE_TBL_TOETSTYPE = "CREATE TABLE toetstype ("
				+ "id INTEGER PRIMARY KEY AUTOINCREMENT"
				+ ", naam VARCHAR(255)"
				+ ", som INTEGER);";
		
		private static final String CREATE_TBL_DEELTOETS = "CREATE TABLE deeltoets ("
				+ "id INTEGER PRIMARY KEY AUTOINCREMENT"
				+ ", toets_id_hoofd INTEGER NOT NULL"
				+ ", toets_id_sub INTEGER NOT NULL"
				+ ", weging INTEGER NOT NULL" //Deelcijfer
				+ ", FOREIGN KEY(toets_id_hoofd) REFERENCES toets(id)"
				+ ",FOREIGN KEY(toets_id_sub) REFERENCES toets(id));";
		
		private static final String CREATE_TBL_HERINNERING = "CREATE TABLE herinnering ("
				+ "id INTEGER PRIMARY KEY AUTOINCREMENT"
				+ ", toets_id INTEGER NOT NULL"
				+ ", datumtijd DATETIME NOT NULL"	
				+ ", beschrijving VARCHAR(255)"				
				+ ", FOREIGN KEY(toets_id) REFERENCES toets(id));";
		
		private static final String CREATE_TBL_INSTELLINGEN = "CREATE TABLE instellingen ("
				+ "id INTEGER PRIMARY KEY AUTOINCREMENT"
				+ ", wachtwoord varchar(255));";
		
		private static final String CREATE_TBL_TODO = "CREATE TABLE todo ("
				+ "id INTEGER PRIMARY KEY AUTOINCREMENT"
				+ ", titel varchar(255)"
				+ ", beschrijving varchar(255)"
				+ ", afgerond TINYINT);";
		
		private static final String INSERT_TOETSTYPE= "INSERT INTO toetstype (naam, som) SELECT ('Proefwerk', 3) UNION ALL SELECT ('Schiftelijke Overhoring', 1);";
		
//		private static final String CREATE_TBL_ACHIEVEMENTS = "CREATE TABLE achievements ("
//				+ "id INTEGER PRIMARY KEY AUTOINCREMENT"
//				+ ", titel varchar(255)"
//				+ ", beschrijving varchar(255)"
//				+ ", punten INTEGER"
//				+ ", voor varchar(255)"
//				+ ", cijfer DOUBLE"
//				+ ", aantal INT)";
				

		public DatabaseHelper(Context context) {
			super(context, "watmoetikhalen", null, 1);
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			db.execSQL(CREATE_TBL_PERIODE);		
			db.execSQL(CREATE_TBL_VAK);
			db.execSQL(CREATE_TBL_TOETSTYPE);
			db.execSQL(CREATE_TBL_TOETS);
			db.execSQL(CREATE_TBL_DEELTOETS);
			db.execSQL(CREATE_TBL_HERINNERING);
			db.execSQL(CREATE_TBL_INSTELLINGEN);
			db.execSQL(CREATE_TBL_TODO);
			
			db.execSQL(SET_PRAGMA);
			
			db.execSQL("");
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			// TODO Auto-generated method stub
			
		}
		
	}

}