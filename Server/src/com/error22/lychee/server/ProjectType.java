package com.error22.lychee.server;

public enum ProjectType {
	Java("PROJECT_JAVA");

	private String networkName;

	private ProjectType(String networkName) {
		this.networkName = networkName;
	}

	public String getNetworkName() {
		return networkName;
	}

}
