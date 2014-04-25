package worms.test;

import static org.junit.Assert.*;

import java.util.Random;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import worms.gui.GUIConstants;
import worms.model.*;
import worms.util.Util;

/**
 * A JUnit Test Suite to test the public methods in the class Worm (contained in worm.model.Worm package).
 * The following constructors are also tested:
 * - A constructor whose arguments are all valid.
 * - A constructor whose x-coordinate is invalid.
 * - A constructor whose y-coordinate is invalid.
 * - A constructor whose radius is invalid.
 * - A constructor whose name is invalid.
 * - A constructor whose world is invalid (i.e. null).
 * A constructor with more than one invalid argument, would behave in the same way as one where only one argument
 * is invalid. These won't be tested.
 * 
 * All public methods are tested for legal and, where possible, illegal cases.
 * 
 * @author Pieter Jan Vingerhoets & Matthijs Nelissen
 * @version 2.0
 */
public class WormTests {
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
	private boolean [][] passable = {{false, false, false, false, false},
									 {false, true, true, true, false},
									 {false, true, true, true, false},
									 {false, true, true, true, false},
									 {false, false, false, false, false}};
	private World world = new World (5.0, 5.0, passable, randomSeed);

	/**
	 * Function that tests whether a valid worm can be created. If it can't, the test will fail.
	 */
	@Test
	public void test_canCreateWorm_LegalCase() {
		try {
			@SuppressWarnings("unused")
			Worm wormAllValid = new Worm(world, 3.0, 3.0, 0.0, 1.0, "AllValidWorm");
		} catch (IllegalArgumentException e) {
			fail("An invalid argument is passed.");
		}
	}

	/**
	 * Function that tests whether a worm can be created with an invalid x-coordinate. If it can't, the test
	 * will fail.
	 * In reality, worms are added from one of the sides of the world and are then moved to the middle.
	 * This means that a worm can never be located outside the world when they first are created.
	 */
	@Test
	public void test_canCreateWorm_InvalidX() {
		Worm wormInvalidX = null;
		try {
			wormInvalidX = new Worm(this.world, 6.0, 3.0, 0.0, 1.0, "InvalidXWorm");
		} catch (IllegalArgumentException e) {
			fail();
		}
		if (!wormInvalidX.getWorld().liesInWorld(wormInvalidX.getX(), wormInvalidX.getY(), wormInvalidX.getRadius()))
			fail("The x-coordinate is invalid.");
	}

	/**
	 * Function that tests whether a worm can be created with an invalid y-coordinate. If it can't, the test
	 * will fail.
	 * In reality, worms are added from one of the sides of the world and are then moved to the middle.
	 * This means that a worm can never be located outside the world when they first are created.
	 */
	@Test
	public void test_canCreateWorm_InvalidY() {
		Worm wormInvalidY = null;
		try {
			wormInvalidY = new Worm(this.world, 3.0, 6.0, 0.0, 1.0, "InvalidYWorm");
		} catch (IllegalArgumentException e) {
			fail();
		}
		if (!wormInvalidY.getWorld().liesInWorld(wormInvalidY.getX(), wormInvalidY.getY(), wormInvalidY.getRadius()))
			fail("The y-coordinate is invalid.");
	}

	/**
	 * Function that tests whether a worm can be created with an invalid radius. If it can't, the test
	 * will fail.
	 */
	@Test
	public void test_canCreateWorm_InvalidRadius() {
		try {
			@SuppressWarnings("unused")
			Worm wormInvalidRadius = new Worm(this.world, 3.0, 3.0, 0.0, 0.1, "InvalidYWorm");
		} catch (IllegalArgumentException e) {
			fail("Invalid radius.");
		}
	}

	/**
	 * Function that tests whether a worm can be created with an invalid name. If it can't, the test will fail.
	 */
	@Test
	public void test_canCreateWorm_InvalidName() {
		try {
			@SuppressWarnings("unused")
			Worm wormInvalidName = new Worm(this.world, 3.0, 3.0, 0.0, 1.0, "InvalidNameWorm#");
		} catch (IllegalArgumentException e) {
			fail("The name is invalid.");
		}
	}
	
	@Test
	public void test_canCreateWorm_InvalidWorld() {
		try {
			@SuppressWarnings("unused")
			Worm wormInvalidWorld = new Worm(null, 3.0, 3.0, 0.0, 1.0, "InvalidWorldWorm");
		} catch (IllegalArgumentException e) {
			fail("The world is invalid.");
		}
	}

	/**
	 * Function that tests the getX() method for a worm. There can be no illegal case of this method.
	 */
	@Test
	public void test_worm_getX_LegalCase() {
		Worm worm = new Worm(this.world, 1.0, 1.0, 0.0, 1.0, "AllValidWorm");
		assertTrue(worm.getX() == 1.0);
	}

