package crypto.service;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import crypto.controller.response.CryptoCurrencyResponse;
import crypto.controller.response.ResponseStatus;
import crypto.model.CryptoCurrencyException;
import crypto.repository.CryptoCurrencyRepository;

@Component
public class CryptoCurrencyService {

	static final String DB_URL = "jdbc:mysql://localhost/freezerdb";
	static final String USER = "root";
	static final String PASS = "";

	@Autowired
	private CryptoCurrencyRepository cryptoCurrencyRepository;

	public CryptoCurrencyResponse addCoinAndPercent(String nameCoin, int percent, String email) {

		CryptoCurrencyResponse cryptoCurrencyResponse = new CryptoCurrencyResponse();
		boolean wasUpdateed;
		try {
			wasUpdateed = cryptoCurrencyRepository.addCoinAndPercent(nameCoin, getActualPrice(nameCoin), percent,
					email);
			if (wasUpdateed) {
				cryptoCurrencyResponse.setStatus(ResponseStatus.SUCCESS);
			} else {
				cryptoCurrencyResponse.setStatus(ResponseStatus.FAILURE);
				cryptoCurrencyResponse.setMessage("Give another name of the coin!");
			}
			return cryptoCurrencyResponse;

		} catch (CryptoCurrencyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			cryptoCurrencyResponse.setStatus(ResponseStatus.FAILURE);
			return cryptoCurrencyResponse;
		}

	}

	public double getActualPrice(String nameCoin) throws CryptoCurrencyException {
		double actualPrice = 0;
		RestTemplate restTemplate = new RestTemplate();
		String fooResourceUrl = "https://pro-api.coinmarketcap.com/v1/cryptocurrency/listings/latest?CMC_PRO_API_KEY=aaed6aac-a16e-4b0a-92c6-844a4ae71380";
		ResponseEntity<String> response = restTemplate.getForEntity(fooResourceUrl, String.class);
		String resp = response.getBody();
		JSONObject ob = new JSONObject(resp);
		JSONArray data = ob.getJSONArray("data");
		for (int i = 0; i < data.length(); i++) {
			JSONObject object = data.getJSONObject(i);
			String name = object.getString("name");
			if (nameCoin.equals(name)) {
				JSONObject quote = object.getJSONObject("quote");
				JSONObject usd = quote.getJSONObject("USD");
				actualPrice = usd.getDouble("price");
				return actualPrice;
			}
		}

		throw new CryptoCurrencyException("Give another name of coin!");

	}
}
