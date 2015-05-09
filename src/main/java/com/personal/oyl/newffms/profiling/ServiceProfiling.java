package com.personal.oyl.newffms.profiling;

import org.aspectj.lang.ProceedingJoinPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ServiceProfiling {
	private static final Logger log = LoggerFactory.getLogger(ServiceProfiling.class);
	
	private int alarmValue;
	
	public Object arround(ProceedingJoinPoint pjp) throws Throwable {
		
		String className  = pjp.getTarget().getClass().getSimpleName();
		String methodName = pjp.getSignature().getName();
		
		long startTime = System.currentTimeMillis();
		
		Object retVal = pjp.proceed();
		
		long period = System.currentTimeMillis() - startTime;
		
		if (period > alarmValue)
			log.info("Method [" + className + "." + methodName + "] takes: " + period + " ms.");
		
		return retVal;
	}

	public int getAlarmValue() {
		return alarmValue;
	}

	public void setAlarmValue(int alarmValue) {
		this.alarmValue = alarmValue;
	}
}
