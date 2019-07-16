package com.instanceofcake.revolut.rest.database;

import org.dalesbred.Database;
import org.dalesbred.annotation.SQL;

import com.instanceofcake.revolut.rest.util.Util;

public class DB {

	public static Database db;

	static {
		db = Database.forUrlAndCredentials("jdbc:hsqldb:mem:memdb;sql.syntax_mys=true;sql.enforce_size=false", "SA",
				"");
		String[] instructions = Util.readInputStream(DB.class.getResourceAsStream("/db-setup.sql")).split(";");
		for (@SQL
		String instruction : instructions) {
			DB.db.update(instruction);
		}
	}

}
