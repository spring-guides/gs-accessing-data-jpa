package hello;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface GameRepository extends CrudRepository<Game, Long>{

    @Query("SELECT g FROM Game g WHERE g.team1.name = :teamName or g.team2.name=:teamName")
    List<Game> findPlayedGames(@Param("teamName") String teamName);
}
