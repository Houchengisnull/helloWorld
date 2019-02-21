package org.hc.learning.spring.conditional;

public class LinuxListService implements ListService{

	@Override
	public String showCommand() {
		return "ls";
	}

}
