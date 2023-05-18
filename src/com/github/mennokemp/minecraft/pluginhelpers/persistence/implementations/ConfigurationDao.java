package com.github.mennokemp.minecraft.pluginhelpers.persistence.implementations;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.plugin.Plugin;

import com.github.mennokemp.minecraft.pluginhelpers.persistence.abstractions.IConfigurationDao;

public class ConfigurationDao implements IConfigurationDao
{
	private final Plugin plugin;
	
	private ConfigurationSection configuration;
	
	public ConfigurationDao(Plugin plugin)
	{
		this.plugin = plugin;
		
		plugin.saveDefaultConfig();
		reload();
	}
		
	@Override
	public void reload()
	{
		configuration = plugin.getConfig();
	}

	@Override
	public ConfigurationSection getConfiguration()
	{
		return configuration;
	}
	
	@Override
	public ConfigurationSection getConfiguration(String... configurationPath)
	{
		ConfigurationSection configuration = this.configuration;
		
		for(String path : configurationPath)
			configuration = configuration.getConfigurationSection(path);
		
		return configuration;
	}

	@Override
	public Stream<ConfigurationSection> getConfigurations(String... configurationPath)
	{
		ConfigurationSection configuration = getConfiguration(configurationPath);
		
		return configuration
				.getKeys(false)
				.stream()
				.map(k -> configuration.getConfigurationSection(k));
	}

	@Override
	public Map<String, String> getStrings(String... configurationPath)
	{
		ConfigurationSection configurationSection = getConfiguration(configurationPath);
		
		return configurationSection
				.getKeys(false)
				.stream()
				.collect(Collectors.toMap(k -> k, k -> configurationSection.getString(k)));
	}
}
