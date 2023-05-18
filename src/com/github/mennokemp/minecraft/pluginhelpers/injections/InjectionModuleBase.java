package com.github.mennokemp.minecraft.pluginhelpers.injections;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

public abstract class InjectionModuleBase
{
	private Map<Class<?>, Object> bindings = new LinkedHashMap<>();
		    
    @SuppressWarnings("unchecked")
    public <T> T get(Class<T> abstraction)
	{
    	return (T)bindings.get(abstraction);
	}
    
    @SuppressWarnings("unchecked")
	public <T> Collection<T> getAll(Class<T> abstraction)
	{
    	Collection<T> implementations = new ArrayList<T>(); 
		
    	for(Object service : bindings.values())
    	{   
    		if(abstraction.isInstance(service))
    			implementations.add((T)service);    			
    	}

    	return implementations;
	}
    
    protected abstract void registerBindings();
    
    protected <T> T register(Class<T> abstraction, T implementation)
    {
    	bindings.put(abstraction, implementation);
    	return implementation;
    }
}
