package com.error22.lychee.server;

import java.util.UUID;

public interface IProject {

	public UUID getId();

	public String getName();

	public ProjectType getType();

}
