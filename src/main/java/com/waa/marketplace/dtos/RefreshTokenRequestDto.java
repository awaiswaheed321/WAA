package com.waa.marketplace.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "DTO for refresh token request, used to get a new access token.")
public class RefreshTokenRequestDto {

    @NotNull
    @Schema(description = "The refresh token used to obtain a new access token.", example = "defg5678ijkl9012mnop34qrstu5678vwxyz")
    private String refreshToken;
}
