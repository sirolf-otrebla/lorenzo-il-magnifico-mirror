package it.polimi.ingsw.ps05.model.resourcesandbonuses;

import java.io.Serializable;

import it.polimi.ingsw.ps05.model.PlayerRelated;
import it.polimi.ingsw.ps05.server.controller.Game;

/** this interface is meant to represent all possible result of an action. An action result is an atomic "effect", and
 *  it can be a Resource or some type of bonus. it must not be confused with {@link it.polimi.ingsw.ps05.model.effects.Effect}
 *  class: an {@link it.polimi.ingsw.ps05.model.effects.Effect} represents a collection ( an arraylist in this implementation
 *  of many Action Results.
 *
 */
public interface ActionResult extends Serializable {

    /** this method applies the ActionResult "effect" to a player
     *
     * @param playerR   this parameter represent the player that is going to be affected by the ActionResult.
     *                  because of being a {@link PlayerRelated} object, it can be a {@link it.polimi.ingsw.ps05.model.Familiar}
     *                  or a {@link it.polimi.ingsw.ps05.model.Player}
     *                  @see PlayerRelated
     */
    void applyResult(PlayerRelated playerR);

    /** there are many action results that need a value to be properly represented. e.g, a value can be a resource amount
     * or can be the magnitude of a die bonus that the action result applies on certain actions. moreover, if the action
     * result is a bonus action, this value can be the die related to that bonus action. this method sets that value.
     *
     * @param amount   integer representing the value of the action result
     * @throws NoSuchMethodException if a certain action result does not need a value, or if that value cannot be set at
     * runtime, this exception is thrown
     */
    public  void setValue(Integer amount) throws NoSuchMethodException;

    /** there are many action results that need a value to be properly represented. e.g, a value can be a resource amount
     * or can be the magnitude of a die bonus that the action result applies on certain actions. moreover, if the action
     * result is a bonus action, this value can be the die related to that bonus action. this method returns that value.
     *
     * @return returns the value of the actionresult
     * @throws NoSuchMethodException if a certain action result does not need a value, this exception is thrown.
     */
    public  Integer getValue() throws NoSuchMethodException;

    /** when an actionresult is serialized and sent by the network, it is important to set a reference to the game object
     * that relates to it. this method set the game related to the action result (it cannot be done at construction time
     * because of class loading limitations).
     *
     * @param game this object represent the match within the action result lives.
     */
    public  void setGame(Game game);

    /**when an actionresult is serialized and sent by the network, it is important to set a reference to the game object
     * that relates to it. this method returns the related game game
     *
     * @return returns a {@link Game} object representing the match within the action result lives.
     */
    public  Game getGame();

    @Override
    public  String toString();



}
