package crypto.model;

public class CryptoReminder {
	private int idReminder;
	private String nameCoin;
	private double initialPrice;
	private double percent;
	private String addedDate;
	private String transferDate;
	private String email;
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getNameCoin() {
		return nameCoin;
	}
	public void setNameCoin(String nameCoin) {
		this.nameCoin = nameCoin;
	}
	
	public double getInitialPrice() {
		return initialPrice;
	}
	public void setInitialPrice(double initialPrice) {
		this.initialPrice = initialPrice;
	}
	public double getPercent() {
		return percent;
	}
	public void setPercent(double percent) {
		this.percent = percent;
	}
	
	public String getAddedDate() {
		return addedDate;
	}
	public void setAddedDate(String addedDate) {
		this.addedDate = addedDate;
	}
	
	public String getTransferDate() {
		return transferDate;
	}
	public void setTransferDate(String transferDate) {
		this.transferDate = transferDate;
	}
	
	public int getIdReminder() {
		return idReminder;
	}
	public void setIdReminder(int idReminder) {
		this.idReminder = idReminder;
	}
	@Override
	public String toString() {
		return "[nameCoin=" + nameCoin + ", initialPrice=" + initialPrice + ", percent=" + percent + ", email="
				+ email + "]\n";
	}
	
	
	
}
