package cn.zzuisa.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.async.DeferredResult;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

// https://github.com/caspar-chen/swagger-ui-layer
@Configuration
public class Swagger2Config {

	@Bean
	public Docket ProductApi() {
		return new Docket(DocumentationType.SWAGGER_2)
				.genericModelSubstitutes(DeferredResult.class)
				.useDefaultResponseMessages(false)
				.forCodeGeneration(false)
				.pathMapping("/")
				.select()
				.apis(RequestHandlerSelectors.basePackage(""))
				.build()
				.apiInfo(productApiInfo());
	}

	private ApiInfo productApiInfo() {
		return new ApiInfoBuilder()
				.title("Volunteer 志愿郑州 API")
				.description("简单优雅的restfun风格")
				.version("1.1")
				.build();
	}

//	@Bean
//	public Docket createRestApi() {
//		return new Docket(DocumentationType.SWAGGER_2)
//				.apiInfo(apiInfo())
//				.select()
//				.apis(RequestHandlerSelectors.basePackage("com.gdata"))
//				.paths(PathSelectors.any())
//				.build()
//				.directModelSubstitute(LocalDate.class, java.sql.Date.class)
//                .directModelSubstitute(DateTime.class, java.util.Date.class);
//	}
//
//	private ApiInfo apiInfo() {
//		return new ApiInfoBuilder()
//				.title("springboot利用swagger构建mall API document")
//				.description("简单优雅的restfun风格，http://zmall.com/")
//				.termsOfServiceUrl("http://zmall.com/")
//				.version("1.1")
//				.build();
//	}
}
