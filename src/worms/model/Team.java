package worms.model;

import java.util.ArrayList;

import be.kuleuven.cs.som.annotate.Basic;

/**
 * A class which implements teams to the game.
 * Every team consists of a group of worms and every team has a name.
 * Friendly fire is allowed, which means that worms of the same team can also kill each other.
 * A game is won by a team (if the only worms that remain are from the same team).
 * A game is won by a worm if that worm is not in a team and is the only worm remaining.
 * This means there are two ways to win a game: as an individual or as a team.
 * 
 * @author Pieter Jan Vingerhoets & Matthijs Nelissen
 * @version 1.0
 */
public class Team {
	/**
	 * Declaration of variables.
	 */
	private String name;
	private ArrayList<Worm> teamMembers = new ArrayList<Worm>();

	/**
	 * Constructor of the class Team.
	 * 
	 * @param name
	 * 			The name of the team.
	 * @post	This team's name has to be the provided team name
	 * 			| (new this).getTeamName() == name
	 * @throws	IllegalArgumentException
	 * 			The provided name of the team is invalid.
	 * 			| !isValidTeamName(name)
	 */
	public Team(String name) throws IllegalArgumentException {
		if (!isValidTeamName(name))
			throw new IllegalArgumentException();
		this.name = name;
	}

	/**
	 * Function that returns the name of this team.
	 * 
	 * @return this.name
	 * 			The name of this team
	 */
	@Basic
	public String getTeamName() {
		return this.name;
	}

	/**
	 * Function that returns all live worms in this team (i.e. the worms who are still alive).
	 * 
	 * @return liveWorms
	 * 			An ArrayList containing all of the live worms in this team
	 */
	public ArrayList<Worm> getLiveWormsInTeam() {
		ArrayList<Worm> liveWorms = new ArrayList<Worm>();
		for (Worm liveWorm : this.teamMembers)
			if (liveWorm.isAlive())
				liveWorms.add(liveWorm);
		return liveWorms;	
	}

	/**
	 * Method that adds the provided worm to this team.
	 * 
	 * @param worm
	 * 			The worm that has to be added to this team
	 * @post	The provided worm has to have been added to this team.
	 * 			| (new worm).getTeamName().equals(this.getTeamName())
	 */
	public void addTeamMember(Worm worm) {
		this.teamMembers.add(worm);
	}

	/**
	 * Function that counts and returns the number of survivors in this team.
	 * 
	 * @return counter
	 * 			The number of survivors in this team
	 */
	public int countSurvivors() {
		int counter = 0;
		for (Worm worm : this.teamMembers) {
			if (worm.getActionPoints() > 0 && worm.getHitPoints() > 0)
				counter++;
		}
		return counter;
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
}
