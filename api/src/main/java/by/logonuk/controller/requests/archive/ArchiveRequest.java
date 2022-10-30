package by.logonuk.controller.requests.archive;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Request for archiving deal")
public class ArchiveRequest {

    @Schema(description = "User id", required = true, type = "string")
    private String userId;

    @Schema(description = "Deal status", required = true, type = "boolean")
    private Boolean isSuccessfully;
}