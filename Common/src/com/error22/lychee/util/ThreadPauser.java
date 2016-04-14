package com.error22.lychee.util;

public class ThreadPauser {
	private PauseObj waitingObj, checkObj, msgObj;
	private boolean gotNotice, stackNotice, awaitRechieved, gotCheck;
	private Object msg;

	public ThreadPauser(boolean stackNotice, boolean awaitRechieved) {
		this.awaitRechieved = awaitRechieved;
		this.stackNotice = stackNotice;
		gotNotice = false;
		waitingObj = new PauseObj();
		checkObj = new PauseObj();
		msgObj = new PauseObj();
	}

	public Object awaitPoke() {
		synchronized (waitingObj) {
			if (!stackNotice)
				gotNotice = false;

			while (!gotNotice) {
				try {
					waitingObj.wait();
				} catch (Exception e) {
				}
			}
			gotNotice = false;
		}
		if (awaitRechieved) {
			synchronized (checkObj) {
				gotCheck = true;
				checkObj.notify();
			}
		}
		synchronized (msgObj) {
			return msg;
		}
	}

	public void poke(Object msg) {
		synchronized (msgObj) {
			this.msg = msg;
		}
		synchronized (waitingObj) {
			gotNotice = true;
			waitingObj.notify();
		}
		if (awaitRechieved) {
			synchronized (checkObj) {
				gotCheck = false;
				while (!gotCheck) {
					try {
						checkObj.wait();
					} catch (Exception e) {
					}
				}
			}
		}
	}

	public void reset() {
		poke(new StateReset());
		synchronized (waitingObj) {
			gotNotice = false;
		}
	}

	public static class PauseObj {
	}

	public static class StateReset {
	}
	
	public static class EmptyObj{
	}
}
