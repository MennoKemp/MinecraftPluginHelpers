package com.github.mennokemp.minecraft.pluginhelpers.bukkit;

import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import com.github.mennokemp.minecraft.pluginhelpers.Constants;

public class TaskRunner 
{
	private final Plugin plugin;
	
	private Runnable task;
	private Runnable completionTask;
	private long delay;
	private long interval = Constants.TicksPerSecond;
	private int cycleCount = 1;
	private int currentCycle = 1;
	
	private BukkitRunnable bukkitTask;
	private boolean isRunning;
	
	public TaskRunner(Plugin plugin)
	{
		this.plugin = plugin;
	}
	
	public TaskRunner(Plugin plugin, Runnable task)
	{
		this.plugin = plugin;
		this.task = task;
	}
	
	public TaskRunner setTask(Runnable task)
	{
		this.task = task;
		return this;
	}
	
	public TaskRunner setCompletionTask(Runnable completionTask)
	{
		this.completionTask = completionTask;
		return this;
	}
	
	public TaskRunner setTimeDelay(int delay)
	{
		if(delay < 0)
			throw new IllegalArgumentException("Delay cannot be smaller than 0");
		
		this.delay = delay * Constants.TicksPerSecond;
		return this;
	}
	
	public TaskRunner setTickDelay(int delay)
	{
		if(delay < 0)
			throw new IllegalArgumentException("Delay cannot be smaller than 0");
		
		this.delay = delay;
		return this;
	}
	
	public TaskRunner setTimeInterval(int interval)
	{
		if(interval < 1)
			throw new IllegalArgumentException("Interval cannot be smaller than 1");

		this.interval = interval * Constants.TicksPerSecond;
		return this;
	}
	
	public TaskRunner setTickInterval(int interval, boolean ticks)
	{
		if(interval < 1)
			throw new IllegalArgumentException("Interval cannot be smaller than 1");

		this.interval = interval;
		return this;
	}
	
	public TaskRunner setCycleCount(int cycleCount)
	{
		if(cycleCount < 1)
			throw new IllegalArgumentException("Cycle count cannot be smaller than 1");

		this.cycleCount = cycleCount;
		return this;
	}
		
	public boolean isRunning()
	{
		return isRunning;
	}
	
	public int getRemainingCycleCount()
	{
		return cycleCount - currentCycle + 1;
	}
	
	public void run()
	{
		if(task == null)
			throw new IllegalStateException("Task not set.");		
	
		if(isRunning)
			throw new IllegalStateException("Task is already running.");
		
		bukkitTask = new BukkitRunnable()
		{
			@Override
			public void run()
			{
				task.run();
				isRunning = false;
			}
		};
		bukkitTask.runTaskLater(plugin, delay);
		
		isRunning = true;
	}
		
	public void runRepeatedly()
	{
		if(task == null)
			throw new IllegalStateException("Task not set.");
		
		if(interval < 1)
			throw new IllegalStateException("Interval must be larger than 0.");
		
		if(isRunning)
			throw new IllegalStateException("Task is already running.");

		bukkitTask = new BukkitRunnable()
		{	
			@Override
			public void run()
			{
				task.run();
				
				if(currentCycle >= cycleCount)
				{
					cancel();
					completionTask.run();		
					isRunning = false;
					return;
				}
				
				currentCycle++;
			}
		};
		bukkitTask.runTaskTimer(plugin, delay, interval);
		
		isRunning = true;
	}
	
	public void cancel()
	{
		if(isRunning)
		{
			bukkitTask.cancel();
			isRunning = false;			
		}
	}
}