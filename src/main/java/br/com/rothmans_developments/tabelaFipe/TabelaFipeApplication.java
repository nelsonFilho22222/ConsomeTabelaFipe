package br.com.rothmans_developments.tabelaFipe;

import br.com.rothmans_developments.tabelaFipe.MenuManager.MenuManager;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TabelaFipeApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(TabelaFipeApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		MenuManager menu = new MenuManager();
		menu.exibeMenu();

	}
}
