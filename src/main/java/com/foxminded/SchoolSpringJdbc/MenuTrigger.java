package com.foxminded.SchoolSpringJdbc;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import com.foxminded.SchoolSpringJdbc.service.ConsoleService;

@Component
@Profile("!disableMenu")
public class MenuTrigger implements ApplicationRunner {

	private final ConsoleService consoleService;

	public MenuTrigger(ConsoleService consoleService) {
		this.consoleService = consoleService;
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {
		consoleService.startMenu();

	}

}
