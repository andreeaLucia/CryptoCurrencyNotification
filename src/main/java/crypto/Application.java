package crypto;

import java.util.Timer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan(basePackages = { "crypto" })
@SpringBootApplication
public class Application {

	public static void main(String[] args) {

		CryptoReminderTimerTask repeatedTask = new CryptoReminderTimerTask();
		Timer timer = new Timer("Timer");
		timer.scheduleAtFixedRate(repeatedTask, 0, 900000);

		SpringApplication.run(Application.class, args);
	}
}
