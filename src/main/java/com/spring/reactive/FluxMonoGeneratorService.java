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

	public Flux<String> namesFluxMap() {
		return Flux.fromIterable(List.of("abc", "bcd", "def", "ghi", "jkl")).map(String::toUpperCase).log();
	}

	public Flux<String> namesFluxMapFilter(int stringLength) {
		return Flux.fromIterable(List.of("abc", "bcd", "abcd", "abcd")).filter(s -> s.length() > stringLength)
				.map(String::toUpperCase).log();
	}

	public Flux<String> namesFluxFlatMapFilter(int stringLength) {
		return Flux.fromIterable(List.of("abc", "bcd", "abcd", "abcd")).filter(s -> s.length() > stringLength)
				.map(String::toUpperCase)
				.flatMap(this::splitString).log();
	}
	
	public Flux<String> splitString(String value) {
		var charArray = value.split("");
		return Flux.fromArray(charArray);
	}

	public Flux<String> nameFluxImmutable() {
		var nameFlux = Flux.fromIterable(List.of("abc", "bcd")).log();
		nameFlux.map(String::toUpperCase);
		return nameFlux;
	}

	public static void main(String[] args) {
		FluxMonoGeneratorService fluxMonoGeneratorService = new FluxMonoGeneratorService();
		fluxMonoGeneratorService.namesFlux().subscribe(name -> {
			System.out.print(name + " ");
		});

		fluxMonoGeneratorService.nameMono().subscribe(name -> {
			System.out.println("mono name is " + name);
		});
	}
}
