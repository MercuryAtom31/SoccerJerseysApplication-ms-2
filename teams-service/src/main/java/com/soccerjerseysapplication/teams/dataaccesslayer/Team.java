package com.soccerjerseysapplication.teams.dataaccesslayer;



import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.aot.generate.GenerationContext;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

//@Entity
//@Table(name = "teams")
@Document(collection = "teams")
@NoArgsConstructor
@Builder
@AllArgsConstructor
@Data
public class Team {

    @Id
    private String id;

    private TeamIdentifier teamIdentifier;


    //@Column(name = "name") Specify if name of field is different.
    private String name;

    private String country;

    public Team(@NotNull TeamIdentifier teamIdentifier, @NotNull String name, @NotNull String country) {
        this.teamIdentifier = teamIdentifier;
        this.name = name;
        this.country = country;
    }

    public Team(String id, String teamId ,String name, String country) {
        this.id = id;
        this.teamIdentifier = new TeamIdentifier(teamId);
        this.name = name;
        this.country = country;
    }

    public Team(String name, String country) {
        this.teamIdentifier = new TeamIdentifier();
        this.name = name;
        this.country = country;
    }
}
