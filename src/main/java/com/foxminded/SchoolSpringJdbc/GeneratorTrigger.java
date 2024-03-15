package com.foxminded.SchoolSpringJdbc;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.foxminded.SchoolSpringJdbc.service.ConsoleService;
import com.foxminded.SchoolSpringJdbc.service.GeneratorService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@Profile("!disableGenerator")
public class GeneratorTrigger implements ApplicationRunner {

	private final GeneratorService generatorService;
	private final ConsoleService consoleService;

	public GeneratorTrigger(GeneratorService generatorService, ConsoleService consoleService) {
		this.generatorService = generatorService;
		this.consoleService = consoleService;
	}

	@Override
	@Transactional
	public void run(ApplicationArguments args) throws Exception {
		log.info("Data generation is starting");
		generatorService.applyGenerator();
		consoleService.startMenu();
	}

}
