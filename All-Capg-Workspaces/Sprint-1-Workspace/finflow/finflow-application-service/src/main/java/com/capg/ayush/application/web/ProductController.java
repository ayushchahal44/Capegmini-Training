/* Build by: Ayush chahal | ayushchahal44@gmail.com */
package com.capg.ayush.application.web;

import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST Controller for retrieving information about loan products.
 * Publicly accessible to provide catalogs of available financial services.
 */
@RestController
@RequestMapping("/api")
public class ProductController {

	/**
	 * Retrieves a list of all available loan products.
	 * @return A map containing the list of loan products
	 */
	@GetMapping("/products")
	public Map<String, Object> products() {
		return Map.of(
				"products", List.of(
						Map.of("code", "HOME", "name", "Home Loan", "description", "Competitive rates for your dream home"),
						Map.of("code", "PERSONAL", "name", "Personal Loan", "description", "Flexible tenure and quick approval"),
						Map.of("code", "AUTO", "name", "Auto Loan", "description", "Finance your vehicle with minimal paperwork")));
	}
}
