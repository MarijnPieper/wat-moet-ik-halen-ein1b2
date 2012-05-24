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

	public long insertToetsToevoegen(Toets t, int vakid){
		ContentValues values = new ContentValues();
		values.put("vak_id", vakid);
		values.put("toetstype_id", t.getToetstype_id());
		values.put("datumtijd" , t.getDatum().toStringForDB());
		values.put("cijfer", t.getCijfer());
		long newToetsToevoegen = mydb.insert("Toets" , null, values);
		
		
		return newToetsToevoegen;
	}

	public long insertVakkenpakket(Periode p){
		ContentValues values = new ContentValues();
		values.put("naam", p.getNaam());
		values.put("startdatum", p.getStartDatum().toStringForDB());
		values.put("einddatum", p.getEindDatum().toStringForDB());
		long newPeriode = mydb.insert("periode", null, values);

		for (Vak v: p.getVakken())
		{
			ContentValues vak = new ContentValues();
			vak.put("periode_id", newPeriode);
			vak.put("naam", v.getNaam());
			vak.put("iscijfer", v.isIscijfer());
			long newVak = mydb.insert("vak", null, vak);
		}
		return newPeriode;
	}

	public ArrayList<Periode> selectVakkenpakketten(){
		ArrayList<Periode> periode = new ArrayList<Periode>();

		Cursor cursor = mydb.rawQuery("SELECT * FROM Periode", null);
		cursor.moveToFirst();

		while (cursor.isAfterLast() == false) {
			Periode p = new Periode(Integer.parseInt(cursor.getString(0)), cursor.getString(1), cursor.getString(2),cursor.getString(3));
			periode.add(p);
			cursor.moveToNext();
		}

		return periode;
	}

	public ArrayList<TypeToets> selectTypeToetsen(){
		ArrayList<TypeToets> types = new ArrayList<TypeToets>();

		Cursor cursor = mydb.rawQuery("SELECT * FROM toetstype", null);
		cursor.moveToFirst();

		while (cursor.isAfterLast() == false) {
			TypeToets type = new TypeToets(cursor.getInt(0), cursor.getString(1), cursor.getInt(2));
			types.add(type);
			cursor.moveToNext();
		}

		return types;
	}
	
	public String selectTypeToets(int id){
		String[] args = new String[]{String.valueOf(id)};
		Cursor cursor = mydb.rawQuery("SELECT naam FROM toetstype WHERE id=?", args);
		boolean gevonden = cursor.moveToFirst();
		
		String resultaat = "";
		if (gevonden) resultaat = cursor.getString(0);
		
		return resultaat;
	}
	
	
	
	public ArrayList<Toets> selectToetsen(int vakid){
		ArrayList<Toets> toetsen = new ArrayList<Toets>();
		String[] args = new String[]{String.valueOf(vakid)};
		

		Cursor cursor = mydb.rawQuery("SELECT id, toetstype_id, beschrijving, datumtijd, cijfer"
				+ " FROM toets WHERE vak_id=?", args);
		cursor.moveToFirst();

		while (cursor.isAfterLast() == false) {
			Toets toets = new Toets(cursor.getInt(0), cursor.getInt(1), cursor.getString(2), new CustomDate(cursor.getString(3)), cursor.getInt(4));
			toetsen.add(toets);
			cursor.moveToNext();
		}
		return toetsen;
	}

	public double selectGemCijferVak(int VakID) {
		Double TotalCijfer = new Double(0);
		Double Count = new Double(0);
		String[] args = new String[]{String.valueOf(VakID)};
		Cursor cursor = mydb.rawQuery("SELECT * FROM toets WHERE vak_id=?", args);
		cursor.moveToFirst();

		while (cursor.isAfterLast() == false) {
			TotalCijfer = TotalCijfer + Double.parseDouble(cursor.getString(5));
			Count++;
			cursor.moveToNext();
		}

		return TotalCijfer / Count;
	}

	public ArrayList<Vak> selectVakken(int pakketID) {
		ArrayList<Vak> vakken = new ArrayList<Vak>();
		String[] args = new String[]{String.valueOf(pakketID)};

		Cursor cursor = mydb.rawQuery("SELECT * FROM vak WHERE periode_id=?", args);
		cursor.moveToFirst();

		while (cursor.isAfterLast() == false) {
			Vak v = new Vak(cursor.getString(2),Integer.parseInt(cursor.getString(0)), 1);
			vakken.add(v);
			cursor.moveToNext();
			//TODO Cijfer meegeven. Nu standaard 1.
		}

		return vakken;
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

		private static final String INSERT_TOETSTYPE= "INSERT INTO toetstype (naam, som) SELECT 'Proefwerk', 3 UNION ALL SELECT 'Schiftelijke Overhoring', 1;";

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

			db.execSQL(INSERT_TOETSTYPE);
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			

		}

	}

}
