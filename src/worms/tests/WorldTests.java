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
 * version 0.3
 */

public class WorldTests {

	private static final double illegalRadius= 0.1;
	private static final double illegalCoordinate = -1.0;
	private static final String illegalTeamName= "lowercase name";

	/**	passable = 	- - - / / 
					- - - / /
					- - - / /		*/
	private static Random ran = new Random();
	private static boolean [][] passable = {{true,true,true, false, false},{true, true, true, false, false},{true,true,true, false, false},{true,true,true, false, false},{true,true,true, false, false}};
	

	@Test
	public void testWorld(){
		World world = new World(5.0,5.0, passable, ran);
		assertEquals(5.0,world.getWidth());
		assertEquals(5.0,world.getHeight());

	}
	@SuppressWarnings("unused")
	@Test 
	public void testConstructorWorld_IllegalWidth(){
		World world = new World(5.0,5.0, passable, ran);
		try{
			world = new World(illegalCoordinate,illegalCoordinate, passable, ran);
		}catch (IllegalArgumentException exc){
			fail("illegal bounds");
		}
	}
	
	@Test
	public void testIsValidWidth_LegalCase() {
		World world = new World(5.0,5.0, passable, ran);
		assertTrue(world.isValidWidth(world.getWidth()));
		world = new World(0.0,0.0, passable, ran);
		assertTrue(world.isValidWidth(world.getWidth()));
	}
	@SuppressWarnings("unused")
	@Test 
	public void testIsValidWidth_IllegalBoundCase(){
		
		try{
			World testWorld = new World(illegalCoordinate,illegalCoordinate*10.0, passable, ran);
		}catch (IllegalArgumentException exc){
			fail("Width is invalid, out of the world bounds");
		}
	}
	
	@Test
	public void testIsValidHeight_LegalCase() {
		World world = new World(5.0,5.0, passable, ran);
		assertTrue(world.isValidHeight(world.getHeight()));
		world= new World(0.0,0.0, passable , ran);
		assertTrue(world.isValidHeight(world.getHeight()));
	}
	@Test
	public void testIsValidHeight_IllegalCoordinateCase(){

		World testWorld = new World (illegalCoordinate*10.0, illegalCoordinate, passable, ran);
		assertFalse("Height is invalid, out of the world bounds",testWorld.isValidHeight(testWorld.getHeight()));
		assertFalse("Width is invalid, out of the world bounds",testWorld.isValidWidth(testWorld.getWidth()));

	}

	@Test
	public void testGetWidth() {
		World world = new World(5.0,5.0, passable, ran);
		assertEquals(5.0, world.getWidth());
	}
	@Test
	public void testGetHeight() {
		World world = new World(5.0,5.0, passable, ran);
		assertEquals(5.0, world.getHeight());
	}
	@Test
	public void testGetRandomSeed(){

		World world = new World(5.0,5.0, passable, ran);
		assertTrue(world.getRandomSeed() == ran);

	}
	@Test
	public void testAddWormToWorld() {
		World world = new World(5.0,5.0, passable, ran);
		world.addWormToWorld();
		assertEquals(1, world.getWorms().size());

	}
	@Test
	public void testAddFoodToWorld() {
		World world = new World(5.0,5.0, passable, ran);
		world.addFoodToWorld();
		assertEquals(1, world.getFood().size());
	}
	@Test
	public void testAddEmptyTeam_LegalNameCase() {
		World world = new World(5.0,5.0, passable, ran);
		world.addEmptyTeam("LegalTestName");
		assertTrue("name is valid for uppercase & lowercase letters", 1 == world.getTeams().size());

	}
	@Test (expected = IllegalArgumentException.class)
	public void testAddEmptyTeam_IllegalNameCase() 
			throws Exception{
		World world = new World(5.0,5.0, passable, ran);
		world.addEmptyTeam(illegalTeamName);
		assertFalse("The name is invalid",world.getTeams().size()==1);
	}
	@Test (expected = IllegalArgumentException.class)
	public void testAddEmptyTeam_IllegalAmountCharNameCase() 
			throws Exception{
		World world = new World(5.0,5.0, passable, ran);
		world.addEmptyTeam("H");
		assertFalse("The name is invalid, insufficient chars",world.getTeams().size()==1);
	}
	@Test (expected = IllegalArgumentException.class)
	public void testAddEmptyTeam_IllegalTeamAmountCase() 
			throws Exception {
		World world = new World(5.0,5.0, passable, ran);

		for (int i = 0; i <= 11; i ++){

			world.addEmptyTeam("LegalTestname");
		}
		assertFalse("The amount of teams are invalid",world.getTeams().size()==11);
	}

