package hello;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;

@SpringBootApplication
public class Application {

	private static final Logger log = LoggerFactory.getLogger(Application.class);

	public static void main(String[] args) {
		SpringApplication.run(Application.class);
	}

	@Bean
	public CommandLineRunner demo(TeamRepository teamRepository, GameRepository gameRepository) {
		return (args) -> {
			// save a couple of teams
			Team first = new Team("first");
			teamRepository.save(first);
			Team second = new Team("second");
			teamRepository.save(second);
			Team third = new Team("third");
			teamRepository.save(third);
			teamRepository.save(new Team("fourth"));

			// save a couple of games
			gameRepository.save(new Game(first, second));
			gameRepository.save(new Game(first, third));

			this.printGamesPlayedByTeam(gameRepository, "first");
			this.printGamesPlayedByTeam(gameRepository, "second");
			this.printGamesPlayedByTeam(gameRepository, "third");
			this.printGamesPlayedByTeam(gameRepository, "fourth");
			log.info("");
		};
	}

	private void printGamesPlayedByTeam(GameRepository gameRepository, String teamName){
		log.info("Games played by team =  {}", teamName);
		List<Game> gamesPlayedByTeamFirst = gameRepository.findPlayedGames(teamName);
		gamesPlayedByTeamFirst.forEach(game ->log.info(game.toString()));
	}

}
