package by.logonuk.controller.requests;

import lombok.Data;

@Data
public class ArchiveRequest {

    private String userId;

    private Boolean isSuccessfully;
}