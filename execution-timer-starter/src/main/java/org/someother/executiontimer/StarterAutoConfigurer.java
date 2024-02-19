package org.someother.executiontimer;

import org.someother.executiontimer.aspects.ExecutionTimerAspect;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(TimerProperties.class)
public class StarterAutoConfigurer {

	@Bean
	ExecutionTimerAspect executionTimerAspect(TimerProperties timerProperties) {
		return new ExecutionTimerAspect(timerProperties);
	}
}
