package com.error22.lychee.network;

import java.util.UUID;

public interface IPairedPacket {
	
	public UUID getPairId();
	
	public boolean isPaired();
	
	public boolean isResponse();
	
}