	/**
	 * Function that tests the getY() method for a worm. There can be no illegal case of this method.
	 */
	@Test
	public void test_worm_getY_LegalCase() {
		Worm worm = new Worm(this.world, 1.0, 1.0, 0.0, 1.0, "AllValidWorm");
		assertTrue(worm.getY() == 1.0);
	}

	/**
	 * Function that tests the getRadius() method for a worm. There can be no illegal case of this method.
	 */
	@Test
	public void test_worm_getRadius_LegalCase() {
		Worm worm = new Worm(this.world, 1.0, 1.0, 0.0, 1.0, "AllValidWorm");
		assertTrue(worm.getRadius() == 1.0);
	}

	/**
	 * Function that tests the getOrientation() method for a worm. There can be no illegal case of this method.
	 */
	@Test
	public void test_worm_getOrientation_LegalCase() {
		Worm worm = new Worm(this.world, 1.0, 1.0, 0.0, 1.0, "AllValidWorm");
		assertTrue(worm.getOrientation() == 0.0);
	}
	
	/**
	 * Function that tests a legal case of the method canFall() for a worm.
	 */
	@Test
	public void test_worm_canFall_LegalCase() {
		Worm canFallLegalWorm = new Worm(this.world, 2.5, 2.5, 0.0, 1.0, "CanFallLegalWorm");
		assertTrue(canFallLegalWorm.canFall());
	}
	
	/**
	 * Function that tests an illegal case of the method canFall() for a worm.
	 */
	@Test
	public void test_worm_canFall_IllegalCase() {
		Worm canFallLegalWorm = new Worm(this.world, 2.1, 2.1, 0.0, 1.0, "CanFallLegalWorm");
		if (!canFallLegalWorm.canFall())
			fail("This worm cannot fall.");
	}
	
	/**
	 * Function that tests a legal case of the fall() method for a worm.
	 */
	@Test
	public void test_worm_fall_LegalCase() {
		Worm fallWorm = new Worm(this.world, 2.5, 2.5, 0.0, 1.0, "FallWorm");
		fallWorm.fall();
		assertTrue(fallWorm.getX() == 2.5 && fallWorm.getY() != 2.5);
	}
	
	/**
	 * Function that tests an illegal case of the fall() method for a worm.
	 */
	@Test
	public void test_worm_fall_IllegalCase() {
		Worm fallWorm = new Worm(this.world, 2.1, 2.1, 0.0, 1.0, "FallWorm");
		fallWorm.fall();
		if (fallWorm.getX() == 2.1 && fallWorm.getY() == 2.1)
			fail("This worm was unable to fall.");
	}
	
	/**
	 * Function that tests a legal case of the shoot() method of a worm.
	 */
	@Test
	public void test_worm_shoot_LegalCase() {
		Worm worm = new Worm(this.world, 2.1, 2.1, 0.0, 1.0, "AllValidWorm");
		int oldActionPoints = worm.getActionPoints();
		try {
			worm.shoot(100);
		} catch (IllegalArgumentException e){
			fail("Invalid yield.");
		} catch (ArithmeticException e) {
			fail("This worm cannot shoot.");
		}
		assertTrue(oldActionPoints - worm.getProjectile().getActionPointsCost()== worm.getActionPoints());
	}
	
	/**
	 * Function that tests an illegal case of the shoot() method of a worm.
	 * In this test the yield will be less than 0.
	 */
	@Test
	public void test_worm_shoot_IllegalCase_Yield1() {
		Worm worm = new Worm(this.world, 2.1, 2.1, 0.0, 1.0, "AllValidWorm");
		try {
			worm.shoot(-1);
		} catch (IllegalArgumentException e){
			fail("Invalid yield.");
		} catch (ArithmeticException e) {
			fail("This worm cannot shoot.");
		}
	}
	
	/**
	 * Function that tests an illegal case of the shoot() method of a worm.
	 * In this test the yield will be greater than 100.
	 */
	@Test
	public void test_worm_shoot_IllegalCase_Yield2() {
		Worm worm = new Worm(this.world, 2.1, 2.1, 0.0, 1.0, "AllValidWorm");
		try {
			worm.shoot(101);
		} catch (IllegalArgumentException e){
			fail("Invalid yield.");
		} catch (ArithmeticException e) {
			fail("This worm cannot shoot.");
		}
	}
	
	/**
	 * Function that tests an illegal case of the shoot() method of a worm.
	 * In this test the worm will be located on impassable terrain.
	 */
	@Test
	public void test_worm_shoot_IllegalCase_ImpassableTerrain() {
		Worm worm = new Worm(this.world, 1.0, 1.0, 0.0, 1.0, "AllValidWorm");
		try {
			worm.shoot(100);
		} catch (IllegalArgumentException e){
			fail("Invalid yield.");
		} catch (ArithmeticException e) {
			fail("This worm cannot shoot.");
		}
	}
	
