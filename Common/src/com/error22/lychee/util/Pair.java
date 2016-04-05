package com.error22.lychee.util;

public class Pair<TFirst, TLast> {
	private TFirst first;
	private TLast last;

	public Pair(TFirst first, TLast last) {
		this.first = first;
		this.last = last;
	}

	public TFirst getFirst() {
		return first;
	}

	public TLast getLast() {
		return last;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((first == null) ? 0 : first.hashCode());
		result = prime * result + ((last == null) ? 0 : last.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Pair other = (Pair) obj;
		if (first == null) {
			if (other.first != null)
				return false;
		} else if (!first.equals(other.first))
			return false;
		if (last == null) {
			if (other.last != null)
				return false;
		} else if (!last.equals(other.last))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Pair [first=" + first + ", last=" + last + "]";
	}

}
