package worms.model;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;

import org.junit.Test;


/**
 * A Junit testsuite to test all public methods of the class world 
 * 
 * The methods are tested for legal and illegal cases
 * 
 * @author Mathijs Nelissen & Pieterjan Vingerhoets 
 * version 0.2
 */

public class WorldTests {

	private static final double illegalRadius= 0.1;
	private static final double illegalCoordinate = -1.0;
	private static final String illegalTeamName= "lowercase name";


	private static Random ron = new Random();
	private static boolean [][] passable = {{true, true, true, false, false},
						{true, true, true, false, false},
						{true, true, true, false, false},
						{true, true, true, false, false},
						{false, false, false, false, false}};
	private static World world = new World(5.0,5.0, passable, ron);

	@Test
	public void testWorld(){

		assertEquals(5.0,world.getWidth());
		assertEquals(5.0,world.getHeight());

	}
	@Test 
	public void testConstructorWorld_IllegalWidth(){
		try{
			world = new World(illegalCoordinate,illegalCoordinate, passable, ron);
		}catch (IllegalArgumentException exc){
			fail("illegal bounds");
		}
	}
	@Test 
	public void testConstructorWorld_IllegalArgumentCase(){
		try{
			world = new World("r",5.0, passable, ron);
		}catch (IllegalArgumentException exc){
			fail("Invalid argument, not a number");
		}
	}
	@Test
	public void testIsValidWidth_LegalCase() {

		assertTrue(world.isValidWidth(world.getWidth()));
		world = new World(0.0,0.0, passable, ron);
		assertTrue(world.isValidWidth(world.getWidth()));
	}
	@Test 
	public void testIsValidWidth_IllegalBoundCase(){

		try{
			World testWorld = new World(illegalCoordinate,illegalCoordinate*10.0, passable, ron);
		}catch (IllegalArgumentException exc){
			fail("Width is invalid, out of the world bounds");
		}
	}
	@Test
	public void testIsValidWidth_IllegalNANCase(){

		World testWorld = new World("r",1.0, passable, ron);
		assertFalse("Width is not a number",testWorld.isValidWidth(testWorld.getWidth()));
	}
	@Test
	public void testIsValidHeight_LegalCase() {

		assertTrue(world.isValidHeight(world.getHeight()));
		world= new World(0.0,0.0, passable , ron);
		assertTrue(world.isValidHeight(world.getHeight()));
	}
	@Test
	public void testIsValidHeight_IllegalCoordinateCase(){

		World testWorld = new World (illegalCoordinate*10.0, illegalCoordinate, passable, ron);
		assertFalse("Height is invalid, out of the world bounds",testWorld.isValidHeight(testWorld.getHeight()));
		assertFalse("Width is invalid, out of the world bounds",testWorld.isValidWidth(testWorld.getWidth()));

	}
	@Test 
	public void testIsValidHeight_IllegalNANCase(){
		try{
			World testWorld = new World(1.0,"r",  passable, ron);
		}catch (IllegalArgumentException exc){
			fail("Invalid argument, not a number");
		}
	}
	@Test
	public void testGetWidth() {

		assertEquals(5.0, world.getWidth());
	}
	@Test
	public void testGetHeight() {

		assertEquals(5.0, world.getHeight());
	}
	@Test
	public void testGetRandomSeed(){


		assertTrue(world.getRandomSeed() == ron);

	}
	@Test
	public void testAddWormToWorld() {

		world.addWormToWorld();
		assertEquals(1, world.getWorms().size());

	}
	@Test
	public void testAddFoodToWorld() {

		world.addFoodToWorld();
		assertEquals(1, world.getFood().size());
	}
	@Test
	public void testAddEmptyTeam_LegalNameCase() {
		// 
		world.addEmptyTeam("This Testname is 4 shame' but legal");
		assertTrue("name is valid for uppercase letters, punctuation and numbers", 1 == world.getTeams().size());

	}
	@Test (expected = IllegalArgumentException.class)
	public void testAddEmptyTeam_IllegalNameCase() 
			throws Exception{
		world.addEmptyTeam(illegalTeamName);
		assertFalse("The name is invalid",world.getTeams().size()==1);
	}
	@Test (expected = IllegalArgumentException.class)
	public void testAddEmptyTeam_IllegalAmountCharNameCase() 
			throws Exception{
		world.addEmptyTeam("H");
		assertFalse("The name is invalid, insufficient chars",world.getTeams().size()==1);
	}
	@Test (expected = IllegalArgumentException.class)
	public void testAddEmptyTeam_IllegalTeamAmountCase() 
			throws Exception {

		for (int i = 0; i <= 11; i ++){

			world.addEmptyTeam("LegalTestname");
		}
		assertFalse("The amount of teams are invalid",world.getTeams().size()==11);
	}