	@Test
	public void testGetActiveProjectile() {
		
		World world = new World(5.0,5.0, passable, ran);
		world.createWorm(1.0, 1.0, 1.0, 1.0, "Test");
		assertTrue(world.getCurrentWorm().getProjectile().isActive());
		world.getCurrentWorm().terminate();
		assertFalse(world.getCurrentWorm().getProjectile().isActive());
	}
	
	@Test
	public void testRemoveFoodFromWorld() {
		World world = new World(5.0,5.0, passable, ran);
		world.addFoodToWorld();
		world.addFoodToWorld();
		world.createFood(1.1, 1.1);
		world.removeFoodFromWorld(((ArrayList<Food>)world.getFood()).get(0));
		assertEquals(1, world.getFood().size());

	}
	@Test
	public void testRemoveProjectileFromWorld(){
		World world = new World(5.0,5.0, passable, ran);
		world.createWorm(1, 1, 1, 1, "Test");
		world.addWormToWorld();
		world.removeProjectileFromWorld(world.getCurrentWorm().getProjectile());
		assertEquals(true,world.getCurrentWorm().getProjectile().isTerminated());
		assertEquals(true,world.getCurrentWorm().isTerminated());
	}
	@Test
	public void testRemoveWormFromWorld() {
		World world = new World(5.0,5.0, passable, ran);
		world.addWormToWorld();
		world.addWormToWorld();
		world.startGame();
		world.removeWormFromWorld(world.getCurrentWorm());
		assertEquals(1, world.getWorms().size());
	}
	@Test
	public void testGetCurrentWorm() {
		World world = new World(5.0,5.0, passable, ran);
		world.addWormToWorld();
		assertSame(((ArrayList<Worm>)world.getWorms()).get(0), world.getCurrentWorm());
	}
	@Test
	public void testGetFood() {
		World world = new World(5.0,5.0, passable, ran);
		world.createFood(1.0, 1.0);
		assertEquals(1,world.getFood().size());
	}
	@Test
	public void testGetWinner() {
		World world = new World(5.0,5.0, passable, ran);
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
		World world = new World(5.0,5.0, passable, ran);
		world.addEmptyTeam("Test");
		assertEquals(1, world.getTeams().size());
	}
	@Test
	public void testGetObjectsInWorld() {
		World world = new World(5.0,5.0, passable, ran);
		world.addFoodToWorld();
		world.addWormToWorld();
		world.addEmptyTeam(" ");
		assertEquals(3, (((ArrayList<Object>)world.getObjectsInWorld()).size()));
	}
	@Test
	public void testCalculateLocationStatus_LegalCases() {
		World world = new World(5.0,5.0, passable, ran);
		assertTrue(world.isImpassable(1.0, 5.0, 0.25));
		assertTrue(world.isAdjacent(1.0, 3.0, 0.95));
		assertTrue(world.isPassable(1.0, 1.0, 0.25));
	}
	@Test
	public void testCalculateLocationStatus_IllegalCases(){
		World world = new World(5.0,5.0, passable, ran);
		assertFalse(world.isImpassable(1.0, 1.0, 0.25));
		assertFalse(world.isAdjacent(1.0, 1.0, 0.25));
		assertFalse(world.isAdjacent(3.0, 1.0, 0.95));
		assertFalse(world.isAdjacent(1.0, 5.0, 0.25));
		assertFalse(world.isPassable(1.0, 5.0, 0.25));
	}
	@Test
	public void testStartGame_LegalCase (){
		World world = new World(5.0,5.0, passable, ran);
		world.createWorm(1.0, 1.0, 1, 1.0, "Test1");
		world.createWorm(1.0, 1.0, 1, 1.0, "Test1");
		world.startGame();
		assertSame(((ArrayList<Worm>)world.getWorms()).get(0), world.getCurrentWorm());
	}

	@Test
	public void testStartNextTurn() {
		World world = new World(5.0,5.0, passable, ran);
		world.createWorm(1.0, 1.0, 1.0, 1.0, "Test1");
		world.createWorm(1.0, 1.0, 1.0, 1.0, "Test2");
		world.startGame();
		world.startNextTurn();
		assertEquals(world.getCurrentWorm().getName(), "Test2");
	}

