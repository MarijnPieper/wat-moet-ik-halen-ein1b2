package nl.saxion.ein1b2;

import java.math.BigDecimal;
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
		values.put("datumtijd" , t.getDatumtijd().toStringForDB());
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
			Periode p = new Periode(Integer.parseInt(cursor.getString(0)), cursor.getString(1), new CustomDate(cursor.getString(2)), new CustomDate(cursor.getString(3)));
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



	public ArrayList<Toets> selectToetsen(int vakid, boolean aankomend, boolean geschiedenis){
		ArrayList<Toets> toetsen = new ArrayList<Toets>();
		String[] args = null;
		String query = "SELECT t.id, t.toetstype_id, t.beschrijving, t.datumtijd, t.cijfer, v.naam FROM toets t LEFT OUTER JOIN vak v ON v.id = t.vak_id ";
		String where = "";
		String orderby = "";
		if (vakid != 0) {
			where = "WHERE t.vak_id=? ";
			args = new String[]{String.valueOf(vakid)};
		}
		if (aankomend == true){
			if (vakid == 0) where = "where ";
			else where += "AND ";
			where += "t.datumtijd > datetime()";
			orderby = " ORDER BY t.datumtijd ASC";
		}
		else if (geschiedenis == true){
			if (vakid == 0) where = "where ";
			else where += "AND ";
			where += "t.datumtijd < datetime()";
			orderby = " ORDER BY t.datumtijd DESC";
		}
		Cursor cursor = mydb.rawQuery(query + where + orderby, args);
		cursor.moveToFirst();

		while (cursor.isAfterLast() == false) {
			String tmp = cursor.getString(3);
			Toets toets = new Toets(cursor.getInt(0), cursor.getInt(1), cursor.getString(2), new CustomDate(cursor.getString(3)), cursor.getInt(4), cursor.getString(5));
			toetsen.add(toets);
			cursor.moveToNext();
		}
		return toetsen;
	}

	public Toets selectAankomendeToets(int periodeId) {
		String ids = "";
		String[] args = new String[]{String.valueOf(periodeId)};
		String query = "SELECT id FROM vak WHERE periode_id=?";

		Cursor cursor = mydb.rawQuery(query, args);
		cursor.moveToFirst();

		while (!cursor.isAfterLast()) {
			ids = ids + cursor.getString(0) + ",";
			cursor.moveToNext();
		}
		
		if (!ids.equals("")) {
			ids = ids.substring(0, ids.length() - 1);
		
			query = "SELECT t.datumtijd, v.naam, o.naam FROM toets t LEFT OUTER JOIN vak v ON v.id = t.vak_ID LEFT OUTER JOIN toetstype o ON o.id = t.toetstype_id WHERE t.vak_ID IN (?) AND t.datumtijd > datetime() ORDER BY t.datumtijd ASC";
			args = new String[]{ids};

			cursor = mydb.rawQuery(query, args);
			cursor.moveToFirst();
			

			Toets t = new Toets(cursor.getString(2), cursor.getString(1), new CustomDate(cursor.getString(0)));

			return t;
		}
		else {  // Geen toets ingesteld, lege toets teruggeven
			cursor.moveToFirst();
			return new Toets(0, "", new CustomDate(31, 10, 1990));
		}
		

		
	}


	// TODO : Proefwerk / SO
	public double selectGemCijferVak(int VakID) {
		Double TotalCijfer = new Double(0);
		Double Count = new Double(0);
		Double gemCijfer = new Double(0);
		String[] args = new String[]{String.valueOf(VakID)};
		Cursor cursor = mydb.rawQuery("SELECT * FROM toets WHERE vak_id=?", args);
		cursor.moveToFirst();

		while (cursor.isAfterLast() == false) {
			if (cursor.getDouble(5) != 0.0) {
				TotalCijfer = TotalCijfer + Double.parseDouble(cursor.getString(5));
				Count++;
			}

			cursor.moveToNext();
		}
		gemCijfer = TotalCijfer / Count;	
		if (!gemCijfer.equals(Double.NaN)) {
			BigDecimal bd = new BigDecimal(gemCijfer);
			bd = bd.setScale(1,BigDecimal.ROUND_HALF_UP);	
			return bd.doubleValue();
		}
		else {
			return gemCijfer;
		}
	}

	public double selectMinCijferVak(int toetsid, double minimaleCijfer, int wegingDezeToets) {
		double totaalCijfers = new Double(0);
		int totaalWegingen = 0;
		double teBehalen = new Double(0);

		String[] args = new String[]{String.valueOf(toetsid)};
		String query = "SELECT sum(toets.cijfer) as totaal_cijfers, count(toetstype.som) as totaal_wegingen FROM toets LEFT OUTER JOIN toetstype ON toets.toetstype_id = toetstype.id " 
				+ "WHERE toets.vak_id=(select vak_id from toets where id=? limit 1)"
				+ "AND toets.cijfer is not NULL " +
				"AND toets.cijfer != '0.0'";
		Cursor cursor = mydb.rawQuery(query, args);
		cursor.moveToFirst();

		while (cursor.isFirst()) {
			totaalCijfers = cursor.getDouble(0);   
			totaalWegingen = cursor.getInt(1);
			cursor.moveToNext();
		}

		teBehalen = ((minimaleCijfer * (totaalWegingen + wegingDezeToets)) - totaalCijfers) / wegingDezeToets;
//		if ()
		BigDecimal bd = new BigDecimal(teBehalen);
		bd = bd.setScale(1,BigDecimal.ROUND_HALF_UP);

		return bd.doubleValue();

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