	/**
	 * Function that tests an illegal case of the shoot() method of a worm.
	 * In this test the worm will not have enough Action Points left (0).
	 */
	@Test
	public void test_worm_shoot_IllegalCase_ActionPointsShort() {
		Worm worm = new Worm(this.world, 1.0, 1.0, 0.0, 1.0, "AllValidWorm");
		worm.setActionPoints(0);
		try {
			worm.shoot(100);
		} catch (IllegalArgumentException e){
			fail("Invalid yield.");
		} catch (ArithmeticException e) {
			fail("This worm cannot shoot.");
		}
	}

	/**
	 * Function that tests the getMass() method for a worm. There can be no illegal case of this method.
	 */
	@Test
	public void test_worm_getMass_LegalCase() {
		Worm worm = new Worm(this.world, 1.0, 1.0, 0.0, 1.0, "AllValidWorm");
		assertTrue(worm.getMass() == 1062.0 * ((4.0 / 3.0) * Math.PI * Math.pow(worm.getRadius(), 3)));
	}

	/**
	 * Function that tests the getName() method for a worm. There can be no illegal case of this method.
	 */
	@Test
	public void test_worm_getName_LegalCase() {
		Worm worm = new Worm(this.world, 1.0, 1.0, 0.0, 1.0, "AllValidWorm");
		assertTrue(worm.getName() == "AllValidWorm");
	}

	/**
	 * Function that tests the getMinimalRadius() method for a worm. There can be no illegal case of this method.
	 */
	@Test
	public void test_worm_getMinimalRadius_LegalCase() {
		Worm worm = new Worm(this.world, 1.0, 1.0, 0.0, 1.0, "AllValidWorm");
		assertTrue(worm.getMinimalRadius() == 0.25);
	}

	/**
	 * Function that tests the getActionPoints() method for a worm. There can be no illegal case of this method.
	 */
	@Test
	public void test_worm_getActionPoints_LegalCase() {
		Worm worm = new Worm(this.world, 1.0, 1.0, 0.0, 1.0, "AllValidWorm");
		assertTrue(worm.getActionPoints() == worm.getMaxActionPoints());
	}

	/**
	 * Function that tests the getMaxActionPoints() method for a worm. There can be no illegal case of this method.
	 */
	@Test
	public void test_worm_getMaxActionPoints_LegalCase() {
		Worm worm = new Worm(this.world, 1.0, 1.0, 0.0, 1.0, "AllValidWorm");
		assertTrue(worm.getMaxActionPoints() == (int) Math.round(worm.getMass()));
	}

	/**
	 * Function that tests a legal case of the method setRadius() for a worm.
	 */
	@Test
	public void test_worm_setRadius_LegalCase() {
		Worm worm = new Worm(this.world, 2.0, 2.0, 0.0, 1.0, "AllValidWorm");
		try {
		worm.setRadius(2.0);
		} catch (IllegalArgumentException e) {
			fail("Invalid argument.");
		}
		assertTrue(worm.getRadius() == 2.0 && 
				worm.getMass() == 1062.0 * ((4.0 / 3.0) * Math.PI * Math.pow(2.0, 3)) &&
				worm.getMaxActionPoints() == (int) Math.round(worm.getMass()) &&
				worm.getActionPoints() <= worm.getMaxActionPoints() &&
				worm.getMaxHitPoints() == (int) Math.round(worm.getMass()) &&
				worm.getActionPoints() <= worm.getMaxActionPoints());
	}
	
	/**
	 * Function that tests an illegal case of the method setRadius() of a worm.
	 */
	@Test
	public void test_worm_setRadius_IllegalCase() {
		Worm worm = new Worm(this.world, 1.0, 1.0, 0.0, 1.0, "AllValidWorm");
		try {
			worm.setRadius(.1);
		} catch (IllegalArgumentException e) {
			fail("An illegal radius has been provided.");
		}
	}

	/**
	 * Function that tests a legal case of the method rename() for a worm.
	 */
	@Test
	public void test_worm_rename_LegalCase() {
		Worm worm = new Worm(this.world, 1.0, 1.0, 0.0, 1.0, "AllValidWorm");
		try {
			worm.rename("John");
		} catch (IllegalArgumentException e) {
			fail("Invalid argument.");
		}
		assertTrue(worm.getName() == "John");
	}
	
	/**
	 * Function that tests an illegal case of the method rename() for a worm.
	 * This test will set no name whatsoever.
	 */
	@Test
	public void test_worm_rename_IllegalCase1() {
		Worm worm = new Worm(this.world, 1.0, 1.0, 0.0, 1.0, "AllValidWorm");
		try {
			worm.rename("");
		} catch (IllegalArgumentException e) {
			fail("Invalid argument.");
		}
	}
	
	/**
	 * Function that tests an illegal case of the method rename() for a worm.
	 * This test will set the name with less than 2 characters.
	 */
	@Test
	public void test_worm_rename_IllegalCase2() {
		Worm worm = new Worm(this.world, 1.0, 1.0, 0.0, 1.0, "AllValidWorm");
		try {
			worm.rename("I");
		} catch (IllegalArgumentException e) {
			fail("Invalid argument.");
		}
	}
	
