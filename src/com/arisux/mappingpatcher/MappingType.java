package com.arisux.mappingpatcher;

public enum MappingType
{
	FIELD("field"), METHOD("method"), PARAMETER("parameter");
	
	String name;
	
	MappingType(String name)
	{
		this.name = name;
	}
	
	public String getName()
	{
		return name;
	}
}
