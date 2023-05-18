package com.github.mennokemp.minecraft.pluginhelpers.commands;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import com.github.mennokemp.minecraft.pluginhelpers.Result;

public abstract class CommandBase implements CommandExecutor
{
	private final static Logger Logger = Bukkit.getLogger();
	
	protected CommandSender sender;
	protected Command command;
		
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
	{
		this.sender = sender;
		command = cmd;
		
		Bukkit.getLogger().info("Executing command: " + getName() + " " + String.join(" ", args));
		
		Result checkResult = canExecute(args);
		if(!checkResult.isSuccessful())
		{
			String message = "Cannot execute command: " + checkResult.getMessage();
			Logger.severe(message);
			sender.sendMessage(message);
			return false;
		}
		
		Result executionResult = onCommand(args);

		Level logLevel = executionResult.isSuccessful()
				? Level.INFO
				: Level.SEVERE;
		
		Logger.log(logLevel, executionResult.getMessage());		
		return executionResult.isSuccessful();
	}
	
	public String getName()
	{
		String name = this.getClass().getSimpleName();	
		return name.substring(0, name.lastIndexOf("Command"));
	}
		
	protected abstract Result canExecute(String[] args);
	
	protected abstract Result onCommand(String[] args);
}