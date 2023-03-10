package com.example.api.bffmyapp.api;

import com.example.api.bffmyapp.dto.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Tag(name = "User")
@RequestMapping("/v1.0/users")
public interface UserApi {

    @Operation(summary = "User list")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User list",
                    content = { @Content(schema = @Schema(implementation = User.class)) }
            ),
            @ApiResponse(responseCode = "401", description = "Not authorized", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal error", content = @Content)
    })
    @GetMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Page<User>> listUser(@RequestParam(required = false, name = "last-name") String lastName,
                                        @RequestParam(required = false, name = "first-name") String firstName,
                                        @RequestParam(required = false, name = "email") String email,
                                        @ParameterObject @PageableDefault(size = 5, sort = {"lastName"}) Pageable pageable);
}