	/**
	 * Function that tests an illegal case of the method rename() for a worm.
	 * This test will set the name to a name that starts with a lowercase letter.
	 */
	@Test
	public void test_worm_rename_IllegalCase3() {
		Worm worm = new Worm(this.world, 1.0, 1.0, 0.0, 1.0, "AllValidWorm");
		try {
			worm.rename("in");
		} catch (IllegalArgumentException e) {
			fail("Invalid argument.");
		}
	}
	
	/**
	 * Function that tests an illegal case of the method rename() for a worm.
	 * This test will set the name to a name that starts with a ".
	 */
	@Test
	public void test_worm_rename_IllegalCase4() {
		Worm worm = new Worm(this.world, 1.0, 1.0, 0.0, 1.0, "AllValidWorm");
		try {
			worm.rename("\"In");
		} catch (IllegalArgumentException e) {
			fail("Invalid argument.");
		}
	}
	
	/**
	 * Function that tests an illegal case of the method rename() for a worm.
	 * This test will set the name to a name that starts with a '.
	 */
	@Test
	public void test_worm_rename_IllegalCase5() {
		Worm worm = new Worm(this.world, 1.0, 1.0, 0.0, 1.0, "AllValidWorm");
		try {
			worm.rename("\'In");
		} catch (IllegalArgumentException e) {
			fail("Invalid argument.");
		}
	}
	
	/**
	 * Function that tests an illegal case of the method rename() for a worm.
	 * This test will set the name to a name that starts with a number.
	 */
	@Test
	public void test_worm_rename_IllegalCase6() {
		Worm worm = new Worm(this.world, 1.0, 1.0, 0.0, 1.0, "AllValidWorm");
		try {
			worm.rename("5In");
		} catch (IllegalArgumentException e) {
			fail("Invalid argument.");
		}
	}
	
	/**
	 * Function that tests an illegal case of the method rename() for a worm.
	 * This test will set the name to a name that starts with a space.
	 */
	@Test
	public void test_worm_rename_IllegalCase7() {
		Worm worm = new Worm(this.world, 1.0, 1.0, 0.0, 1.0, "AllValidWorm");
		try {
			worm.rename(" In");
		} catch (IllegalArgumentException e) {
			fail("Invalid argument.");
		}
	}

	/**
	 * Function that tests a legal case of the method canMove() for a worm.
	 */
	@Test
	public void test_worm_canMove_LegalCase() {
		Worm worm = new Worm(this.world, 2.1, 2.1, 0.0, 1.0, "AllValidWorm");
		assertTrue(worm.canMove());
	}
	
	/**
	 * Function that tests an illegal case of the method canMove() for a worm.
	 * For this test, the worm will always hit impassable terrain.
	 */
	@Test
	public void test_worm_canMove_IllegalCase1() {
		Worm worm = new Worm(this.world, 3.5, 3.5, 0.0, 1.0, "AllValidWorm");
		if (!worm.canMove())
			fail("The worm hits impassable terrain.");
	}
	
	/**
	 * Function that tests an illlegal case of the method canMove() for a worm.
	 * For this test, the worm has insuffiecient Action Points to move (0).
	 */
	@Test
	public void test_worm_canMove_IllegalCase2() {
		Worm worm = new Worm(this.world, 2.1, 2.1, 0.0, 1.0, "AllValidWorm");
		worm.setActionPoints(0);
		if (!worm.canMove())
			fail("The worm has insufficient Action Points.");
	}

	/**
	 * Function that tests a legal case of the method move() for a worm.
	 */
	@Test
	public void test_worm_move_LegalCase() {
		Worm worm = new Worm(this.world, 2.1, 2.1, 0.0, 1.0, "AllValidWorm");
		double oldX = worm.getX();
		double oldY = worm.getY();
		int oldActionPoints = worm.getActionPoints();
		try {
			worm.move();
		} catch (RuntimeException e) {
			fail("This worm cannot move.");
		}
		assertTrue(worm.getX() != oldX && worm.getY() != oldY && worm.getActionPoints() != oldActionPoints
				&& worm.getWorld().isAdjacent(worm.getX(), worm.getY(), worm.getRadius()));
	}
	
	/**
	 * Function that tests an illegal case of the method move() for a worm.
	 * In this method, the worm has insufficient Action Points (0).
	 */
	@Test
	public void test_worm_move_IllegalCase() {
		Worm worm = new Worm(this.world, 2.1, 2.1, 0.0, 1.0, "AllValidWorm");
		worm.setActionPoints(0);
		try {
			worm.move();
		} catch (RuntimeException e) {
			fail("This worm cannot move.");
		}
	}

	/**
	 * Function that tests a legal case of the method canTurn() for a worm.
	 * There is no illegal case of this method.
	 */
	@Test
	public void test_worm_canTurn_LegalCase() {
		Worm worm = new Worm(this.world, 2.1, 2.1, 0.0, 1.0, "AllValidWorm");
		assertTrue(worm.canTurn(Math.PI / 4));
	}
	
