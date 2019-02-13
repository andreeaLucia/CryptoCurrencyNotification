package crypto.controller.request;

public class CryptoCurrencyRequest {
	private int idReminders;
	private String nameCoin;
	private Double initialPrice;
	private int percent;
	private String addedDate;
	private String email;
	
	public String getNameCoin() {
		return nameCoin;
	}
	public void setNameCoin(String nameCoin) {
		this.nameCoin = nameCoin;
	}
	public Double getInitialPrice() {
		return initialPrice;
	}
	public void setInitialPrice(Double initialPrice) {
		this.initialPrice = initialPrice;
	}
	public int getPercent() {
		return percent;
	}
	public void setPercent(int percent) {
		this.percent = percent;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public int getIdReminders() {
		return idReminders;
	}
	public void setIdReminders(int idReminders) {
		this.idReminders = idReminders;
	}
	public String getAddedDate() {
		return addedDate;
	}
	public void setAddedDate(String addedDate) {
		this.addedDate = addedDate;
	}
	
}
