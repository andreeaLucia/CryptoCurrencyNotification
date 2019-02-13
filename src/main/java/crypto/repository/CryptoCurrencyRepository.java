package crypto.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.springframework.stereotype.Component;

@Component
public class CryptoCurrencyRepository {
	static final String DB_URL = "jdbc:mysql://localhost/cronjob";
	static final String USER = "root";
	static final String PASS = "";

	public boolean addCoinAndPercent(String nameCoin, double initialPrice, int percent, String email) {

		try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
				PreparedStatement st = conn.prepareStatement(
						"INSERT INTO REMINDERS (nameCoin, initialPrice, percent, email, addedDate) values(?, ?, ?, ?, now())");) {
			st.setString(1, nameCoin);
			st.setDouble(2, initialPrice);
			st.setInt(3, percent);
			st.setString(4, email);
			int result = st.executeUpdate();
			if (result == 0) {
				return false;
			}
		} catch (SQLException se) {
			se.getStackTrace();
			return false;
		}
		return true;
	}

	public boolean insertIntoHistoryReminders(String nameCoin, double initialPrice, double percent, String addedDate,
			String email) {

		try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
				PreparedStatement st = conn.prepareStatement(
						"INSERT INTO HISTORYREMINDERS(nameCoin, initialPrice, percent, addedDate, transferDate, email) values(?, ?, ?, ?, now(), ?)");) {
			st.setString(1, nameCoin);
			st.setDouble(2, initialPrice);
			st.setDouble(3, percent);
			st.setString(4, addedDate);
			st.setString(5, email);
			int result = st.executeUpdate();
			if (result == 0) {
				return false;
			}
		} catch (SQLException se) {
			se.getStackTrace();
			return false;
		}
		return true;
	}

	public boolean deleteFromReminders(int idReminder) {

		try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
				PreparedStatement st = conn.prepareStatement("Delete from reminders where idReminder=?");) {
			st.setInt(1, idReminder);
			int result = st.executeUpdate();
			if (result == 0) {
				return false;
			}
		} catch (SQLException se) {
			se.getStackTrace();
			return false;
		}
		return true;
	}
}
