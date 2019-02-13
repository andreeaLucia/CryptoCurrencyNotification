package crypto;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.TimerTask;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import crypto.model.CryptoReminder;
import crypto.repository.CryptoCurrencyRepository;

public class CryptoReminderTimerTask extends TimerTask {

	static final String DB_URL = "jdbc:mysql://localhost/cronjob";
	static final String USER = "root";
	static final String PASS = "";

	
	private CryptoCurrencyRepository cryptoCurrencyRepository = new CryptoCurrencyRepository();

	private RestTemplate restTemplate = new RestTemplate();
	private String fooResourceUrl = "https://pro-api.coinmarketcap.com/v1/cryptocurrency/listings/latest?CMC_PRO_API_KEY=aaed6aac-a16e-4b0a-92c6-844a4ae71380";

	@Override
	public void run() {
		verifyPercent();
	}

	public void verifyPercent() {
		Double currentPrice = 0.0;
		ResponseEntity<String> response = restTemplate.getForEntity(fooResourceUrl, String.class);
		String resp = response.getBody();
		JSONObject ob = new JSONObject(resp);
		JSONArray data = ob.getJSONArray("data");
		List<CryptoReminder> listOfCryptoReminders = getListOfCryptoReminders();
		for (CryptoReminder entry : listOfCryptoReminders) {
			for (int i = 0; i < data.length(); i++) {
				JSONObject object = data.getJSONObject(i);
				String coin = object.getString("name");
				if (entry.getNameCoin().equals(coin)) {
					JSONObject quote = object.getJSONObject("quote");
					JSONObject usd = quote.getJSONObject("USD");
					currentPrice = usd.getDouble("price");
					if (entry.getInitialPrice() - entry.getInitialPrice() / 100 * entry.getPercent() >= currentPrice) {
						//sendingEmail(entry.getEmail(), entry.getInitialPrice(), entry.getPercent(), entry.getNameCoin(),
						//		currentPrice);
						boolean var = cryptoCurrencyRepository.insertIntoHistoryReminders(entry.getNameCoin(),
								entry.getInitialPrice(), entry.getPercent(), entry.getAddedDate(), entry.getEmail());
						if (var == true) {
							System.out.println("Succesful transfer!");
							cryptoCurrencyRepository.deleteFromReminders(entry.getIdReminder());
							System.out.println("Succesful deleted!");
						}
					}
					break;
				}

			}
		}

	}

	public List<CryptoReminder> getListOfCryptoReminders() {
		List<CryptoReminder> listOfCryptoReminders = new ArrayList<>();
		try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
				PreparedStatement st = conn.prepareStatement("SELECT * FROM reminders");) {
			ResultSet rs = st.executeQuery();
			while (rs.next()) {
				CryptoReminder cryptoReminder = new CryptoReminder();
				cryptoReminder.setNameCoin(rs.getString("nameCoin"));
				cryptoReminder.setInitialPrice(rs.getDouble("initialPrice"));
				cryptoReminder.setPercent(rs.getDouble("percent"));
				cryptoReminder.setEmail(rs.getString("email"));
				listOfCryptoReminders.add(cryptoReminder);
			}

		} catch (SQLException se) {
			// Handle errors for JDBC
			se.printStackTrace();
		}
		return listOfCryptoReminders;
	}

	public void sendingEmail(String email, Double initialPrice, Double percent, String nameCoin, Double currentPrice) {
		final String username = "andreLuciaJucan@gmail.com";
		final String password = "********";

		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");

		Session session = Session.getInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		});

		try {
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress("andreLuciaJucan@gmail.com"));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email));
			message.setSubject("Buy coins" + " " + nameCoin + " " + "!");
			message.setText("You can buy" + " " + nameCoin + " they are more cheap with " + percent + " "
					+ "new price is " + currentPrice + " " + "old price is" + " " + initialPrice);

			Transport.send(message);

			System.out.println("Done");

		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
	}

	public CryptoCurrencyRepository getCryptoCurrencyRepository() {
		return cryptoCurrencyRepository;
	}

	public void setCryptoCurrencyRepository(CryptoCurrencyRepository cryptoCurrencyRepository) {
		this.cryptoCurrencyRepository = cryptoCurrencyRepository;
	}

}
