package com.waa.marketplace.dtos.requests;

import com.waa.marketplace.enums.Role;
import com.waa.marketplace.validations.ValidRole;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Data transfer object for seller/buyer signup")
public class SignupRequestDto {
    @NotNull
    @Schema(description = "Name of the account holder", example = "John Doe")
    private String name;

    @NotNull
    @Schema(description = "Email address of the account holder", example = "john.doe@example.com")
    private String email;

    @NotNull
    @Schema(description = "Password for the account", example = "securePassword123")
    private String password;

//    @NotNull
    @ValidRole(values = {Role.SELLER, Role.BUYER})
    private String role;
}
