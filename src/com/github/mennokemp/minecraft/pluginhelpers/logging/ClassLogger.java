package com.github.mennokemp.minecraft.pluginhelpers.logging;

import java.util.logging.Logger;
import java.util.logging.Level;

public class ClassLogger
{
	private Logger logger;
	private String className;
	
	public ClassLogger(Logger logger, String callerName)
	{
		this.logger = logger;
		this.className = callerName;
	}
	
	public void logInfo(String message)
	{
		logger.log(Level.INFO, formatMessage(message));
	}
	
	public void logError(String message) 
	{
		logger.log(Level.SEVERE, formatMessage(message));
	}
	
	public void logError(String message, Throwable throwable) 
	{
		logger.log(Level.SEVERE, formatMessage(message) + "\n" + throwable.getMessage());
	}
	
	private String formatMessage(String message)
	{
		return "[" + className + "] " + message;
	}
}
