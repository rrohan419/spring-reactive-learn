package com.spring.reactive;

import java.time.Duration;
import java.util.List;
import java.util.Random;

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
				.map(String::toUpperCase).flatMap(this::splitString).log();
	}

	public Flux<String> splitString(String value) {
		var charArray = value.split("");
		return Flux.fromArray(charArray);
	}

	public Mono<List<String>> namesMonoFlatMapFilter(int stringLength) {
		return Mono.just("alex").map(String::toUpperCase).filter(s -> s.length() > stringLength)
				.flatMap(this::splitStringMono);
	}

	// convert a mono input to flux output
	public Flux<String> namesMonoFlatMapManyFilter(int stringLength) {
		return Mono.just("alex").map(String::toUpperCase).filter(s -> s.length() > stringLength)
				.flatMapMany(this::splitString).log();
	}

	public Mono<List<String>> splitStringMono(String value) {
		var charArray = value.split("");
		return Mono.just(List.of(charArray));
	}

	public Flux<String> namesFluxFlatMapFilterWithDelay(int stringLength) {

		// this example is for showing you that flat map is async in nature.
		return Flux.fromIterable(List.of("abc", "bcd", "abcd", "abcd")).filter(s -> s.length() > stringLength)
				.map(String::toUpperCase).flatMap(this::splitStringWithDelay).log();
	}

	public Flux<String> namesFluxConcatMapFilterWithDelay(int stringLength) {

		// this example is for showing you that you can use concat map when you required
		// sequencing
		// works just like falt map just with ordering
		return Flux.fromIterable(List.of("abc", "bcd", "abcd", "abcd")).filter(s -> s.length() > stringLength)
				.map(String::toUpperCase).concatMap(this::splitStringWithDelay).log();
	}

	public Flux<String> splitStringWithDelay(String value) {
		var delay = new Random().nextInt(1000);
		var charArray = value.split("");
		return Flux.fromArray(charArray).delayElements(Duration.ofMillis(delay));
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
