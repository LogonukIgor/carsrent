package by.logonuk.domain.attachments;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;

@Data
@Embeddable
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Credentials {

    private String login;

    private String mail;

    @JsonIgnore
    private String password;
}
