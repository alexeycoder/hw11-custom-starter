package org.someother.executiontimer;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

@Data
@ConfigurationProperties("exectimer")
public class TimerProperties {

	boolean enable = true;
}
