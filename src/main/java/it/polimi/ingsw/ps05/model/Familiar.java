package it.polimi.ingsw.ps05.model;

import it.polimi.ingsw.ps05.model.spaces.ActionSpace;
import it.polimi.ingsw.ps05.model.resourcesandbonuses.Dice;
import it.polimi.ingsw.ps05.model.exceptions.RepeatedAssignmentException;

import java.io.Serializable;

import static it.polimi.ingsw.ps05.model.ColorEnumeration.NOT_INITIALIZED;

/** this class represents a Player's Family member. a Family member is necessary to activate
 * most types of actions, and is identified by a color from {@link ColorEnumeration}, which
 * can be {@code ColorEnumeration.Orange, ColorEnumeration.White, ColorEnumeration.Black,} and a player.
 * moreover this class implements the {@link PlayerRelated} interface, which represents all the object who
 * are related to a certain player inside the Game's model. through this interface, the game controller or
 * an action space can obtain the reference to the {@link Player} who's related to this FamilyMember
 *
 */
public class Familiar implements Serializable, PlayerRelated {


	/**
	 * 
	 */
	private static final long serialVersionUID = 8175413398375108149L;
	private ActionSpace position;
	private Dice relatedDice;
	private ColorEnumeration color = NOT_INITIALIZED;
	private boolean used =false;
	private Player relatedPlayer = null;


	private ColorEnumeration relatedPlayerColor;

	/**
	 * this is the default constructor for this class. it is not suggested to instance this
	 * Class using this Constructor.
	 */
	public Familiar(){
		
	}

	/** this constructor needs the player to insance the player
	 *
	 * @param player player which possesses the FamilyMember
	 */
	public Familiar (Player player){
		this.relatedPlayer = player;
		this.relatedPlayerColor = this.relatedPlayer.getColor();
	}

	/** this constructor needs the player which possesses the FamilyMember,
	 *  and the FamilyMember's color
	 *
	 * @param player	player which possesses the FamilyMember
	 * @param color	FamilyMember's color
	 */
	public Familiar (Player player, ColorEnumeration color){
		this.relatedPlayer = player;
		this.color = color;
		this.relatedPlayerColor = this.relatedPlayer.getColor();
	}

	/**	this constructor needs the player which possesses the FamilyMember,
	 *  the FamilyMember's color, and the die related to that color
	 *
	 * @param dice 	 die related to this family Member
	 * @param color  FamilyMember's color
	 * @param player player which possesses the FamilyMember
	 */
	public Familiar(Dice dice, ColorEnumeration color, Player player){
		this.relatedPlayer = player;
		this.relatedDice = dice;
		this.color = color;
		this.relatedPlayerColor = this.relatedPlayer.getColor();
	}

	/**
	 *
	 * @return returns the position where the Family Member is located. returns null
	 * if the Family Member is not used;
 	 */
	public ActionSpace getPosition() {
		return position;
	}

	/**
	 *
	 * @return returns a boolean value which is {@code true} if the Object is used by the player (e.g
	 * is located on a certain {@link ActionSpace} on the board), and {@code false} if the familiar is not
	 * already used an thus can do actions.
	 */
	public boolean isUsed(){
		return used;
	}

	/** this method is used to set the Family Member position on a certain action space. it is designed
	 * only to set the {@code position} field inside the Family Member, and to not modify the ActionSpace.
	 * thus, when using this method is also mandatory to set the {@link ActionSpace} occupied by the Family
	 * Member
	 *
	 * @param position	the {@link ActionSpace} where the Family Member is located
	 */
	public void setPosition(ActionSpace position) {
		this.position = position;
		used = true;
	}

	/** this method is used to set the dice related to a certain family member when another turn starts
	 *
	 * @param dice	 the {@link Dice} object related to a familyMember
	 */
	public void setDice(Dice dice){
		this.relatedDice = dice;
		//System.out.println(relatedPlayer.getPlayerID() + ": " + dice.getValue() + " setted on color: " + color.toString());
	}

	/** this method is used to set the Family Member's color. it can be called only once: if called more times
	 *	it throws a {@link RepeatedAssignmentException } exception
	 * @param color the family member's color (Can be {@code Orange, White, Black, Any, Ghost} )
	 * @throws RepeatedAssignmentException this exception is thrown when the method is called but the color's
	 * already set.
	 */
	public void setColor(ColorEnumeration color) throws RepeatedAssignmentException {
		if (this.color == NOT_INITIALIZED) {
			this.color = color;
		} else {
			throw new RepeatedAssignmentException();
		}
	}

	/** this method is used to set the {@link Player} which possesses this Family Membr
	 *
	 * @param player the {@link Player} which possesses this Family Membr
	 * @throws RepeatedAssignmentException this exception is thrown when the method is called
	 * but the color's already set.
	 */
	public void setPlayer(Player player) throws RepeatedAssignmentException {
		if (this.relatedPlayer == null) {
			this.relatedPlayer = player;
			this.relatedPlayerColor = this.relatedPlayer.getColor();
		} else {
			throw new RepeatedAssignmentException();
		}
	}

	/** this method resets the Family Member position. Note that this method only takes care of this object
	 * state, it not modify any of the {@link ActionSpace} objects
	 */
	public void resetPosition(){
	    this.setPosition(null);
    }

	/** getter for related die
	 *
	 * @return die related to this family member
	 */
	public Dice getRelatedDice() {
		return this.relatedDice;
	}

	/** getter for related color
	 *
	 * @return Color of this family member
	 */
	public ColorEnumeration getColor(){
		return this.color;
	}

	/** getter for related {@link Player}
	 *
	 * @return {@link Player} related to this family member
	 */
	@Override
	public Player getRelatedPlayer(){
		return this.relatedPlayer;
	}


	/** getter for related {@link Player} Color, which can be {@code Red, Blue, Yellow, Green}
	 *
	 * @return Color of {@link Player} related to this family Member
	 */
	public ColorEnumeration getRelatedPlayerColor() {
		return relatedPlayerColor;
	}
}
