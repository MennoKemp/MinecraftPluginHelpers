package com.github.mennokemp.minecraft.pluginhelpers;

public class Result 
{
	private final boolean isValid;
	private final String message;
	
	private Result(boolean isValid, String message) 
	{
		this.isValid = isValid;
		this.message = message;
	}
	
	public static Result failure(String message)
	{
		return new Result(false, message);
	}
	
	public static Result success()
	{
		return new Result(true, "");
	}
	
	public static Result success(String message)
	{
		return new Result(true, message);
	}
	
	public boolean isSuccessful()
	{
		return isValid;
	}
	
	public String getMessage()
	{
		return message;
	}
}
