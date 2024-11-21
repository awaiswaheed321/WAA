package com.waa.marketplace.dtos.responses;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "DTO for login response containing the JWT access and refresh tokens.")
public class LoginResponseDto {

    @Schema(description = "JWT access token for authenticating the user in subsequent requests.", example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiYWRtaW4iOnRydWV9.abcd1234")
    private String accessToken;

    @Schema(description = "JWT refresh token to obtain a new access token when the current one expires.", example = "defg5678ijkl9012mnop34qrstu5678vwxyz")
    private String refreshToken;

    @Schema(description = "User information", implementation = UserResponseDto.class)
    private UserResponseDto user;
}
