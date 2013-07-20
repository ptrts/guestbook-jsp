package com.suchorukov.tarouts.guestbook;

import java.sql.*;
import java.sql.Timestamp;
import java.util.LinkedList;
import java.util.List;

public class GuestBookControllerImpl implements GuestBookController {

	private Connection connection;

	@Override
	public void addRecord(String userName, String message) throws SQLException {

		PreparedStatement preparedStatement = null;

		try {
			Timestamp postDate = new Timestamp(System.currentTimeMillis());

			preparedStatement = connection.prepareStatement(
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

	@Override
	public List<Record> getRecords() throws SQLException {

		PreparedStatement preparedStatement = null;

		try {
			preparedStatement = connection.prepareStatement(
					"SELECT id, message, postDate, userName FROM posts AS posts"
			);

			ResultSet resultSet = preparedStatement.executeQuery();

			resultSet.beforeFirst();

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

	public GuestBookControllerImpl() throws ClassNotFoundException, SQLException {
		connection = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/guestbook", "app", "app");
		} catch (SQLException e) {
			if (connection != null) {
				connection.close();
			}
			throw e;
		}
	}

	@Override
	public void close() throws Exception {
		if (connection != null) {
			connection.close();
		}
	}
}
