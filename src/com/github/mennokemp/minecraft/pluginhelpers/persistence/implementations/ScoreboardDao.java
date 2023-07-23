package com.github.mennokemp.minecraft.pluginhelpers.persistence.implementations;

import org.bukkit.scoreboard.Criteria;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.RenderType;
import org.bukkit.scoreboard.Scoreboard;

import com.github.mennokemp.minecraft.pluginhelpers.persistence.abstractions.IScoreboardDao;

public abstract class ScoreboardDao<T> implements IScoreboardDao<T>
{
	private final Scoreboard scoreboard;
	
	private final String objectiveName;
	private final String displayName;
	
	private Objective objective;
	
	protected ScoreboardDao(Scoreboard scoreboard, String objectiveName, String displayName) 
	{
		this.scoreboard = scoreboard;
		this.objectiveName = objectiveName;
		this.displayName = displayName;
		
		objective = scoreboard.getObjective(objectiveName);
		
		if(objective == null)
			createObjective();
	}
	
	@Override
	public int getValue(T valueType)
	{
		return objective.getScore(valueType.toString()).getScore();
	}
	
	@Override
	public void setValue(T valueType, int value)
	{
		objective.getScore(valueType.toString()).setScore(value);
	}
	
	@Override
	public void clear()
	{
		scoreboard.getObjective(objectiveName).unregister();
		createObjective();
	}
	
	@Override
	public void setDisplaySlot(DisplaySlot displaySlot)
	{
		objective.setDisplaySlot(displaySlot);
	}
	
	protected Objective getObjective()
	{
		return objective;
	}
	
	private void createObjective()
	{
		objective = scoreboard.registerNewObjective(objectiveName, Criteria.DUMMY, displayName, RenderType.INTEGER);    	
	}
}