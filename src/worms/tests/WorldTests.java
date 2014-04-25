package worms.test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Random;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import worms.model.*;


/**
 * A JUnit Test Suite to test all public methods of the class World (contained in the worms.model.World package).
 * Also checked in this Test Suite are the following:
 * - A constructor of the class World, with valid arguments;
 * - A constructor of the class World, with invalid width;
 * - A constructor of the class World, with invalid height.
 * 
 * All public methods of the class World are tested for legal and, where possible, illegal cases.
 * 
 * @author Pieter Jan Vingerhoets & Matthijs Nelissen
 * @version 1.0
 */

public class WorldTests {
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}
	
	/**
	 * Declaring variables.
	 */
	private Random randomSeed = new Random();
	private boolean [][] passableMap = {{false, false, false, false, false},
									 {false, true, true, true, false},
									 {false, true, true, true, false},
									 {false, true, true, true, false},
									 {false, false, false, false, false}};
	private World world = new World (5.0, 5.0, this.passableMap, this.randomSeed);
	
	/**
	 * Function that tests a legal case of creating a world.
	 */
	@Test
	public void test_canCreateWorld_LegalCase() {
		try {
			new World(5.0, 5.0, this.passableMap, this.randomSeed);
		} catch (IllegalArgumentException e) {
			fail("Invalid width or height");
		}
	}
	
	/**
	 * Function that tests an illegal case of creating a world.
	 * In this test, we make the width too small.
	 */
	@Test
	public void test_canCreateWorld_IllegalCase1() {
		try {
			new World(-1.0, 5.0, this.passableMap, this.randomSeed);
		} catch (IllegalArgumentException e) {
			fail("Invalid width or height");
		}
	}
	
	/**
	 * Function that tests an illegal case of creating a world.
	 * In this test, we make the width too great.
	 */
	@Test
	public void test_canCreateWorld_IllegalCase2() {
		try {
			new World(Double.POSITIVE_INFINITY, 5.0, this.passableMap, this.randomSeed);
		} catch (IllegalArgumentException e) {
			fail("Invalid width or height");
		}
	}
	
	/**
	 * Function that tests an illegal case of creating a world.
	 * In this test, we make the height too small.
	 */
	@Test
	public void test_canCreateWorld_IllegalCase3() {
		try {
			new World(5.0, -1.0, this.passableMap, this.randomSeed);
		} catch (IllegalArgumentException e) {
			fail("Invalid width or height");
		}
	}
	
	/**
	 * Function that tests an illegal case of creating a world.
	 * In this test, we make the height too great.
	 */
	@Test
	public void test_canCreateWorld_IllegalCase4() {
		try {
			new World(5.0, Double.POSITIVE_INFINITY, this.passableMap, this.randomSeed);
		} catch (IllegalArgumentException e) {
			fail("Invalid width or height");
		}
	}
	
	/**
	 * Function that tests the method getWidth() for the world.
	 * There is no illegal case for this method.
	 */
	@Test
	public void test_world_getWidth_LegalCase() {
		assertTrue(this.world.getWidth() == 5.0);
	}
	
	/**
	 * Function that tests the method getHeight() for the world.
	 * There is no illegal case for this method.
	 */
	@Test
	public void test_world_getHeight_LegalCase() {
		assertTrue(this.world.getHeight() == 5.0);
	}
	
	/**
	 * Function that tests the method getRandomSeed() for the world.
	 * There is no illegal case for this method.
	 */
	@Test
	public void test_world_getRandomSeed_LegalCase() {
		assertTrue(this.world.getRandomSeed() == this.randomSeed);
	}
	
	/**
	 * Function that tests the method getCurrentWorm() for the world.
	 * There is no illegal case for this method.
	 */
	@Test
	public void test_world_getCurrentWorm_LegalCase() {
		this.world.addWormToWorld();
		this.world.addWormToWorld();
		this.world.startGame();
		assertTrue(this.world.getCurrentWorm() == ((ArrayList<Worm>) this.world.getWorms()).get(0));
	}
	
	/**
	 * Function that tests the method getActiveProjectile() for the world.
	 * There is no illegal case for this method.
	 */
	@Test
	public void test_world_getActiveProjectile_LegalCase() {
		this.world.addWormToWorld();
		this.world.addWormToWorld();
		this.world.startGame();
		assertTrue(this.world.getActiveProjectile() == this.world.getCurrentWorm().getProjectile());
	}
	
	/**
	 * Function that tests the method getFood() for the world.
	 * There is no illegal case for this method.
	 */
	@Test
	public void test_world_getFood_LegalCase() {
		Food food = this.world.createFood(1.0, 1.0);
		ArrayList<Food> foodInWorld = (ArrayList<Food>) this.world.getFood();
		assertTrue(foodInWorld.size() == 1 && foodInWorld.contains(food));
	}
	
	/**
	 * Function that tests the method getTeams() for the world.
	 */
	@Test
	public void test_world_getTeams_LegalCase() {
		this.world.addEmptyTeam("ValidTeamName");
		assertTrue(this.world.getTeams().size() == 1);
	}
	
	/**
	 * Function that tests the method getWorms() for the world.
	 * There is no illegal case for this method.
	 */
	@Test
	public void test_world_getWorms_LegalCase() {
		Worm worm = this.world.createWorm(2.1, 2.1, 0.0, 1.0, "AllValidWorm");
		ArrayList<Worm> wormsInWorld = (ArrayList<Worm>) this.world.getWorms();
		assertTrue(wormsInWorld.size() == 1 && wormsInWorld.contains(worm));
	}
	
	/**
	 * Function that tests the method getObjectsInWorld() for the world.
	 * There is no illegal case for this method.
	 */
	@Test
	public void test_world_getObjectsInWorld_LegalCase() {
		Worm worm1 = this.world.createWorm(2.1, 2.1, 0.0, 1.0, "AllValidWorm");
		Worm worm2 = this.world.createWorm(2.9, 2.9, 0.0, 1.0, "AllValidWorm");
		Food food = this.world.createFood(1.0, 1.0);
		this.world.startGame();
		assertTrue(this.world.getObjectsInWorld().contains(worm1) && 
				this.world.getObjectsInWorld().contains(worm2) && 
				this.world.getObjectsInWorld().contains(food) &&
				this.world.getObjectsInWorld().contains(this.world.getActiveProjectile()));
	}
	
	/**
	 * Function that tests the method getWinner() for the world.
	 * There is no illegal case for this method.
	 */
	@Test
	public void test_world_getWinner_LegalCase() {
		this.world.addWormToWorld();
		this.world.startGame();
		assertTrue(this.world.getWinner() == ((ArrayList<Worm>) this.world.getWorms()).get(0).getName());
	}
	
	/**
	 * Function that tests a legal case of the method addWormToWorld() for the world.
	 */
	@Test
	public void test_world_addWormToWorld_LegalCase() {
		this.world.addWormToWorld();
		assertTrue(this.world.getWorms().size() == 1);
	}
	
	/**
	 * Function that tests an illegal case of the method addWormToWorld() for the world.
	 * In this test, we start the game and then try to add another worm.
	 */
	@Test
	public void test_world_addWormToWorld_IllegalCase() {
		this.world.addWormToWorld();
		this.world.addWormToWorld();
		this.world.startGame();
		this.world.addWormToWorld();
		if (this.world.getWorms().size() != 3)
			fail("The game has already been started.");
	}
	
	/**
	 * Function that tests a legal case of the method removeWormFromWorld() for the world.
	 */
	@Test
	public void test_world_removeWormFromWorld_LegalCase() {
		this.world.addWormToWorld();
		try {
			this.world.removeWormFromWorld(((ArrayList<Worm>) this.world.getWorms()).get(0));
		} catch (IllegalArgumentException e) {
			fail("Invalid argument.");
		}
		assertTrue(this.world.getWorms().size() == 0);
	}
	
	/**
	 * Function that tests an illegal case of the method removeWormFromWorld() for the world.
	 * In this test, we want to remove a worm (here: null reference) from the world.
	 */
	@Test
	public void test_world_removeWormFromWorld_IllegalCase() {
		try {
			this.world.removeWormFromWorld(null);
		} catch (IllegalArgumentException e) {
			fail("Invalid argument");
		}
	}
	
	/**
	 * Function that tests a legal case of the method addFoodToWorld() for the world.
	 */
	@Test
	public void test_world_addFoodToWorld_LegalCase() {
		this.world.addFoodToWorld();
		assertTrue(this.world.getFood().size() == 1);
	}
	
	/**
	 * Function that tests an illegal case of the method addFoodToWorld() for the world.
	 * In this test, we start the game and then try to add another piece of food.
	 */
	@Test
	public void test_world_addFoodToWorld_IllegalCase() {
		this.world.addWormToWorld();
		this.world.addWormToWorld();
		this.world.addFoodToWorld();
		this.world.addFoodToWorld();
		this.world.startGame();
		this.world.addFoodToWorld();
		if (this.world.getFood().size() != 3)
			fail("The game has already been started.");
	}
	
	/**
	 * Function that tests a legal case of the method removeFoodFromWorld() for the world.
	 */
	@Test
	public void test_world_removeFoodFromWorld_LegalCase() {
		this.world.addFoodToWorld();
		try {
			this.world.removeFoodFromWorld(((ArrayList<Food>) this.world.getFood()).get(0));
		} catch (IllegalArgumentException e) {
			fail("Invalid argument.");
		}
		assertTrue(this.world.getFood().size() == 0);
	}
	
	/**
	 * Function that tests an illegal case of the method removeFoodFromWorld() for the world.
	 * In this test, we want to remove a piece of food (here: null reference) from the world.
	 */
	@Test
	public void test_world_removeFoodFromWorld_IllegalCase() {
		try {
			this.world.removeFoodFromWorld(null);
		} catch (IllegalArgumentException e) {
			fail("Invalid argument");
		}
	}
	
	/**
	 * Function that tests a legal case of the method addEmptyTeam() for the world.
	 */
	@Test
	public void test_world_addEmptyTeam_LegalCase() {
		String validTeamName = "TeamName";
		if (!Team.isValidTeamName(validTeamName))
			fail("Invalid team name.");
		try {
			this.world.addEmptyTeam(validTeamName);
		} catch (RuntimeException e) {
			fail("Cannot create another team: the maximum amount of teams has already been created.");
		}
	}
	
	/**
	 * Function that tests an illegal case of the method addEmptyTeam() for the world.
	 * In this test, the team name is invalid.
	 */
	@Test
	public void test_world_addEmptyTeam_IllegalCase1() {
		String invalidTeamName = "teamName";
		if (!Team.isValidTeamName(invalidTeamName))
			fail("Invalid team name.");
		try {
			this.world.addEmptyTeam(invalidTeamName);
		} catch (RuntimeException e) {
			fail("Cannot create another team: the maximum amount of teams has already been created.");
		}
	}
	
	/**
	 * Function that tests an illegal case of the method addEmptyTeam() for the world.
	 * In this test, the maximum amount of teams (10) is already reached.
	 */
	@Test
	public void test_world_addEmptyTeam_IllegalCase2() {
		String validTeamName = "TeamName";
		this.world.addEmptyTeam("One");
		this.world.addEmptyTeam("Two");
		this.world.addEmptyTeam("Three");
		this.world.addEmptyTeam("Four");
		this.world.addEmptyTeam("Five");
		this.world.addEmptyTeam("Six");
		this.world.addEmptyTeam("Seven");
		this.world.addEmptyTeam("Eight");
		this.world.addEmptyTeam("Nine");
		this.world.addEmptyTeam("Ten");
		if (!Team.isValidTeamName(validTeamName))
			fail("Invalid team name.");
		try {
			this.world.addEmptyTeam(validTeamName);
		} catch (RuntimeException e) {
			fail("Cannot create another team: the maximum amount of teams has already been created.");
		}
	}
	
	/**
	 * Function that tests a legal case of the method removeProjectileFromWorld() for the world.
	 */
	@Test
	public void test_world_removeProjectileFromWorld_LegalCase() {
		this.world.addWormToWorld();
		this.world.addWormToWorld();
		this.world.startGame();
		try {
			this.world.removeProjectileFromWorld(this.world.getCurrentWorm().getProjectile());
		} catch (IllegalArgumentException e) {
			fail("Invalid argument.");
		}
		assertTrue(this.world.getActiveProjectile().isTerminated());
	}
	
	/**
	 * Function that tests an illegal case of the method removeProjectileFromWorld() for the world.
	 * In this test, we want to remove a projectile (here: null reference) from the world.
	 */
	@Test
	public void test_world_removeProjectileFromWorld_IllegalCase() {
		try {
			this.world.removeProjectileFromWorld(null);
		} catch (IllegalArgumentException e) {
			fail("Invalid argument");
		}
	}
	
	/**
	 * Function that tests a legal case of the method createFood() for the world.
	 */
	@Test
	public void test_world_createFood_LegalCase() {
		try {
			this.world.createFood(1.0, 1.0);
		} catch (RuntimeException e) {
			fail("The game has already been started.");
		}
		assertTrue(this.world.getFood().size() == 1);
	}
	
	/**
	 * Function that tests an illegal case of the method createFood() for the world.
	 * For this test, we start the game and then try to create another piece of food.
	 */
	@Test
	public void test_world_createFood_IllegalCase() {
		this.world.addWormToWorld();
		this.world.addWormToWorld();
		this.world.startGame();
		try {
			this.world.createFood(1.0, 1.0);
		} catch (RuntimeException e) {
			fail("The game has already been started.");
		}
	}
	
	/**
	 * Function that tests a legal case of the method createWorm() for the world.
	 */
	@Test
	public void test_world_createWorm_LegalCase() {
		try {
			this.world.createWorm(2.1, 2.1, 0.0, 1.0, "AllValidWorm");
		} catch (RuntimeException e) {
			fail("The game has already been started.");
		}
		assertTrue(this.world.getWorms().size() == 1);
	}
	
	/**
	 * Function that tests an illegal case of the method createWorm() for the world.
	 * For this test, we start the game and then try to create another worm.
	 */
	@Test
	public void test_world_createWorm_IllegalCase() {
		this.world.addWormToWorld();
		this.world.addWormToWorld();
		this.world.startGame();
		try {
			this.world.createWorm(2.1, 2.1, 0.0, 1.0, "AllValidWorm");
		} catch (RuntimeException e) {
			fail("The game has already been started.");
		}
	}
	
	/**
	 * Function that tests a legal case of the method startGame() for the world.
	 * There is no illegal case for this method.
	 */
	@Test
	public void test_world_startGame_LegalCase() {
		this.world.addWormToWorld();
		this.world.addWormToWorld();
		this.world.startGame();
		assertTrue(this.world.getCurrentWorm() == ((ArrayList<Worm>) this.world.getWorms()).get(0));
	}
	
	/**
	 * Function that tests a legal case of the method startNextTurn() for the world.
	 * There is no illegal case for this method.
	 */
	@Test
	public void test_world_startNextTurn_LegalCase() {
		this.world.addWormToWorld();
		this.world.addWormToWorld();
		this.world.startGame();
		this.world.startNextTurn();
		assertTrue(this.world.getCurrentWorm() == ((ArrayList<Worm>) this.world.getWorms()).get(1));
	}
	
	/**
	 * Function that tests a legal case of the method isAdjacent() for the world.
	 * There is no illegal case for this method.
	 */
	@Test
	public void test_world_isAdjacent_LegalCase() {
		assertTrue(this.world.isAdjacent(2.1, 2.1, 1.0));
	}
	
	/**
	 * Function that tests a legal case of the method isImpassable() for the world.
	 * There is no illegal case for this method.
	 */
	@Test
	public void test_world_isImpassble_LegalCase() {
		assertTrue(this.world.isImpassable(2.0, 2.0, 1.0));
	}
	
	/**
	 * Function that tests a legal case of the method isPassable() for the world.
	 * There is no illegal case for this method.
	 */
	@Test
	public void test_world_isPassble_LegalCase() {
		assertTrue(this.world.isPassable(2.5, 2.5, 1.0));
	}
	
	/**
	 * Function that tests a legal case of the method isGameFinished() for the world.
	 */
	@Test
	public void test_world_isGameFinished_LegalCase1() {
		assertFalse(this.world.isGameFinished());
	}
	
	/**
	 * Function that tests a legal case of the method isGameFinished() for the world.
	 */
	@Test
	public void test_world_isGameFinished_LegalCase2() {
		this.world.startGame();
		assertTrue(this.world.isGameFinished());
	}
	
	/**
	 * Function that tests a legal case of the method liesInWorld() for the world.
	 */
	@Test
	public void test_world_liesInWorld_LegalCase() {
		assertTrue(this.world.liesInWorld(2.1, 2.1, 1.0));
	}
	
	/**
	 * Function that tests an illegal case of the method liesInWorld() for the world.
	 * For this test, the x-coordinate is too small.
	 */
	@Test
	public void test_world_liesInWorld_IllegalCase1() {
		if (!this.world.liesInWorld(-.1, 2.1, 1.0))
			fail("The object with those properties does not lie in the world.");
	}
	
	/**
	 * Function that tests an illegal case of the method liesInWorld() for the world.
	 * For this test, the x-coordinate is too big.
	 */
	@Test
	public void test_world_liesInWorld_IllegalCase2() {
		if (!this.world.liesInWorld(5.1, 2.1, 1.0))
			fail("The object with those properties does not lie in the world.");
	}
	
	/**
	 * Function that tests an illegal case of the method liesInWorld() for the world.
	 * For this test, the y-coordinate is too small.
	 */
	@Test
	public void test_world_liesInWorld_IllegalCase3() {
		if (!this.world.liesInWorld(2.1, -.1, 1.0))
			fail("The object with those properties does not lie in the world.");
	}
	
	/**
	 * Function that tests an illegal case of the method liesInWorld() for the world.
	 * For this test, the y-coordinate is too big.
	 */
	@Test
	public void test_world_liesInWorld_IllegalCase4() {
		if (!this.world.liesInWorld(2.1, 5.1, 1.0))
			fail("The object with those properties does not lie in the world.");
	}
	
	/**
	 * Function that tests a legal case of the method isValidWidth() for the world.
	 */
	@Test
	public void test_world_isValidWidth_LegalCase() {
		assertTrue(World.isValidWidth(50));
	}
	
	/**
	 * Function that tests an illegal case of the method isValidWidth() for the world.
	 * For this test, we test a width that is less than the lower boundary for width of a world.
	 */
	@Test
	public void test_world_isValidWidth_IllegalCase1() {
		if (!World.isValidWidth(-1))
			fail("The provided width is invalid.");
	}
	
	/**
	 * Function that tests an illegal case of the method isValidWidth() for the world.
	 * For this test, we test a width that is greater than the upper boundary for width of a world.
	 */
	@Test
	public void test_world_isValidWidth_IllegalCase2() {
		if (!World.isValidWidth(Double.POSITIVE_INFINITY))
			fail("The provided width is invalid.");
	}
	
	/**
	 * Function that tests a legal case of the method isValidHeight() for the world.
	 */
	@Test
	public void test_world_isValidHeight_LegalCase() {
		assertTrue(World.isValidHeight(50));
	}
	
	/**
	 * Function that tests an illegal case of the method isValidHeight() for the world.
	 * For this test, we test a height that is less than the lower boundary for height of a world.
	 */
	@Test
	public void test_world_isValidHeight_IllegalCase1() {
		if (!World.isValidHeight(-1))
			fail("The provided height is invalid.");
	}
	
	/**
	 * Function that tests an illegal case of the method isValidHeight() for the world.
	 * For this test, we test a height that is greater than the upper boundary for height of a world.
	 */
	@Test
	public void test_world_isValidHeight_IllegalCase2() {
		if (!World.isValidHeight(Double.POSITIVE_INFINITY))
			fail("The provided height is invalid.");
	}
}
