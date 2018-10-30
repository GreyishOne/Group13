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
    Room river, darkroom, ladder, start, jungle;
    
    
    
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
        // Instantiate rooms with description
        start = new Room("You are confused, and everything looks strange. There is a ladder ahead, do you want to continue?\nXXXXXXXXXXXXXXXXXXXXXXXXXX\n" +
"X88888888                X\n" +
"X888888         oo       X\n" +
"X88888         /()\\  ->  X\n" +
"X88888          /\\       X\n" +
"XXXXXXXXXXXXXXXXXXXXXXXXXX");
        ladder = new Room("You are at a ladder, you see a jungle ahead of you, do you want to continue?\nXXXXXXXXXXXXXXXXXXXXXXXXXX\n" +
"X                     -> X\n" +
"X      oo      øøøøøøøøøøX\n" +
"X <-  /()\\     [         X\n" +
"X      /\\      [         X\n" +
"XXXXXXXXXXXXXXXXXXXXXXXXXX");
        jungle = new Room("You see a ladder behind you, and now find yourself in a jungle,\nyou see a river ahead, and a rope that you could use to swing yourself across the river,\ndo you want to continue or go back?\nXØØØØØØØØØØØØØØØØØØØØØØØØX\n" +
"X<-            l         X\n" +
"Xøøø    oo     l         X\n" +
"X  ]   /()\\    l     ->  X\n" +
"X  ]    /\\               X\n" +
"XØØØØØØØØØØØØ________ØØØØX");
        river = new Room("You see the rope behind you, that you can use to swing back across the river, and a dark tunnel ahead, do you want to continue?\nXØØØØØØØØØØØØØØØØØØØØØØØØX\n" +
"X      l              888X\n" +
"X      l        oo     88X\n" +
"X      l   <-  /()\\  ->  X\n" +
"X               /\\       X\n" +
"XØØØ________ØØØØØØØØØØØØØX");
        darkroom = new Room("You are now in a completely dark place, do you want to stay here?\nXXXXXXXXXXXXXXXXXXXXXXXXXX\n" +
"X888888888888888888888888X\n" +
"X     oo   88888888888888X\n" +
"X <- /()\\  88888888888888X\n" +
"X     /\\   88888888888888X\n" +
"X888888888888888888888888X");
        
        // Set exits to other rooms
        start.setExit("continue", ladder);
        
        ladder.setExit("climb", jungle);
        ladder.setExit("backtrack", start);
        
        jungle.setExit("rope", river);
        jungle.setExit("backtrack", ladder);
        
        
        river.setExit("tunnel", darkroom);
        river.setExit("rope", jungle);

        darkroom.setExit("backtrack", river);

        // Set which room is current
        currentRoom = start;
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
        System.out.println("DONT PLAY THIS GAME!");
        System.out.println("CONTINUE AT YOUR OWN RISK");
        System.out.println("");
        
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
        System.out.println("You are lost. You are alone. You wander around");
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