	/**
	 * Function that tests an illegal case of the method canTurn() for a worm.
	 * In this test, the worm will have insufficient (0) Action Points.
	 */
	@Test
	public void test_worm_canTurn_IllegalCase() {
		Worm worm = new Worm(this.world, 2.1, 2.1, 0.0, 1.0, "AllValidWorm");
		worm.setActionPoints(0);
		if (!worm.canTurn(Math.PI / 4))
			fail("This worm has insufficient Action Points.");
	}

	/**
	 * Function that tests a legal case of the method turn() for a worm.
	 * There is no illegal case for this method.
	 */
	@Test
	public void test_worm_turn_LegalCase() {
		Worm worm = new Worm(this.world, 2.1, 2.1, 0.0, 1.0, "AllValidWorm");
		double oldDirection = worm.getOrientation();
		int oldActionPoints = worm.getActionPoints();
		worm.turn(Math.PI / 4);
		assertTrue(worm.getOrientation() == (Math.PI / 4) + oldDirection && 
				worm.getActionPoints() == oldActionPoints - Math.abs(Math.round(60 / 
						(int) Math.round(2 * Math.PI / (Math.PI / 4)))));
	}

	/**
	 * Function that tests the method getJumpTime() for a worm.
	 * There is no illegal case of this method.
	 */
	@Test
	public void test_worm_getJumpTime_LegalCase() {
		Worm worm = new Worm(this.world, 2.1, 2.1, Math.PI / 4, 1.0, "AllValidWorm");
		double jumpTime = worm.getJumpTime(GUIConstants.JUMP_TIME_STEP);
		double newX = 0, newY = 0;
		double[] jumpStep = new double[2];
		double totalJumpTime = 0;
		do {
			jumpStep = worm.getJumpStep(totalJumpTime);
			newX = jumpStep[0];
			newY = jumpStep[1];
			totalJumpTime += GUIConstants.JUMP_TIME_STEP;
		} while (worm.getRadius() >= Math.sqrt(Math.pow(worm.getX() - newX, 2)
				+ Math.pow(worm.getY() - newY, 2))|| !((worm.getWorld().liesInWorld(newX, newY,
				worm.getRadius())) || (worm.getWorld().isAdjacent(newX, newY,
				worm.getRadius()))));
		assertTrue(Util.fuzzyEquals(jumpTime, totalJumpTime, .0083));
	}

	/**
	 * Function that tests the method getJumpStep() for a worm.
	 * There is no illegal case of this method.
	 */
	@Test
	public void test_worm_getJumpStep_LegalCase() {
		Worm worm = new Worm(this.world, 2.1, 2.1, 0.0, 1.0, "AllValidWorm");
		double[] resultArray = worm.getJumpStep(1);
		assertTrue(resultArray[0] == worm.getX() + ((((((5 * worm.getActionPoints()) + (worm.getMass() * worm.GRAV_CST)) / worm.getMass()) * .5) * Math.cos(worm.getOrientation())) * 1) && 
				resultArray[1] == worm.getY() + (((((((5 * worm.getActionPoints()) + (worm.getMass() * worm.GRAV_CST)) / worm.getMass()) * .5) * Math.sin(worm.getOrientation())) * 1) - (.5 * worm.GRAV_CST * Math.pow(1, 2))));
	}

	/**
	 * Function that tests a legal case of the method jump() for a worm.
	 */
	@Test
	public void test_worm_jump_LegalCase() {
		Worm worm = new Worm(this.world, 2.1, 2.1, 0.0, 1.0, "AllValidWorm");
		double oldX = worm.getX();
		double oldY = worm.getY();
		double newCoordinates[] = worm.getJumpStep(worm.getJumpTime(GUIConstants.JUMP_TIME_STEP));
		try {
			worm.jump(GUIConstants.JUMP_TIME_STEP);
		} catch (ArithmeticException e) {
			fail("This worm cannot jump.");
		}
		assertTrue(worm.getActionPoints() == 0 && oldX != newCoordinates[0] && oldY != newCoordinates[1]);
	}

	/**
	 * Function that tests a legal case of the method setActionPOints() for a worm.
	 */
	@Test
	public void test_worm_setActionPoints_LegalCase() {
		Worm worm = new Worm(this.world, 2.1, 2.1, 0.0, 1.0, "AllValidWorm");
		worm.setActionPoints(20);
		assertTrue(worm.getActionPoints() == 20);
	}
	
	/**
	 * Function that tests a legal case of the method setActionPOints() for a worm.
	 * In this test, we set the Action Points to a negative number.
	 */
	@Test
	public void test_worm_setActionPoints_IllegalCase1() {
		Worm worm = new Worm(this.world, 2.1, 2.1, 0.0, 1.0, "AllValidWorm");
		worm.setActionPoints(-1);
		if (worm.getActionPoints() != -1)
			fail("Invalid argument.");
	}
	
