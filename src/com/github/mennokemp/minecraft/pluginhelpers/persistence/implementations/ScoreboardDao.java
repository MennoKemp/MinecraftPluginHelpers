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
	
	private Objective objective;
	
	protected ScoreboardDao(Scoreboard scoreboard) 
	{
		this.scoreboard = scoreboard;
		
		objective = scoreboard.getObjective(getObjectiveName());
		
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
		scoreboard.getObjective(getObjectiveName()).unregister();
		createObjective();
	}
	
	@Override
	public void setDisplaySlot(DisplaySlot displaySlot)
	{
		objective.setDisplaySlot(displaySlot);
	}
	
	protected abstract String getObjectiveName();
	
	protected abstract String getObjectiveDisplayName();
	
	protected Objective getObjective()
	{
		return objective;
	}
	
	private void createObjective()
	{
		objective = scoreboard.registerNewObjective(getObjectiveName(), Criteria.DUMMY, getObjectiveDisplayName(), RenderType.INTEGER);    	
	}
}