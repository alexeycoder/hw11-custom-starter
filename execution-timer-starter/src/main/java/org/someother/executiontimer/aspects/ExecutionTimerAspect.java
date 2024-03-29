package org.someother.executiontimer.aspects;

import java.time.Duration;
import java.time.temporal.ChronoUnit;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.someother.executiontimer.TimerProperties;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class ExecutionTimerAspect {

	private final TimerProperties properties;

	@Pointcut("@within(org.someother.executiontimer.annotations.Timer)")
	public void beanAnnotatedWithTimer() {
	}

	@Pointcut("@annotation(org.someother.executiontimer.annotations.Timer)")
	public void methodAnnotatedWithTimer() {
	}

	@Around("beanAnnotatedWithTimer() || methodAnnotatedWithTimer()")
	public Object logMethodExecutionTime(ProceedingJoinPoint joinPoint)
			throws Throwable {

		if (!properties.isEnable()) {
			return joinPoint.proceed();
		}

		long time = System.nanoTime();
		Object result = joinPoint.proceed();
		time = System.nanoTime() - time;

		String cls = joinPoint.getTarget().getClass().getSimpleName();
		String mtod = joinPoint.getSignature().getName();
		log.info("{} - {} #({})", cls, mtod, toDurationHumanReadable(time));

		return result;
	}

	/**
	 * Конвертация целочисленного значения времени длительности в наносекундах в
	 * удобочитаемое строковое представление.
	 * 
	 * @param nanoseconds Суммарная длительность в наносекундах.
	 * @return Удобочитаемую строку времени длительности в виде "1 сек 234 мс 555
	 *         мкс".
	 */
	private static String toDurationHumanReadable(long nanoseconds) {
		var duration = Duration.of(nanoseconds, ChronoUnit.NANOS);
		StringBuilder sb = new StringBuilder(Long.toString(duration.getSeconds())).append(" сек ")
				.append(duration.toMillisPart()).append(" мс ")
				.append(duration.toNanosPart() / 1000 - duration.toMillisPart() * 1000).append(" мкс");
		return sb.toString();
	}

	//	public static void main(String[] args) {
	//		System.out.println(toDurationHumanReadable(1_234_555_999L));
	//	}
}