	/**
	 * Function that tests a legal case of the method setActionPOints() for a worm.
	 * In this test, we set the Action Points to a higher number than the maximum amount of Action Points.
	 */
	@Test
	public void test_worm_setActionPoints_IllegalCase2() {
		Worm worm = new Worm(this.world, 2.1, 2.1, 0.0, 1.0, "AllValidWorm");
		worm.setActionPoints(worm.getMaxActionPoints() + 1);
		if (worm.getActionPoints() != worm.getMaxActionPoints() + 1)
			fail("Invalid argument.");
	}
	
	/**
	 * Function that tests the method getWorld() for a worm.
	 * There are no illegal cases for this method.
	 */
	@Test
	public void test_worm_getWorld_LegalCase() {
		Worm worm = new Worm(this.world, 2.1, 2.1, 0.0, 1.0, "AllValidWorm");
		assertTrue(worm.getWorld() == this.world);
	}
	
	/**
	 * Function that tests the method getHitPoints() for a worm.
	 * There are no illegal cases for this method.
	 */
	@Test
	public void test_worm_getHitPoints_LegalCase() {
		Worm worm = new Worm(this.world, 2.1, 2.1, 0.0, 1.0, "AllValidWorm");
		assertTrue(worm.getHitPoints() == (int) Math.round(worm.getMass()));
	}
	
	/**
	 * Function that tests the method getMaxHitPoints() for a worm.
	 * There is no illegal case for this method.
	 */
	@Test
	public void test_worm_getMaxHitPoints_LegalCase() {
		Worm worm = new Worm(this.world, 2.1, 2.1, 0.0, 1.0, "AllValidWorm");
		assertTrue(worm.getMaxHitPoints() == (int) Math.round(worm.getMass()));
	}
	
	/**
	 * Function that tests the method getSelectedWeapon() for a worm.
	 * There is no illegal case for this method.
	 */
	@Test
	public void test_worm_getSelectedWeapon_LegalCase() {
		Worm worm = new Worm(this.world, 2.1, 2.1, 0.0, 1.0, "AllValidWorm");
		assertTrue(worm.getSelectedWeapon() == worm.getProjectile().getWeaponName());
	}
	
	/**
	 * Function that tests the method getTeamName() for a worm.
	 * There is no illegal case for this method.
	 */
	@Test
	public void test_worm_getTeamName_LegalCase() {
		Worm worm = new Worm(this.world, 2.1, 2.1, 0.0, 1.0, "AllValidWorm");
		assertTrue(worm.getTeamName() == null);
	}
	
	/**
	 * Function that tests the method getProjectile() for a worm.
	 * There is no illegal case for this method.
	 */
	@Test
	public void test_worm_getProjectile_LegalCase() {
		Worm worm = new Worm(this.world, 2.1, 2.1, 0.0, 1.0, "AllValidWorm");
		worm.setProjectile(null);
		assertTrue(worm.getProjectile() == null);
	}
	
	/**
	 * Function that tests a legal case of the method setHitPoints() for a worm.
	 */
	@Test
	public void test_worm_setHitPoints_LegalCase() {
		Worm worm = new Worm(this.world, 2.1, 2.1, 0.0, 1.0, "AllValidWorm");
		worm.setHitPoints(5);
		assertTrue(worm.getHitPoints() == 5);
	}
	
	/**
	 * Function that tests an illegal case of the method setHitPoints() for a worm.
	 * In this test, we set the Hit Points of the worm to a negative value.
	 */
	@Test
	public void test_worm_setHitPoints_IllegalCase1() {
		Worm worm = new Worm(this.world, 2.1, 2.1, 0.0, 1.0, "AllValidWorm");
		worm.setHitPoints(-1);
		if (worm.getHitPoints() != -1)
			fail("Invalid argument.");
	}
	
	/**
	 * Function that tests an illegal case of the method setHitPoints() for a worm.
	 * In this test, we set the Hit Points of the worm to a number greater than the maximum amount of Hit Points of the worm.
	 */
	@Test
	public void test_worm_setHitPoints_IllegalCase2() {
		Worm worm = new Worm(this.world, 2.1, 2.1, 0.0, 1.0, "AllValidWorm");
		worm.setHitPoints(worm.getHitPoints() + 1);
		if (worm.getHitPoints() != worm.getMaxHitPoints() + 1)
			fail("Invalid argument");
	}
	
	/**
	 * Function that tests the method setProjectile() for a worm.
	 * This method has no illegal cases.
	 */
	@Test
	public void test_worm_setProjectile_LegalCase() {
		Worm worm = new Worm(this.world, 2.1, 2.1, 0.0, 1.0, "AllValidWorm");
		worm.setProjectile(null);
		assertTrue(worm.getProjectile() == null);
	}
	
