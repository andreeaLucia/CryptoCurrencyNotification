package crypto.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import crypto.controller.request.CryptoCurrencyRequest;
import crypto.controller.response.CryptoCurrencyResponse;
import crypto.service.CryptoCurrencyService;

@RestController
public class CryptoCurrencyController {
	
	@Autowired
	private CryptoCurrencyService cryptoCurrencyService;
	
	@RequestMapping(path = "/addCoinAndPercent", method = RequestMethod.POST)
	public CryptoCurrencyResponse addCoinAndPercent(@RequestBody CryptoCurrencyRequest cryptoCurrencyRequest){
	
		return  cryptoCurrencyService.addCoinAndPercent(cryptoCurrencyRequest.getNameCoin(),
				cryptoCurrencyRequest.getPercent(),
				cryptoCurrencyRequest.getEmail());
	}
	
}
