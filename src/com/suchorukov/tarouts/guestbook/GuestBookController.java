package com.suchorukov.tarouts.guestbook;

import java.sql.SQLException;
import java.util.List;

public interface GuestBookController extends AutoCloseable {
	void addRecord(String userName, String message) throws SQLException;
	List<Record> getRecords() throws SQLException;
}