	/**
	 * Function that tests the method recalculateAngle() for a worm.
	 * This method has no illegal cases.
	 */
	@Test
	public void test_worm_recalculateAngle_LegalCase() {
		Worm worm = new Worm(this.world, 2.1, 2.1, 0.0, 1.0, "AllValidWorm");
		assertTrue(worm.recalculateAngle(9 * Math.PI / 4) == Math.PI / 4);
	}
	
	/**
	 * Function that tests a legal case of the method addToTeam() for a worm.
	 */
	@Test
	public void test_worm_addToTeam_LegalCase() {
		Worm worm = new Worm(this.world, 2.1, 2.1, 0.0, 1.0, "AllValidWorm");
		Team team = new Team("ValidTeamName");
		try {
			worm.addToTeam(team);
		} catch (Exception e) {
			fail("Invalid team.");
		}
		assertTrue(worm.getTeamName() == team.getTeamName());
	}
	
	/**
	 * Function that tests an illegal case of the method addToTeam() for a worm.
	 */
	@Test
	public void test_worm_addToTeam_IllegalCase() {
		Worm worm = new Worm(this.world, 2.1, 2.1, 0.0, 1.0, "AllValidWorm");
		Team team = null;
		try {
			worm.addToTeam(team);
		} catch (Exception e) {
			fail("Invalid team.");
		}
	}
	
	/**
	 * Function that tests the method selectNextWeapon() for a worm.
	 * This method has no illegal cases.
	 */
	@Test
	public void test_worm_selectNextWeapon_LegalCase() {
		Worm worm = new Worm(this.world, 2.1, 2.1, 0.0, 1.0, "AllValidWorm");
		worm.selectNextWeapon();
		assertTrue(worm.getProjectile() instanceof Bazooka);
	}
	
	/**
	 * Function that tests a legal case of the method isValidX() for a worm.
	 */
	@Test
	public void test_worm_isValidX_LegalCase() {
		assertTrue(Worm.isValidX(Worm.LOWER_BOUND_X));
	}
	
	/**
	 * Function that tests an illegal case of the method isValidX() for a worm.
	 * In this test, we make the x-coordinate less than the lower bound for x.
	 */
	@Test
	public void test_worm_isValidX_IllegalCase1() {
		if (!Worm.isValidX(Worm.LOWER_BOUND_X - 1))
			fail("Invalid x.");
	}
	
	/**
	 * Function that tests an illegal case of the method isValidX() for a worm.
	 * In this test, we make the x-coordinate greater than the upper bound for x.
	 */
	@Test
	public void test_worm_isValidX_IllegalCase2() {
		if (!Worm.isValidX(Double.POSITIVE_INFINITY))
			fail("Invalid x.");
	}
	
	/**
	 * Function that tests a legal case of the method isValidY() for a worm.
	 */
	@Test
	public void test_worm_isValidY_LegalCase() {
		assertTrue(Worm.isValidX(Worm.LOWER_BOUND_Y));
	}
	
	/**
	 * Function that tests an illegal case of the method isValidY() for a worm.
	 * In this test, we make the y-coordinate less than the lower bound for y.
	 */
	@Test
	public void test_worm_isValidY_IllegalCase1() {
		if (!Worm.isValidY(Worm.LOWER_BOUND_Y - 1))
			fail("Invalid y.");
	}
	
	/**
	 * Function that tests an illegal case of the method isValidY() for a worm.
	 * In this test, we make the y-coordinate greater than the upper bound for y.
	 */
	@Test
	public void test_worm_isValidY_IllegalCase2() {
		if (!Worm.isValidY(Double.POSITIVE_INFINITY))
			fail("Invalid y.");
	}
	
	/**
	 * Function that tests a legal case of the method isValidMass() for a worm.
	 */
	@Test
	public void test_worm_isValidMass_LegalCase() {
		assertTrue(Worm.isValidMass(.25));
	}
	
	/**
	 * Function that tests an illegal case of the method isValidMass() for a worm.
	 * In this test, we make the mass 0.
	 */
	@Test
	public void test_worm_isValidMass_IllegalCase() {
		if (!Worm.isValidMass(0))
			fail("Invalid mass.");
	}
	
	/**
	 * Function that tests a legal case of the method isValidName() for a worm.
	 */
	@Test
	public void test_worm_isValidName_LegalCase() {
		try {
			Worm.isValidName("AllValidName");
		} catch (NullPointerException e) {
			fail("Name is null.");
		}
		assertTrue(Worm.isValidName("AllValidName"));
	}
	
	/**
	 * Function that tests an illegal case of the method isValidName() for a worm.
	 * In this test, we make the name null.
	 */
	@Test
	public void test_worm_isValidName_IllegalCase1() {
		try {
			Worm.isValidName(null);
		} catch (NullPointerException e) {
			fail("Name is null.");
		}
	}
	
