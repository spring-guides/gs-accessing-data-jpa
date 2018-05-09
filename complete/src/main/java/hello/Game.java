package hello;

import javax.persistence.*;

@Entity
public class Game {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    @ManyToOne
    private Team team1;

    @ManyToOne
    private Team team2;

    Game() {
    }

    public Game(Team team1, Team team2) {
        this.team1 = team1;
        this.team2 = team2;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Game{");
        sb.append("team1=").append(team1);
        sb.append(", team2=").append(team2);
        sb.append('}');
        return sb.toString();
    }
}
