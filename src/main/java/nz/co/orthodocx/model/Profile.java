package nz.co.orthodocx.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Document
public class Profile {

    @Id
    private String id;
    private String firstname;
    private String lastname;

    public Profile(String firstname, String lastname) {
        this.firstname = firstname;
        this.lastname = lastname;
    }
}
