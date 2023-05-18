package com.github.mennokemp.minecraft.pluginhelpers.persistence.abstractions;

import java.util.Map;
import java.util.stream.Stream;

import org.bukkit.configuration.ConfigurationSection;

public interface IConfigurationDao extends IReloadable
{
	ConfigurationSection getConfiguration();
	
	ConfigurationSection getConfiguration(String... configurationPath);
	
	Stream<ConfigurationSection> getConfigurations(String... configurationPath);
		
	Map<String, String> getStrings(String... configurationPath);
}
