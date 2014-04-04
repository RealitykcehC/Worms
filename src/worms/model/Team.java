package worms.model;

import java.util.ArrayList;

/**
 * ...
 * 
 * @author PieterJan
 */
public class Team {
	/**
	 * Declaration of variables.
	 */
	private String name;
	private ArrayList<Worm> teamMembers;

	public Team(String name) throws IllegalArgumentException {
		this.name = name;
	}

	public static boolean isValidTeamName(String name)
			throws NullPointerException {
		if (name == null)
			throw new NullPointerException();
		if (name.length() >= 2 && Character.isUpperCase(name.charAt(0))) {
			for (int i = 0; i < name.length(); i++) {
				if (!(Character.isUpperCase(name.charAt(i)))
						&& !(Character.isLowerCase(name.charAt(i)))) {
					return false;
				}
			}
			return true;
		}
		return false;
	}

	public void addWormToTeam(Worm worm) throws IllegalArgumentException {
		teamMembers.add(worm);
	}

	public String getTeamName() {
		return this.name;
	}

	public int countSurvivors() throws NullPointerException {
		int counter = 0;
		for (Worm worm : teamMembers) {
			if (worm.getActionPoints() > 0 && worm.getHitPoints() > 0)
				counter++;
		}
		return counter;
	}
}
