package com.github.mennokemp.minecraft.pluginhelpers.bukkit;

import java.util.Arrays;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.World;

public class LocationParser
{	
	private final World world;
	
	public LocationParser(World world)
	{
		this.world = world;
	}
	
	public Location fromCoordinates(int x, int y, int z)
	{
		return new Location(world, x, y, z);
	}
	
	public Location fromString(String coordinates)
	{
		List<Integer> coords = Arrays.asList(coordinates.split(","))
			.stream()
			.map(c -> Integer.parseInt(c))
			.toList();
		
		return coords.size() == 3
				? new Location(world, coords.get(0), coords.get(1), coords.get(2))
				: new Location(world, coords.get(0), coords.get(1), coords.get(2), coords.get(3), coords.get(4));
	}
}
