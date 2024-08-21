package com.comparus.multipledatasource.controller;

import com.comparus.multipledatasource.model.User;
import com.comparus.multipledatasource.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.Explode;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.enums.ParameterStyle;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Operation(summary = "Find all users by params")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Will return users, based on params"),
            @ApiResponse(responseCode = "404", description = "database parameter provided with wrong value")
    })
    @GetMapping("/users")
    public List<User> findAllUsersWithParams(
            @Parameter(
                    description = "database: string, id: string, username: string, name: string, surname: string",
                    in = ParameterIn.QUERY,
                    schema = @Schema(
                            type = "object",
                            additionalProperties = Schema.AdditionalPropertiesValue.TRUE,
                            ref = "#/components/schemas/queryRequestMap"
                    ),
                    style = ParameterStyle.FORM,
                    explode = Explode.TRUE
            )
            @RequestParam Map<String, String> queryParams) {
        return userService.findAllUsersWithParams(queryParams);
    }
}
