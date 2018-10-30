package group13;

// Import section
import java.util.Set;
import java.util.HashMap;
import java.util.Iterator;

/**
 * Lets rooms have descriptions and exits leading to other rooms.
 * 
 * @author Rasmus Willer
 */
public class Room {
    // Declare variables
    private String description;
    private String leftDesc;
    private String roomName;
    private HashMap<String, Room> exits;

    /**
     * Constructor; assign description of room to variable and instantiate exit
     * HashMap.
     * 
     * @param description String, description of room.
     */
    public Room(String description) {
        this.description = description;
        exits = new HashMap<String, Room>();
    }
    
    public Room(String description, String rightDesc) {
        this.description = description;
        this.leftDesc = rightDesc;
        exits = new HashMap<String, Room>();
    }
    
    public Room(String roomName, String description, String rightDesc) {
        this.description = description;
        this.leftDesc = rightDesc;
        this.roomName = roomName;
        exits = new HashMap<String, Room>();
    }

    /**
     * Set an exit point from the room.
     * 
     * @param direction String, direction out of room.
     * @param neighbor Room, the room the exit leads to.
     */
    public void setExit(String direction, Room neighbor) {
        exits.put(direction, neighbor);
    }
    
    public String getRoomName(){
        return this.roomName;
    }

    /**
     * Return short description of the room.
     * 
     * @return String, short description of the room.
     */
    public String getShortDescription() {
        return description;
    }

    /**
     * Return long description of the room.
     * 
     * @return String, long description of the room.
     */
    public String getLongDescription() {
        return description + ".\n" + getExitString();
    }
    
    public String getLongDescriptionLeft() {
        return leftDesc + ".\n" + getExitString();
    }

    /**
     * Return complex String of exits from the room.
     * 
     * @return String, exits from the room.
     */
    public String getExitString() {
        // Start the String with title
        String returnString = "Exits:";
        // add each exit to the String, separated by space
        Set<String> keys = exits.keySet();
        for(String exit : keys) {
            returnString += " " + exit;
        }
        // Return String listing exits
        return returnString;
    }

    /**
     * Get the room associated with an exit.
     * 
     * @param direction String, direction of exit.
     * @return Room, room in that direction.
     */
    public Room getExit(String direction) {
        return exits.get(direction);
    }
}
