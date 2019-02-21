package org.hc.learning.spring.conditional;

public class WindowsListService implements ListService{

	@Override
	public String showCommand() {
		return "dir";
	}

}
