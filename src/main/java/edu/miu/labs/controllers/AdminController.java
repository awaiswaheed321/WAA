package edu.miu.labs.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/admin")
public class AdminController {

    /**
     * This API endpoint is restricted to users with admin privileges.
     * Only users with the ADMIN authority can access this endpoint.
     * The API responds with a simple message indicating it's an admin-only API.
     * Ensure that the security configuration properly restricts access to
     * this endpoint by using hasAuthority("ADMIN") in the SecurityFilterChain.
     */
    @GetMapping
    public ResponseEntity<String> getAdminApi() {
        return ResponseEntity.ok("Admin API");
    }
}
