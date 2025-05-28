package com.chatterbox.config;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

/**
 * Configuration class to set up CORS (Cross-Origin Resource Sharing) for the application.
 * Allows requests from specific origins and methods, enabling secure cross-origin requests.
 */
@Configuration
public class CorsConfig {

	/**
	 * Configures CORS settings to allow requests from the frontend application (e.g., Angular app running on localhost:4200).
	 * This method permits specific HTTP methods and headers, and allows credentials to be included in cross-origin requests.
	 * 
	 * @return A CorsFilter that applies the specified CORS configuration.
	 */
	@Bean
	public CorsFilter corsFilter() {
		CorsConfiguration config = new CorsConfiguration();
		config.setAllowedOrigins(List.of("http://localhost:4200"));
		config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
		config.setAllowedHeaders(List.of("Authorization", "Cache-Control", "Content-Type"));
		config.setAllowCredentials(true);

		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", config);

		return new CorsFilter(source);
	}

}
