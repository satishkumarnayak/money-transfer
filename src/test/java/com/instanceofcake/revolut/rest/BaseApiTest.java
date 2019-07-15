package com.instanceofcake.revolut.rest;

import org.dalesbred.Database;
import org.dalesbred.annotation.SQL;
import org.junit.BeforeClass;
import com.instanceofcake.revolut.rest.database.DB;
import com.instanceofcake.revolut.rest.util.Util;

public class BaseApiTest {

	@BeforeClass
	
	public static void setUp() throws Exception {
		DB.db = Database.forUrlAndCredentials("jdbc:hsqldb:mem:memdb;sql.syntax_mys=true;sql.enforce_size=false", "SA","");
	//	DB.db   = Database.forUrlAndCredentials("jdbc:mysql://localhost:3306/test", "root", "admin");
		String[] instructions = Util.readInputStream(BaseApiTest.class.getResourceAsStream("/test-db-setup.sql")) .split(";");
		for (@SQL String instruction : instructions) {
			DB.db.update(instruction);
		}

	}

}
