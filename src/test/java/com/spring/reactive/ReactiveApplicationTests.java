package com.spring.reactive;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import reactor.test.StepVerifier;

@SpringBootTest
class ReactiveApplicationTests {

	FluxMonoGeneratorService fluxMonoGeneratorService = new FluxMonoGeneratorService();
	
	@Test
	void contextLoadsSuccess() {
		var namesFlux = fluxMonoGeneratorService.namesFlux();
		
		StepVerifier.create(namesFlux).expectNext("abc", "bcd", "def", "ghi", "jkl")
		.verifyComplete();
	}
	
	@Test
	void contextLoadsCount() {
		var namesFlux = fluxMonoGeneratorService.namesFlux();
		
		StepVerifier.create(namesFlux).expectNextCount(5)
		.verifyComplete();
	}
	
	@Test
	void namesFluxMap() {
		var namesFlux = fluxMonoGeneratorService.nameFluxImmutable();
		
		StepVerifier.create(namesFlux).expectNext("abc", "bcd")
		.verifyComplete();
	}
	
	@Test
	void namesFluxMapFilter() {
		var namesFlux = fluxMonoGeneratorService.namesFluxMapFilter(3);
		
		StepVerifier.create(namesFlux).expectNext("ABCD", "ABCD")
		.verifyComplete();
	}
	
	@Test
	void namesFluxFlatMapFilter() {
		var namesFlux = fluxMonoGeneratorService.namesFluxFlatMapFilter(3);
		
		StepVerifier.create(namesFlux).expectNext("A", "B", "C", "D","A", "B", "C", "D")
		.verifyComplete();
	}

}
