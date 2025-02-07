package application.havenskin.DataAccess;

import jakarta.validation.constraints.NotBlank;

public class TransactionDto {
    @NotBlank(message = "type is required")
    private String type;
}
