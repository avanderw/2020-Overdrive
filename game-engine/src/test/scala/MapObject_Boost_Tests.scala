import org.scalatest.FunSuite
import test.TestHelper
import za.co.entelect.challenge.game.contracts.Config.Config
import za.co.entelect.challenge.game.contracts.command.RawCommand
import za.co.entelect.challenge.game.contracts.commands.CommandFactory
import za.co.entelect.challenge.game.contracts.game.CarGamePlayer

class MapObject_Boost_Tests extends FunSuite{
  private val commandText = "NOTHING";
  private var commandFactory: CommandFactory = null;
  private var nothingCommand: RawCommand = null;

  def initialise() = {
    Config.loadDefault();
    commandFactory = new CommandFactory;
    nothingCommand = commandFactory.makeCommand(commandText)
  }

  test("Given players during race when player hits boost then player gains 1 boost powerup") {
    initialise()
    val gameMap = TestHelper.initaliseGameWithMapObjectAt(1, 3, Config.BOOST_MAP_OBJECT);
    val testGamePlayer1 = TestHelper.getTestGamePlayer1();

    val testCarGamePlayer = testGamePlayer1.asInstanceOf[CarGamePlayer];
    nothingCommand.performCommand(gameMap, testGamePlayer1);

    assert(testCarGamePlayer.getPowerups().count(x => x == Config.BOOST_POWERUP_ITEM) == 1);
  }
}
