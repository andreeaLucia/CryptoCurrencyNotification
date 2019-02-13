package crypto.controller.response;

import java.util.List;

import crypto.model.CryptoReminder;

public class CryptoCurrencyResponse extends BaseResponse {
	List<CryptoReminder> cryptoCurrency;

	public CryptoCurrencyResponse() {

	}
	public CryptoCurrencyResponse(List<CryptoReminder> cryptoCurrency) {
			this.cryptoCurrency = cryptoCurrency;
	}
	public List<CryptoReminder> getCryptoCurrency() {
		return cryptoCurrency;
	}
	public void setCryptoCurrency(List<CryptoReminder> cryptoCurrency) {
		this.cryptoCurrency = cryptoCurrency;
	}
	
}
