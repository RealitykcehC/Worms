package worms.model;

import java.util.ArrayList;

/**
 * A class which implements teams to the game.
 * Every team consists of a group of worms and every team has a name.
 * Friendly fire is allowed, which means that worms of the same team can also kill each other.
 * A game is won by a team (if only the only worms that remain are from the same team). In that case, 
 * all worms who died of that team have also won, although they are dead.
 * A game is won by a worm if that worm is not in a team and is the only worm remaining.
 * This means there are two ways to win a game: as an individual or as a team.
 * 
 * @author Pieter Jan Vingerhoets & Matthijs Nelissen
 * @version 0.1a
 */
public class Team {
	/**
	 * Declaration of variables.
	 */
	private String name;
	private ArrayList<Worm> teamMembers;

	/**
	 * Constructor of the class Team.
	 * The name of the team has to be valid.
	 * 
	 * @param name
	 * 			The name of the team.
	 * @post	This team's name has to be the provided team name
	 * 			| (new this).getName() == name
	 * @throws	IllegalArgumentException
	 * 			The provided name of the team is invalid.
	 * 			| !isValidName(name)
	 */
	public Team(String name) throws IllegalArgumentException {
		if (!isValidTeamName(name))
			throw new IllegalArgumentException();
		this.name = name;
	}

	/**
	 * Function that checks whether or not a provided name is a valid team name.
	 * 
	 * @param name
	 * 			The team name that needs to be checked
	 * @return true
	 * 			The provided name is a valid team name
	 * @return false
	 * 			The provided name is an invalid team name
	 */
	public static boolean isValidTeamName(String name) {
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

	/**
	 * Function that adds a worm to this team.
	 * 
	 * @param worm
	 * 			The worm that has to be added to this team
	 * @post	The provided worm has to be a part of this team
	 * 			| (new this).teamMembers.contains(worm) == true
	 * @throws	IllegalArgumentException
	 * 			The worm has to be a valid worm, i.e. it exists.
	 * 			| worm == null
	 */
	public void addWormToTeam(Worm worm) throws IllegalArgumentException {
		if (worm == null)
			throw new IllegalArgumentException();
		this.teamMembers.add(worm);
	}

	/**
	 * Function that returns the name of this team.
	 * 
	 * @return this.name
	 * 			The name of this team
	 */
	public String getTeamName() {
		return this.name;
	}

	/**
	 * Function that counts and returns the number of survivors in this team.
	 * 
	 * @return counter
	 * 			The number of survivors in this team
	 * @throws	NullPointerException
	 * 			The team must exist, otherwise an exception has to be thrown.
	 * 			| teamMembers == null
	 */
	public int countSurvivors() throws NullPointerException {
		if (teamMembers == null)
			throw new NullPointerException();
		int counter = 0;
		for (Worm worm : teamMembers) {
			if (worm.getActionPoints() > 0 && worm.getHitPoints() > 0)
				counter++;
		}
		return counter;
	}
}
