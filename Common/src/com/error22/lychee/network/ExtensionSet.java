package com.error22.lychee.network;

import java.util.EnumSet;

public class ExtensionSet {
	private EnumSet<NetworkExtension> set;
	
	public ExtensionSet() {
		set = EnumSet.noneOf(NetworkExtension.class);
	}
	
	public void enable(NetworkExtension extension){
		set.add(extension);
	}
	
	public void enableAll(ExtensionSet other){
		set.addAll(other.getInternalSet());
	}
	
	public void disable(NetworkExtension extension){
		set.remove(extension);
	}
	
	public boolean isEnabled(NetworkExtension extension){
		return set.contains(extension);
	}
	
	public boolean areEnabled(NetworkExtension... extensions){
		for(NetworkExtension e : extensions){
			if(!isEnabled(e)){
				return false;
			}
		}
		return true;
	}
	
	public boolean areEnabled(EnumSet<NetworkExtension> requiredExtensions) {
		return set.containsAll(requiredExtensions);
	}
	
	public NetworkExtension[] getAllEnabled(){
		return set.toArray(new NetworkExtension[set.size()]);
	}
	
	public EnumSet<NetworkExtension> getInternalSet() {
		return set;
	}

	
	
}
