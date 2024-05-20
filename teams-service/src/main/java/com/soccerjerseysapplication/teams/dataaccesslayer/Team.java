package com.soccerjerseysapplication.teams.dataaccesslayer;



import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.validator.constraints.UniqueElements;
import org.springframework.aot.generate.GenerationContext;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
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
    @Indexed(unique = true)
    private TeamIdentifier teamIdentifier;
    @Indexed(unique = true)
    private String name;
    private String country;
}
