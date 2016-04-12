package com.error22.lychee.network;

import java.util.UUID;

public interface IPairablePacket extends IPacket{
	
	public boolean isPaired();
	
	public UUID getPairId();
	
}