	@Test
	public void testGetActiveProjectile() {
		world.createWorm(1.0, 1.0, 1.0, 1.0, "Test");
		assertEquals(world.getCurrentWorm().getProjectile(), worm.getProjectile());
	}
	@Test
	public void testRemoveFoodFromWorld() {

		world.addFoodToWorld();
		world.addFoodToWorld();
		world.createFood(1.1, 1.1);
		world.removeFoodFromWorld(world.getFood().get(0));
		assertEquals(1, world.getFood().size());

	}
	@Test
	public void testRemoveProjectileFromWorld(){
		world.createWorm(1, 1, 1, 1, "Test");
		world.addWormToWorld();
		world.removeProjectileFromWorld(world.getWorms().get(0).getProjectile().terminate());
		assertEquals(true,world.getWorms().get(0).getProjectile().isTerminated());
		assertEquals(true,world.getWorms().get(0).isTerminated());
	}
	@Test
	public void testRemoveWormFromWorld() {

		world.addWormToWorld();
		world.addWormToWorld();
		world.startGame();
		world.removeWormFromWorld(world.getCurrentWorm());
		assertEquals(1, world.getWorms().size());
	}
	@Test
	public void testGetCurrentWorm() {
		world.addWormToWorld();
		assertSame(world.getWorms().get(0), world.getCurrentWorm());
	}
	@Test
	public void testGetFood() {
		world.createFood(1.0, 1.0);
		assertEquals(1,world.getFood().size());
	}
	@Test
	public void testGetWinner() {

		world.createWorm(1.0, 1.0, 1.0, 1.0, "Test");
		world.addWormToWorld();
		world.addWormToWorld();
		for (Worm worm : world.getWorms()){
			if (worm !=world.getCurrentWorm()){
				worm.setHitPoints(0);
			}
		}
		assertSame(world.getCurrentWorm(), world.getWinner());
	}
	@Test
	public void testGetWorms() {
		world.addEmptyTeam("Test");
		assertEquals(1, world.getTeams().size());
	}
	@Test
	public void testGetObjectsInWorld() {

		world.addFoodToWorld();
		world.addWormToWorld();
		world.addEmptyTeam(" ");
		assertEquals(3, world.getObjectsInWorld().size());
	}
	@Test
	public void testCalculateLocationStatus_LegalCases() {

		assertTrue(world.isImpassable(1.0, 5.0, 0.25));
		assertTrue(world.isAdjacent(1.0, 3.0, 0.95));
		assertTrue(world.isPassable(1.0, 1.0, 0.25));
	}
	@Test
	public void testCalculateLocationStatus_IllegalCases(){

		assertFalse(world.isImpassable(1.0, 1.0, 0.25));
		assertFalse(world.isAdjacent(1.0, 1.0, 0.25));
		assertFalse(world.isAdjacent(10, 5.0, 0.25));
		assertFalse(world.isPassable(1.0, 5.0, 0.25));
	}
	@Test
	public void testStartGame_LegalCase (){
		world.createWorm(1.0, 1.0, 1, 1.0, "Test1");
		world.createWorm(1.0, 1.0, 1, 1.0, "Test1");
		world.startGame();
		assertsame(world.getWorms().get(0), world.getCurrentWorm());
		assertTrue(world.isStarted());
	}

