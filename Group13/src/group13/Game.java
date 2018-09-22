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
        Room outside, theatre, pub, lab, office;
        
        // Instantiate rooms with description
        lab = new Room("in a computing lab");
        pub = new Room("in the campus pub");
        office = new Room("in the computing admin office");
        outside = new Room("outside the main entrance of the university");
        theatre = new Room("in a lecture theatre");
        
        // Set exits to other rooms
        lab.setExit("north", outside);
        lab.setExit("east", office);
        
        pub.setExit("east", outside);
        
        office.setExit("west", lab);
        
        outside.setExit("east", theatre);
        outside.setExit("south", lab);
        outside.setExit("west", pub);

        theatre.setExit("west", outside);

        // Set which room is current
        currentRoom = outside;
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

        // If help command, print help message
        if (commandWord == CommandWord.HELP) {
            printHelp();
        // If go command, then call go method with parsed command
        } else if (commandWord == CommandWord.GO) {
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
            System.out.println(currentRoom.getLongDescription());
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
