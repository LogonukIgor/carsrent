package by.logonuk.exceptionhandle;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Map;

@Builder
@Setter
@Getter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class ErrorValidationContainer{

    private String exceptionId;

    private String errorMessage;

    private Map<String, String> allValidMessages;

    private int errorCode;

    private String errorClass;
}