	/**
	 * Function that tests an illegal case of the method isValidName() for a worm.
	 * In this test, we make the name start with a lowercase letter.
	 */
	@Test
	public void test_worm_isValidName_IllegalCase2() {
		try {
			Worm.isValidName("invalidName");
		} catch (NullPointerException e) {
			fail("Name is null.");
		}
		if (!Worm.isValidName("invalidName"))
			fail("Invalid name.");
	}
	
	/**
	 * Function that tests an illegal case of the method isValidName() for a worm.
	 * In this test, we make the name start with a space.
	 */
	@Test
	public void test_worm_isValidName_IllegalCase3() {
		try {
			Worm.isValidName(" invalidName");
		} catch (NullPointerException e) {
			fail("Name is null.");
		}
		if (!Worm.isValidName(" invalidName"))
			fail("Invalid name.");
	}
	
	/**
	 * Function that tests an illegal case of the method isValidName() for a worm.
	 * In this test, we make the name start with a number.
	 */
	@Test
	public void test_worm_isValidName_IllegalCase4() {
		try {
			Worm.isValidName("1invalidName");
		} catch (NullPointerException e) {
			fail("Name is null.");
		}
		if (!Worm.isValidName("1invalidName"))
			fail("Invalid name.");
	}
	
	/**
	 * Function that tests an illegal case of the method isValidName() for a worm.
	 * In this test, we make the name less than 2 characters long.
	 */
	@Test
	public void test_worm_isValidName_IllegalCase5() {
		try {
			Worm.isValidName("I");
		} catch (NullPointerException e) {
			fail("Name is null.");
		}
		if (!Worm.isValidName("I"))
			fail("Invalid name.");
	}
	
	/**
	 * Function that tests an illegal case of the method isValidName() for a worm.
	 * In this test, we make the name start with a '.
	 */
	@Test
	public void test_worm_isValidName_IllegalCase6() {
		try {
			Worm.isValidName("\'invalidName");
		} catch (NullPointerException e) {
			fail("Name is null.");
		}
		if (!Worm.isValidName("\'invalidName"))
			fail("Invalid name.");
	}
	
	/**
	 * Function that tests an illegal case of the method isValidName() for a worm.
	 * In this test, we make the name start with a ".
	 */
	@Test
	public void test_worm_isValidName_IllegalCase7() {
		try {
			Worm.isValidName("\"invalidName");
		} catch (NullPointerException e) {
			fail("Name is null.");
		}
		if (!Worm.isValidName("\"invalidName"))
			fail("Invalid name.");
	}
	
	/**
	 * Function that tests a legal case of the method isValidRadius() for a worm.
	 */
	@Test
	public void test_worm_isValidRadius_LegalCase() {
		assertTrue(Worm.isValidRadius(Worm.MINIMAL_RADIUS));
	}
	
	/**
	 * Function that tests an illegal case of the method isValidRadius() for a worm.
	 * In this test, we make the radius that has to be tested less than the minimal radius.
	 */
	@Test
	public void test_worm_isValidRadius_IllegalCase() {
		if (!Worm.isValidRadius(0.249))
			fail("Invalid radius.");
	}
	
	/**
	 * Function that tests the method terminate() for a worm.
	 * There is no illegal case for this method.
	 */
	@Test
	public void test_worm_terminate_LegalCase() {
		Worm worm = new Worm(this.world, 2.1, 2.1, 0.0, 1.0, "AllValidWorm");
		worm.terminate();
		assertTrue(worm.isTerminated() && worm.getProjectile() == null && worm.getTeamName() == null);
	}
	
	/**
	 * Function that tests the method isTerminated() for a worm.
	 * There are no illegal cases for this method.
	 */
	@Test
	public void test_worm_isTerminated_LegalCase() {
		Worm worm = new Worm(this.world, 2.1, 2.1, 0.0, 1.0, "AllValidWorm");
		worm.terminate();
		assertTrue(worm.isTerminated());
	}
	
	/**
	 * Function that tests a legal case of the method isAlive() for a worm.
	 * This method does not have any illegal cases.
	 */
	@Test
	public void test_worm_isAlive_LegalCase1() {
		Worm worm = new Worm(this.world, 2.1, 2.1, 0.0, 1.0, "AllValidWorm");
		assertTrue(worm.isAlive());
	}
	
	/**
	 * Function that tests a legal case of the method isAlive() for a worm.
	 * This method does not have any illegal cases.
	 */
	@Test
	public void test_worm_isAlive_LegalCase2() {
		Worm worm = new Worm(this.world, 2.1, 2.1, 0.0, 1.0, "AllValidWorm");
		worm.setHitPoints(0);
		assertFalse(worm.isAlive());
	}
	
	/**
	 * Function that tests a legal case of the method isAlive() for a worm.
	 * This method does not have any illegal cases.
	 */
	@Test
	public void test_worm_isAlive_LegalCase3() {
		Worm worm = new Worm(this.world, 2.1, 2.1, 0.0, 1.0, "AllValidWorm");
		worm.terminate();
		assertFalse(worm.isAlive());
	}
}
