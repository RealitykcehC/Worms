import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;

import org.junit.Test;

import worms.model.World;
import worms.model.Worm;
import worms.model.*;

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
	private static final String illegalTeamName= "lowercase name";

	/**
	 * Init world with legal arguments to commence the tests
	 */
	@Before
	public static void setup(){
		/**	passable = 	- - - / / 
					- - - / /
					- - - / /
					- - - / /
					- - - / /	*/
		boolean [][] passable = {{true,true,true, false, false},
					{true,true,true, false, false},
					{true,true,true, false, false},
					{true,true,true, false, false},
					{true,true,true, false, false}};
					
		World world = new World(5.0,5.0, passable, Random random);

	}
	@Test
	public void testWorld(){

		assertEquals(5.0,world.getWidth());
		assertEquals(5.0,world.getHeight());

	}
	@Test
	public void testIsValidWidth_LegalCase() {

		assertTrue(world.isValidWidth(world.getWidth()));
		world= new World(0.0, 0.0);
		assertTrue(world.isValidWidth(world.getWidth()));
	}
	@Test 
	public void testIsValidWidth_IllegalBoundCase(){
			

		World testWorld = new World(illegalCoordinate,illegalCoordinate*10.0);
		assertFalse(["Width is invalid, out of the world bounds"],testWorld.isValidWidth(testWorld.getWidth()));
		assertFalse(["Height is invalid, out of the world bounds"],testWorld.isValidHeight(testWorld.getHeight()))

	}
	@Test
	public void testIsValidWidth_IllegalArgumentCase(){

		World testWorld = new World("r",1.0);
		assertFalse(["Width is not a number"],testWorld.isValidWidth(testWorld.getWidth()));
	}
	@Test
	public void testIsValidHeight_LegalCase() {

		assertTrue(world.isValidHeight(world.getHeight));
		world= new World(0.0,0.0);
		assertTrue(world.isValidHeight(world.getHeight));
	}
	@Test
	public void testIsValidHeight_IllegalCase(){

		World testWorld = new World (illegalCoordinate*10.0, illegalCoordinate);
		assertFalse(["Height is invalid, out of the world bounds"],testWorld.isValidHeight(testWorld.getHeight()))
		assertFalse(["Width is invalid, out of the world bounds"],testWorld.isValidWidth(testWorld.getWidth());

	}
	@Test 
	public void testIsValidHeight_IllegalCase(){

		World testWorld = new World(1.0,"e");
		assertFalse(["Height is not a number"],testWorld.isValidHeight(testWorld.getHeight()));
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
		Random ran = new Random ();
		world = new World(5, 5, passable, ran);
		assertTrue(world.getRandomSeed == ran);

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
		assertTrue(world.addEmtyTeam("This Testname is 4 shame' but legal"));
		assertTrue(["name is valid for uppercase letters, punctuation and numbers"], 1 == world.getTeams().size());

	}
	@Test (expected = IllegalArgumentException.class)
	public void testAddEmptyTeam_IllegalNameCase() 
			throws Exception{
		world.addEmptyTeam(illegalTeamName);
		assertFalse(["The name is invalid"],world.getTeams().size()==1);
	}
	@Test (expected = IllegalArgumentException.class)
	public void testAddEmptyTeam_IllegalAmountCharNameCase() 
			throws Exception{
		world.addEmtyTeam("H");
		assertFalse(["The name is invalid, insufficient chars"],world.getTeams().size()==1);
	}
	@Test (expected = IllegalAmountException.class)
	public void testAddEmptyTeam_IllegalTeamAmountCase() 
			throws Exception {

		for (int i = 0; i <= 11; i ++){

			world.addEmptyTeam("LegalTestname");
		}
		assertFalse(["The amount of teams are invalid"],world.getTeams().size()==11);
	}

	@Test
	public void testGetActiveProjectile() {
		world.createWorm(1.0, 1.0, 1.0, 1.0, "Test");
		assertEquals(world.getCurrentWorm().getProjectile, worm.getProjectile());
	}
	@Test
	public void testRemoveFoodFromWorld() {

		world.addFoodToWorld();
		world.addFoodToWorld();
		world.removeFoodFromWorld(world.getWorms().get(0));
		assertEquals(1, world.getFood().size());

	}
	@Test
	public void testRemoveProjectileFromWorld(){
		world.createWorm(1, 1, 1, 1, "Test");
		world.addWormToWorld();
		world.removeProjectileFromWorld(world.getWorms().get(0).getProjectile().terminate());
		assertEquals(true,world.getWorms().get(0).getProjectile(),isTerminated());
		assertEquals(true,world.getWorms().get(0).isTerminated());
	}
	@Test
	public void testRemoveWormFromWorld() {

		world.addWormToWorld();
		world.removeWormFromWorld(world.addWormToWorld());
		assertEquals(1, world.getWorms().size());
	}
	@Test
	public void testGetCurrentWorm() {
		World.addWormToWorld();
		assertSame(world.getWorms()[0], world.getCurrentWorm());
	}
	@Test
	public void testGetFood() {
		world.addFoodToWorld(1.0, 1.0);
		assertEquals(1,world.getFood().size());
	}
	@Test
	public void testGetWinner() {

		Worm won = world.createWorm(1.0, 1.0, 0.25);
		Worm didntwin= world.addWormToWorld(1.0, 00, 0.25);
		didntwin.setMaxHitPoints(0);
		world.removeWormFromWorld(world.addWormToWorld(1.0, 0.0, 0.25));
		world.removeWormFromWorld(world.addWormToWorld(0.0, 1.0, 0.25));

		assertSame(won, world.getWinner());
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
		world.addEmptyTeam();
		assertEquals(3, world.getObjectsInWorld().size());
	}
	@Test
	public void testCalculateLocationStatus_LegalCases() {

		assertEquals(world.isImpassable(1.0, 5.0, 0.25), world.calculateLocationStatus(1.0, 5.0, 0.25));
		assertEquals(world.isAdjacent(1.0, 3.0, 0.95), world.calculateLocationStatus(1.0, 3.0, 0.95));
		assertEquals(world.isPassable(1.0, 1.0, 0.25), world.calculateLocationStatus(1.0, 1.0, 0.25));
	}
	@Test
	public void testCalculateLocationStatus_IllegalCases(){

		assertFalse(world.isImpassable(1.0, 1.0, 0.25) == world.calculateLocationStatus(1.0, 1.0, 0.25);
		assertFalse(world.isAdjacent(1.0, 1.0, 0.25) == world.calculateLocationStatus(1.0, 1.0, 0.25);
		assertFalse(world.isAdjacent(10, 5.0, 0.25) == world.calculateLocationStatus(10, 5.0, 0.25);
		assertFalse(world.isPassable(1.0, 5.0, 0.25) == world.calculateLocationStatus(1.0, 5.0, 0.25);
	}
	@Test
	public void testStartGame_LegalCase (){
		world.createWorm(1.0, 1.0, 1, 1.0, "Test1");
		world.createWorm(1.0, 1.0, 1, 1.0, "Test1");
		world.startGame();
		assertsame(collectionOfWorms().get(0), world.getCurrentWorm());
		assertEquals(true, world.isStarted);
	}

	@Test
	public void testStartNextTurn() {
		world.createWorm(1.0, 1.0, 1.0, 1.0, "Test1");
		world.createWorm(1.0, 1.0, 1.0, 1.0, "Test2");
		world.startGame();
		world.startNextTurn();
		assertEquals (world.getCurrentWorm().getName(), "Test2")
	}

	@Test
	public void testGetTeams() {
		world.addEmptyTeam("Hey");
		world.addEmptyTeam("Hela");
		world.addEmptyTeam();

		assertEquals(3, world.getTeams().size());
	}

	@Test
	public void testCreateFood_LegalTerrainCase() {

		world.createFood(1.0, 1.0);
		assertEquals(1, world.getFood().size());

	}
	@Test (expected = RuntimeException)
	public void testCreateFood_IllegalTerrainCase() 
			throws Exception{
		world.createFood(1.0, 5.0);
		assertFalse(["Invalid Terrain"],world.getFood().size()==1); 
	}
	@Test (expected = RuntimeException)
	public void testCreateFood_IllegalBoundCase()
			throws Exception{

		world.createFood(illegalCoordinate, illegalCoordinate);
		assertFalse(["Invalid Coordinates"],world.getfood().size()==1)
	}
	@Test
	public void testCreateWorm_LegalCase() {
		world.createWorm(1.0, 1.0, 0.0, 0.25, "Test");
		assertEquals(world.getWorms().size() == 1);
		world.createWorm(0.0, 0.0, 0.0, 0.25, "Test");
		assertEquals(world.getWorms().size() == 2)
	}
	@Test (expected = RuntimeException)
	public void testCreateWorm_IllegalTerrainCase()
			throws Exception
			{
		world.createWorm(1.0, 5.0, 0.0, 0.25, "Test");
		assertFalse(["Invalid coordinates"],world.getWorms().size()== 1);
			}
	@Test (expected = RuntimeException)
	public void testCreateWorm_OutOfWorldBoundsCase()
			throws Exception
			{
		world.createWorm(illegalCoordinate, illegalCoordinate, 0.0, 0.25, "Test");
		assertFalse(["Invalid coordinates, out of world bounds"],world.getWorms().size()== 1);
			}
	@Test (expected = RuntimeException)
	public void testCreateWorm_IllegalNameCase()
			throws Exception{

		world.createWorm(0.0, 0.0, 0.0, 0.25, illegalTeamName);
		assertFalse(["Invalid name"],1 == world.getWorms().size());
	}
	@Test (expected = RuntimeException)
	public void testCreateWorm_IllegalRadiusCase()
			throws Exception{

		world.createWorm(0.0, 0.0, 0.0, illegalRadius, "Test");
		assertFalse(["Invalid Radius"],1 == world.getWorms().size());

	}
	@Test
	public void testLiesInWorld_LegalCase() {

		assertTrue(world.liesInWorld(1.0, 2.0, 0.25));
		assertTrue(world.liesInWorld(1.0, 2.0, 1));
	}
	@Test
	public void testLiesInWorld_IllegalOutOfBoundCase(){

		assertFalse(["Out of world bounds"],world.liesInWorld(illegalCoordinate, illegalCoordinate, 0.25));
	}
	@Test 
	public void testLiesInWorld_IllegalRadiusCase(){

		assertFalse(["Invalid Radius"],world.liesinWorld(1.0, 1.0, illegalRadius));
	}
	@Test
	public void testIsGameFinished(){
		assertFalse(world.isGameFinished);
		world.createWorm(1.0, 1.0, 1.0, 1.0, "Test1");
		world.createWorm(1.0, 1.0, 1.0, 1.0, "Test2");
		world.startGame();
		world.world.getWorms().get(0).setMaxHitPoints(0);
		assertTrue(world.isGameFinished);
	}


}
