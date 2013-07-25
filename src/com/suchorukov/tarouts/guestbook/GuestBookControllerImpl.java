package com.suchorukov.tarouts.guestbook;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.LinkedList;
import java.util.List;

public class GuestBookControllerImpl implements GuestBookController {

	private DataSource ds;

	@Override
	public void addRecord(String userName, String message) throws SQLException {

		PreparedStatement preparedStatement = null;

		try {
			Timestamp postDate = new Timestamp(System.currentTimeMillis());

			preparedStatement = ds.getConnection().prepareStatement(
					"INSERT INTO " +
							"posts (message, postDate, userName) " +
							"VALUES (?, ?, ?)"
			);

			preparedStatement.setString(1, message);
			preparedStatement.setTimestamp(2, postDate);
			preparedStatement.setString(3, userName);

			preparedStatement.execute();
		} finally {
			if (preparedStatement != null) {
				preparedStatement.close();
			}
		}
	}

	public GuestBookControllerImpl(DataSource ds) {
		this.ds = ds;
	}

	@Override
	public List<Record> getRecords() throws SQLException {

		PreparedStatement preparedStatement = null;

		try {
			preparedStatement = ds.getConnection().prepareStatement(
					"SELECT id, message, postDate, userName FROM posts AS posts"
			);

			ResultSet resultSet = preparedStatement.executeQuery();

			List<Record> list = new LinkedList<>();

			while (resultSet.next()) {
				Record record = new Record();
				record.setId(resultSet.getInt("id"));
				record.setMessage(resultSet.getString("message"));
				record.setPostDate(resultSet.getTimestamp("postDate"));
				record.setUserName(resultSet.getString("userName"));

				list.add(record);
			}

			return list;

		} finally {
			if (preparedStatement != null) {
				preparedStatement.close();
			}
		}
	}
}
