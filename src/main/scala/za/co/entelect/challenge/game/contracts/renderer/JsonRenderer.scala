package za.co.entelect.challenge.game.contracts.renderer

import net.liftweb.json._
import net.liftweb.json.JsonDSL._

import za.co.entelect.challenge.game.contracts.game.GamePlayer
import za.co.entelect.challenge.game.contracts.map.GameMap
import za.co.entelect.challenge.game.contracts.map.CarGameMap
import za.co.entelect.challenge.game.contracts.game.CarGamePlayer

class JsonRenderer extends GameMapRenderer {
    def render(gameMap: GameMap, gamePlayer: GamePlayer): String = {
        val carGameMap: CarGameMap = gameMap.asInstanceOf[CarGameMap];

        val shouldRenderFragment = gamePlayer != null;
        if(shouldRenderFragment) 
        {
            val carGamePlayer: CarGamePlayer = gamePlayer.asInstanceOf[CarGamePlayer];
            return renderFragment(carGameMap, carGamePlayer);
        } 
        else 
        {
            return renderVisualiserMap(carGameMap);
        }
    }

    def renderFragment(gameMap: CarGameMap, gamePlayer: CarGamePlayer): String = {
        val mapFragment = gameMap.getMapFragment(gamePlayer);
        val mapFragmentPlayer = mapFragment.getPlayer();
        val mapFragmentPlayerPosition = mapFragmentPlayer.getPosition();
        val mapFragmentJsonStructure = 
            ("currentRound" -> mapFragment.getCurrentRound()) ~
            ("maxRounds" -> 1500) ~
            ("player" ->
                ("id" -> mapFragmentPlayer.getId()) ~
                ("position" ->
                    ("lane" -> mapFragmentPlayerPosition.getLane()) ~
                    ("blockNumber" -> mapFragmentPlayerPosition.getBlockNumber())
                ) ~
                ("speed" -> mapFragmentPlayer.getSpeed()) ~
                ("state" -> mapFragmentPlayer.getState())
            ) ~
            ("lanes" -> 
                mapFragment.getLanes().toList.map {l => 
                    ("position" ->
                        ("lane" -> l.getPosition().getLane()) ~
                        ("blockNumber" -> l.getPosition().getBlockNumber())
                    ) ~
                    ("object" -> l.getMapObject()) ~
                    ("occupiedByPlayerWithId" -> l.getOccupiedByPlayerWithId())
                });
        
        val jsonString = prettyRender(mapFragmentJsonStructure);
        return jsonString;
    }

    def renderVisualiserMap(gameMap: CarGameMap) : String = {
        throw new NotImplementedError("Json renderer render visualiser map");
    }

    def commandPrompt(gamePlayer: GamePlayer): String = {
        throw new NotImplementedError("Json renderer command prompt");
    }
}