import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;

import org.junit.Test;

import worms.model.World;
import worms.model.Worm;
/**
 * A Junit testsuite to test all public methods of the class world 
 * 
 * The methods are tested for legal and illegal cases
 * 
 * @author Mathijs Nelissen & Pieterjan Vingerhoets 
 * version 0.2
 */

public class WorldTests {

	private static final double EPS = Util.DEFAULT_EPSILON;
	private static final double illegalRadius= 0.1;
	private static final double illegalCoordinate = -1.0;
	private static final String illegalTeamName= "testName";
	@Before
	public static void setup(){
		/**	passable = 	- - - / / 
					- - - / /
					- - - / /		*/
		boolean [][] passable = {[{true,true,true, false, false},{true, true, true, false, false},{true,true,true, false, false}};
		World world = new World(5,5, passable, Random random);
	}
	@After
	public void Breakdown(){
		World world = new World (5,5);
	}

	@Test
	public void testWorld(){
		
		assertEquals(5,world.getWidth());
		assertEquals(5,world.getHeight());
	}
	@Test
	public void testIsValidWidth_LegalCase() {
		
		assertTrue(world.isValidWidth(world.getWidth()));
		world= new World(0, 0);

	}
	@Test 
	public void testIsValidWidth_IllegalCase()
		throws Exception{
		
		World testWorld = new World(illegalCoordinate,illegalCoordinate-9);
		assertFalse(testWorld.isValidWidth(testWorld.getWidth()));
	}
	@Test
	public void testIsValidHeight_LegalCase() {
		
		assertTrue(world.isValidHeight(world.getHeight));
		world= new World(0,0);
		assertTrue(world.isValidHeight(world.getheight));
	}
	@Test
	public void testIsValidHeight_IllegalCase()
		throws Exception{
		
		World testWorld = new World (illegalCoordinate, illegalCoordinate-9;
		assertFalse(testWorld.isValidWidth(testWorld.getHeight()))
	}
	@Test
	public void testGetWidth() {
		
		assertEquals(5, world.getWidth());
	}
	@Test
	public void testGetHeight() {
		
		assertEquals(5, world.getHeight());
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
		
		assertTrue(world.addEmtyTeam("This Testname is 4 shame' but legal"));
		assertTrue(world.getTeams().size()==1)

	}
	@Test 
	public void testAddEmtpyTeam_IllegalNameCase()
		throws Exception{
		world.addEmptyTeam(illegalTeamName);
		assertFalse(world.getTeams().size()==1);
	}
	@Test 
	public void testAddEmptyTeam_IllegalAmountCharNameCase()
		throws Exception{
		world.addEmtyTeam("H");
		assertFalse(world.getTeams().size()==1);
	}
	@Test 
	public void testAddEmptyTeam_IllegalTeamAmountCase()
			throws Exception {
		
		for (int i = 0; i <= 11; i ++){
			
			world.addEmptyTeam("LegalTestname");
		}
		assertFalse(world.getTeams().size()==11);
	}

	@Test
	public void testGetActiveProjectile() {

	}
	@Test
	public void testRemoveFoodFromWorld() {
		
		world.addFoodToWorld();
		world.removeFoodFromWorld(world.addFoodToWorld());
		assertEquals(1, world.getFood().size());

	}
	@Test
	public void testRemoveWormFromWorld() {
		
		world.addWormToWorld;
		world.removeWormFromWorld(world.addWormToWorld());
		assertEquals(1, world.getWorms().size());
	}
	@Test
	public void testGetCurrentWorm() {
		World.addWormToWorld();
		assertEquals(world.getWorms()[0], world.getCurrentWorm());
	}
	@Test
	public void testGetFood() {
		world.addFoodToWorld(1, 1);
		assertEquals(1,world.getFood().size());
	}
	@Test
	public void testGetWinner() {
		
		Worm won = world.createWorm(1, 1, 0.25);
		world.removeWormFromWorld(world.addWormToWorld(1, 0, 0.25));
		world.removeWormFromWorld(world.addWormToWorld(0, 1, 0.25));
		
		assertEquals(won, world.getWinner());
	}
	@Test
	public void testGetWorms() {
		world.addEmptyTeam("Test");
		assertEquals(1, world.getTeams());
	}
	@Test
	public void testGetObjectsInWorld() {
		
		world.addFoodToWorld();
		world.addWormToWorld();
		world.addEmptyTeam();
		assertTrue(world.getObjectsInWorld().size() == 3);
	}
	@Test
	public void testCalculateLocationStatus_LegalCases() {
		
		world = new World(5,5, passable, Random random);
		assertEquals(world.isImpassable, world.calculateLocationStatus(1, 5, 0.25));
		assertEquals(world.isAdjacent, world.calculateLocationStatus(1, 3, 0.95));
		assertEquals(world.isPassable, world.calculateLocationStatus(1, 1, 0.25));
	}
	@Test
	public void testStartNextTurn() {
		
	}

	@Test
	public void testGetTeams() {
		world.addEmptyTeam("Hey");
		world.addEmptyTeam("Hela");
		assertEquals(2, world.getTeams().size());
	}

	@Test
	public void testCreateFood_LegalTerrainCase() {

		world.createFood(1, 1);
		assertEquals(1, world.getFood().size());

	}
	@Test 
	public void testCreatFood_IllegalTerrainCase() 
			throws Exception{
		world.createFood(1, 5);
		assertFalse(world.getFood().size()==1); 
	}
	@Test 
	public void testCreateFood_IllegalBoundCase()
			throws Exception
			{
		world.createFood(illegalCoordinate, illegalCoordinate);
		assertFalse(world.getfood().size()==1)
			}
	@Test
	public void testCreateWorm_LegalCase() {
		world.createWorm(1, 1, 0, 0.25, "Test");
		assertEquals(world.getWorms().size() == 1);
		world.createWorm(0, 0, 0, 0.25, "Test");
		assertEquals(world.getWorms().size() == 2)
	}
	@Test
	public void testCreateWorm_IllegalTerrainCase()
			throws Exception
			{
		world.createWorm(1, 5, 0, 0.25, "Test");
		assertFalse(world.getWorms().size()== 1);
			}
	@Test
	public void testCreateWorm_OutOfWorldBoundsCase()
			throws Exception
			{
		world.createWorm(illegalCoordinate, illegalCoordinate, 0, 0.25, "Test");
		assertFalse(world.getWorms().size()== 1);
			}
	@Test
	public void testCreateWorm_IllegalNameCase()
			throws Exception{
		
		world.createWorm(0, 0, 0, 0.25, illegalTeamName);
		assertFalse(1 == world.getWorms().size());
			}
	@Test
	public void testCreateWorm_IllegalRadiusCase()
			throws Exception{
		
		world.createWorm(0, 0, 0, illegalRadius, "Test");
		assertFalse(1 == world.getWorms().size());

	}
	@Test
	public void testLiesInWorld_LegalCase() {
		
		assertTrue(world.liesInWorld(1, 2, 0.25));
		assertTrue(world.liesInWorld(0, 2, 0.25));
	}
	@Test
	public void testLiesInWorld_IllegalOutOfBoundCase()
			throws Exception{
		
		assertFalse(world.liesInWorld(illegalCoordinate, illegalCoordinate, 0.25));
	}
	@Test
	public void testLiesInWorld_IllegalTerrainCase()
			throws Exception{
		
		assertFalse(world.liesInWorld(1, 4, 0.25));
	}
	@Test 
	public void testLiesInWorld_IllegalRadiusCase()
			throws Exception{
				
		assertFalse(world.liesinWorld(1, 1, illegalRadius));
	}


}
