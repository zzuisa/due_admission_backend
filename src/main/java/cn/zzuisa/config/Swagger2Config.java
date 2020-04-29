package cn.zzuisa.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.async.DeferredResult;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.ArrayList;
import java.util.List;

// https://github.com/caspar-chen/swagger-ui-layer
@Configuration
public class Swagger2Config {

	@Bean
	public Docket ProductApi() {
		ParameterBuilder ticketPar = new ParameterBuilder();
		List<Parameter> pars = new ArrayList<Parameter>();
		ticketPar.name("token").description("user token")
				.modelRef(new ModelRef("string")).parameterType("header")
				.required(false).build(); //header中的ticket参数非必填，传空也可以
		pars.add(ticketPar.build());  //根据每个方法名也知道当前方法在设置什么参数
		return new Docket(DocumentationType.SWAGGER_2)
				.genericModelSubstitutes(DeferredResult.class)
				.useDefaultResponseMessages(false)
				.forCodeGeneration(false)
				.pathMapping("/")
				.select()
				.apis(RequestHandlerSelectors.basePackage(""))
				.build()
				.globalOperationParameters(pars)
				.apiInfo(productApiInfo());
	}

	private ApiInfo productApiInfo() {
		return new ApiInfoBuilder()
				.title("Concept of a data base for special entry tests to University of Duisburg-Essen")
				.description("webservice:RESTful")
				.version("1.0")
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
