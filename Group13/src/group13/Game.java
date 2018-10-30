package group13;

/**
 * Instantiation of Game class generate rooms and creates an input parser.
 * A method call runs the game.
 * 
 * @author Rasmus Willer
 */
public class Game {
    // Declare variables
    private Parser parser;
    private Room currentRoom;
    private String currentRoomName;
    private int hasBanana = 0;
    private int monkyHappy = 0;
    
    /**
     * No-arg constructor; Creates rooms and input parser.
     */
    public Game() {
        createRooms();
        parser = new Parser();
    }

    /**
     * Generate rooms with description and sets exit points to other rooms,
     * and sets which room is the current.
     */
    private void createRooms() {
        // Declare variables
        Room start, stairs, banana, monky, noMonky, flood;
        
        // Instantiate rooms with description
        start = new Room("start", "Første rum", "Du har været her før?");
        stairs = new Room("stairs", "Trapper!", "Trapper!");
        banana = new Room("banana", "Hey en banan!", "Hey en banan!");
        monky = new Room("monky", "Goddag abe", "Goddag abe!");
        noMonky = new Room("noMonky", "INGEN abe", "INGEN abe!");
        flood = new Room("flood", "Du hænger i over en flod!", "Du hænger i over en flod!");
        /*
        office = new Room("in the computing admin office");
        outside = new Room("outside the main entrance of the university");
        theatre = new Room("in a lecture theatre");
        */
        // Set exits to other rooms
        start.setExit("right", stairs);
        
        stairs.setExit("left", banana);
        stairs.setExit("right", banana);
        
        banana.setExit("left", stairs);
        banana.setExit("right", monky);
        
        monky.setExit("left", banana);
        monky.setExit("right", flood);
        
        noMonky.setExit("left", banana);
        noMonky.setExit("right", flood);
      
        flood.setExit("left", monky);

        // Set which room is current
        currentRoom = start;
        //currentRoomName = "start";
    }

    /**
     * Prints welcome message, runs the game, quit message.
     */
    public void play() {
        // Print welcome message
        printWelcome();

        // Game loop variable
        boolean finished = false;
        
        // Game loop
        while (! finished) {
            // Get new user input
            Command command = parser.getCommand();
            // Process command and if quit turn true
            finished = processCommand(command);
        }
        // Exit message
        System.out.println("Thank you for playing.  Good bye.");
    }

    /**
     * Print welcome message
     */
    private void printWelcome() {
        System.out.println();
        
        // Welcome message
        System.out.println("Welcome to the World of Zuul!");
        System.out.println("World of Zuul is a new, incredibly boring " +
                "adventure game.");
        
        // Help message
        System.out.println("Type '" + CommandWord.HELP + "' if you need help.");
        System.out.println();
        
        // Current room message
        System.out.println(currentRoom.getLongDescription());
    }
       

    /**
     * Processes the user command and perform the action.
     * 
     * @param command Command, contains parsed user input.
     * @return boolean, true for quit.
     */
    private boolean processCommand(Command command) {
        // Declare and assign quit variable to false
        boolean wantToQuit = false;
        
        // Declare and assign first command word
        CommandWord commandWord = command.getCommandWord();

        // If unknown command, then error message
        if(commandWord == CommandWord.UNKNOWN) {
            System.out.println("I don't know what you mean...");
            return false;
        }
        
        if(commandWord == CommandWord.INVENTORY){
            if(hasBanana == 1){
                System.out.println("Du har 1 banan i din inventory");
            }else{
                System.out.println("Du har intet i din inventory");
            }
        }
        
        if(commandWord == CommandWord.COLLECT){
            if(command.getSecondWord().equals("banana")){
                this.hasBanana = 1;
            }else{
                System.out.println("Do you wonna collect something?");
            }
        }
        
        if(commandWord == CommandWord.GIVE){
            if(command.getSecondWord().equals("banana") && currentRoom.getRoomName().equals("monky") && this.hasBanana == 1 && monkyHappy == 0){
                this.monkyHappy = 1;
                this.hasBanana = 0;
            }else if(this.hasBanana == 0){
                System.out.println("Du har jo ikke nogen banan?");
            }else{
                System.out.println("Der er jo ikke nogen at give en banan til??");
            }
        }
        
       

        // If help command, print help message
        if (commandWord == CommandWord.HELP) {
            printHelp();
        // If go command, then call go method with parsed command
        } else if (commandWord == CommandWord.GO) {
            
            if(currentRoom.getRoomName().equals("monky") && monkyHappy == 0 && command.getSecondWord().equals("right")){
                System.out.println("The monky wont let you go threw? What do you do?");
                return false;
            }
            
            goRoom(command);
        // If quit command, call quit method with parsed command
        } else if (commandWord == CommandWord.QUIT) {
            wantToQuit = quit(command);
        }
        // Return quit variable, true if want to quit
        return wantToQuit;
    }

    /**
     * Print help message and show available commands.
     */
    private void printHelp() {
        // Help message
        System.out.println("You are lost. You are alone. You wander");
        System.out.println("around at the university.");
        System.out.println();
        System.out.println("Your command words are:");
        
        // Print available commands
        parser.showCommands();
    }

    /**
     * Go to another room according to parsed command.
     * 
     * @param command Command, parsed user commands.
     */
    private void goRoom(Command command) {
        // If no second parsed user command, print error and return
        if(!command.hasSecondWord()) {
            System.out.println("Go where?");
            return;
        }
        
        // Assign second command word as direction
        String direction = command.getSecondWord();
        // Assign which room to go to
        
        Room nextRoom = currentRoom.getExit(direction);
       
        
        // If no room, write error message
        if (nextRoom == null) {
            System.out.println("There is no door!");
        // Set next room to current and print room description
        } else {
            currentRoom = nextRoom;
            if(direction.equals("right")){
                System.out.println(currentRoom.getLongDescription());
            }else if(direction.equals("left")){
                System.out.println(currentRoom.getLongDescriptionLeft());
            }
        }
    }

    /**
     * Confirm quit user request and return quit status.
     * 
     * @param command Command, parsed user command.
     * @return boolean, true for quit.
     */
    private boolean quit(Command command) {
        // If second command word, print error message and return false
        if(command.hasSecondWord()) {
            System.out.println("Quit what?");
            return false;
        // Otherwise return true
        } else {
            return true;
        }
    }
}