	@Test
	public void testGetTeams() {
		World world = new World(5.0,5.0, passable, ran);
		world.addEmptyTeam("Hey");
		world.addEmptyTeam("Hela");
		world.addEmptyTeam(" ");

		assertEquals(3, world.getTeams().size(), 0.1);
	}

	@Test
	public void testCreateFood_LegalTerrainCase() {
		World world = new World(5.0,5.0, passable, ran);
		world.createFood(4.8, 1.0);
		assertEquals(1, world.getFood().size());
	}
	@Test 
	public void testCreateFood_IllegalTerrainCase() 
			throws Exception{
		World world = new World(5.0,5.0, passable, ran);
		world.createFood(1.0, 5.0);
		assertFalse("Invalid Terrain",world.getFood().size()==1); 
	}
	@Test 
	public void testCreateFood_IllegalBoundCase()
			throws Exception{
		World world = new World(5.0,5.0, passable, ran);
		world.createFood(illegalCoordinate, illegalCoordinate);
		assertFalse("Invalid Coordinates",world.getFood().size()==1);
	}
	@Test
	public void testCreateWorm_LegalCase() {
		World world = new World(5.0,5.0, passable, ran);
		world.createWorm(1.0, 1.0, 0.0, 0.25, "Test");
		assertEquals(world.getWorms().size(),1);
		world.createWorm(0.0, 0.0, 0.0, 0.25, "Test");
		assertEquals(world.getWorms().size(),2);
	}
	@Test 
	public void testCreateWorm_IllegalTerrainCase()	{
		World world = new World(5.0,5.0, passable, ran);
		world.createWorm(1.0, 5.0, 0.0, 0.25, "Test");
		assertFalse("Invalid coordinates",world.getWorms().size()== 1);
	}
	@Test
	public void testCreateWorm_OutOfWorldBoundsCase(){
		World world = new World(5.0,5.0, passable, ran);
		try{
			world.createWorm(illegalCoordinate, illegalCoordinate, 0.0, 0.25, "Test");
		}catch (IllegalArgumentException exc){
			fail("Invalid Coordinates, out of world bounds");
		}
	}
	@Test 
	public void testCreateWorm_IllegalNameCase()
			throws Exception{
		World world = new World(5.0,5.0, passable, ran);
		try{
		world.createWorm(0.0, 0.0, 0.0, 0.25, illegalTeamName);
		}catch (IllegalArgumentException exc){
			fail("Invalid name");
		}
	}
	@Test
	public void testCreateWorm_IllegalRadiusCase(){
		World world = new World(5.0,5.0, passable, ran);
		try{
		world.createWorm(0.0, 0.0, 0.0, illegalRadius, "Test");
		}catch(IllegalArgumentException exc){
			fail("Invalid Radius");
		}
	}
	@Test
	public void testLiesInWorld_LegalCase() {
		
		World world = new World(5.0,5.0, passable, ran);
		assertTrue(world.liesInWorld(1.0, 2.0, 0.25));
		assertTrue(world.liesInWorld(1.0, 2.0, 1));
	}
	@Test
	public void testLiesInWorld_IllegalOutOfBoundCase(){
		World world = new World(5.0,5.0, passable, ran);
		try{
			world.liesInWorld(illegalCoordinate, illegalCoordinate, 0.25);
		}catch(IllegalArgumentException exc){
			fail("Out of world Bounds");
		}
	}
	@Test 
	public void testLiesInWorld_IllegalRadiusCase(){
		World world = new World(5.0,5.0, passable, ran);
		try{
			world.liesInWorld(1.0, 1.0, illegalRadius);
		}catch (IllegalArgumentException exc){
			fail("Invalid Radius");
		}
	}
	@Test
	public void testIsGameFinished(){
		
		World world = new World(5.0,5.0, passable, ran);
		assertFalse(world.isGameFinished());
		world.createWorm(1.0, 1.0, 1.0, 1.0, "Test1");
		world.createWorm(1.0, 1.0, 1.0, 1.0, "Test2");
		world.startGame();
		((ArrayList<Worm>)world.getWorms()).get(0).setHitPoints(0);
		assertTrue(world.isGameFinished());
	}


}
