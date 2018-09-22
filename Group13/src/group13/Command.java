package group13;


/**
 * Container for parsed user input.
 * 
 * @author Rasmus Willer
 */
public class Command {
    // Declare class variables
    private CommandWord commandWord;
    private String secondWord;

    /**
     * Constructor; assigning available commands and 1st command word, and 2nd
     * command word to object.
     * 
     * @param commandWord CommandWord, containing available 1st command words.
     * @param secondWord String, second command word.
     */
    public Command(CommandWord commandWord, String secondWord) {
        this.commandWord = commandWord;
        this.secondWord = secondWord;
    }

    /**
     * Return 1st command word.
     * 
     * @return CommandWord, containing available commands and first command
     * word.
     */
    public CommandWord getCommandWord() {
        return commandWord;
    }

    /**
     * Return 2nd command word.
     * 
     * @return String, 2nd command word
     */
    public String getSecondWord() {
        return secondWord;
    }

    /**
     * Check for unknown command.
     * 
     * @return boolean, true for unknown command.
     */
    public boolean isUnknown() {
        return (commandWord == CommandWord.UNKNOWN);
    }

    /**
     * Check for 2nd command word.
     * 
     * @return boolean, false if no 2nd command word.
     */
    public boolean hasSecondWord() {
        return (secondWord != null);
    }
}
