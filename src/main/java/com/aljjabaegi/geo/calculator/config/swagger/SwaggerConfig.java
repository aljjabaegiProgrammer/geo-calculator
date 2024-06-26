package com.aljjabaegi.geo.calculator.config.swagger;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.context.annotation.Configuration;

/**
 * WEB MVC 및 Swagger 관련 설정
 * 참조 <a href="https://swagger.io/docs/specification/authentication/">...</a>
 *
 * @author GEONLEE
 * @version 1.0.0
 * @since 2024-06-25<br />
 */
@OpenAPIDefinition(
        info = @Info(title = "Geo Calculator",
                description = """
                        지도 관련 계산 정보 조회
                        """, version = "v1.0"),
        servers = @Server(url = "/geo-calculator")
)
@Configuration
public class SwaggerConfig {
}
