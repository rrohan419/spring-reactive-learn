package com.spring.reactive;

import java.util.List;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class FluxMonoGeneratorService {

	public Flux<String> namesFlux() {
		return Flux.fromIterable(List.of("abc", "bcd", "def", "ghi", "jkl")).log();
	}
	
	public Mono<String> nameMono() {
		return Mono.just("rohan").log();
	}
	
	public static void main(String[] args) {
		FluxMonoGeneratorService fluxMonoGeneratorService = new FluxMonoGeneratorService();
		fluxMonoGeneratorService.namesFlux().subscribe(name -> {
			System.out.print(name+" ");
		});
		
		fluxMonoGeneratorService.nameMono().subscribe(name -> {
			System.out.println("mono name is " + name);
		});
	}
}