	@Test
	public void testStartNextTurn() {
		world.createWorm(1.0, 1.0, 1.0, 1.0, "Test1");
		world.createWorm(1.0, 1.0, 1.0, 1.0, "Test2");
		world.startGame();
		world.startNextTurn();
		assertEquals(world.getCurrentWorm().getName(), "Test2");
	}

	@Test
	public void testGetTeams() {
		world.addEmptyTeam("Hey");
		world.addEmptyTeam("Hela");
		world.addEmptyTeam(" ");

		assertEquals(3, world.getTeams().size());
	}

	@Test
	public void testCreateFood_LegalTerrainCase() {

		world.createFood(1.0, 1.0);
		assertEquals(1, world.getFood().size());

	}
	@Test 
	public void testCreateFood_IllegalTerrainCase() 
			throws Exception{
		world.createFood(1.0, 5.0);
		assertFalse("Invalid Terrain",world.getFood().size()==1); 
	}
	@Test 
	public void testCreateFood_IllegalBoundCase()
			throws Exception{

		world.createFood(illegalCoordinate, illegalCoordinate);
		assertFalse("Invalid Coordinates",world.getFood().size()==1);
	}
	@Test
	public void testCreateWorm_LegalCase() {
		world.createWorm(1.0, 1.0, 0.0, 0.25, "Test");
		assertEquals(world.getWorms().size(),1);
		world.createWorm(0.0, 0.0, 0.0, 0.25, "Test");
		assertEquals(world.getWorms().size(),2);
	}
	@Test 
	public void testCreateWorm_IllegalTerrainCase()	{

		world.createWorm(1.0, 5.0, 0.0, 0.25, "Test");
		assertFalse("Invalid coordinates",world.getWorms().size()== 1);
	}
	@Test
	public void testCreateWorm_OutOfWorldBoundsCase(){
		try{
			world.createWorm(illegalCoordinate, illegalCoordinate, 0.0, 0.25, "Test");
		}catch (IllegalArgumentException exc){
			fail("Invalid Coordinates, out of world bounds");
		}
	}
	@Test 
	public void testCreateWorm_IllegalNameCase()
			throws Exception{
		try{
		world.createWorm(0.0, 0.0, 0.0, 0.25, illegalTeamName);
		}catch (IllegalArgumentException exc){
			fail("Invalid name");
		}
	}
	@Test
	public void testCreateWorm_IllegalRadiusCase(){
		try{
		world.createWorm(0.0, 0.0, 0.0, illegalRadius, "Test");
		}catch(IllegalArgumentException exc){
			fail("Invalid Radius");
		}
	}
	@Test
	public void testLiesInWorld_LegalCase() {

		assertTrue(world.liesInWorld(1.0, 2.0, 0.25));
		assertTrue(world.liesInWorld(1.0, 2.0, 1));
	}
	@Test
	public void testLiesInWorld_IllegalOutOfBoundCase(){
		try{
			world.liesInWorld(illegalCoordinate, illegalCoordinate, 0.25);
		}catch(IllegalArgumentException exc){
			fail("Out of world Bounds");
		}
	}
	@Test 
	public void testLiesInWorld_IllegalRadiusCase(){
		try{
			world.liesInWorld(1.0, 1.0, illegalRadius);
		}catch (IllegalArgumentException exc){
			fail("Invalid Radius");
		}
	}
	@Test
	public void testIsGameFinished(){
		assertFalse(world.isGameFinished);
		world.createWorm(1.0, 1.0, 1.0, 1.0, "Test1");
		world.createWorm(1.0, 1.0, 1.0, 1.0, "Test2");
		world.startGame();
		world.getWorms().get(0).setHitPoints(0);
		assertTrue(world.isGameFinished);
	}


}
