package com.ewha.unis.global.api;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Health-Check", description = "서버 상태 확인 API")
public interface HealthCheckApi {
    @Operation(
            summary = "헬스 체크",
            description = "서버 현재 상태를 확인하기 위한 GET API. 정상 동작 시 'OK'를 반환한다."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "서버가 정상 동작 중")})
    String healthCheck();
}
