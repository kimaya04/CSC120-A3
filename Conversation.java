import java.util.Scanner;
import java.util.Random;

class Conversation implements Chatbot {

  // Attributes 
  // Array of stored responses
  public String[] responses = {"Hello earthling! What are you thinking?", "Interesting...", "Go on.", "I see.", "That's something.", "Tell me more.", "Adios!"};
  // Empty array for conversation transcript
  public String[] transcript;
  // Empty string for user input
  public String inputString;
  // Array of keywords to be mirrored
  public String[] keyStrings = {"me","I","am","you","my","your", "I'm", "you're", "are"};
  // Array of mirrored keywords
  public String[] mirrorStrings = {"you","you","are","I","your","my", "you're", "I'm", "am"};
  // Integer for number of rounds
  int rounds;
  // Scanner object for user input
  Scanner reader = new Scanner(System.in);

  /**
   * Constructor
   * Initializes the Conversation object
   * Initializes empty transcript array, empty inputString, and int rounds
   */
  Conversation() {
    transcript=new String[100];
    inputString = "";
    rounds = 0;
  }

  /**
   * Starts and runs the conversation with the user
   * Asks user for number of rounds
   * Iterates through rounds, asks user for input and responds appropriately
   * When rounds are complete, prints last response
   * Uses counter to appropriately add each line of conversation to transcript array
   */
  public void chat() {
    int counter = 0;

    System.out.println("How many rounds?");
    rounds = reader.nextInt();
    reader.nextLine();

    System.out.println(responses[0]);
    transcript[counter++] = responses[0] + "\n";

    while  (rounds>0) {
      inputString = reader.nextLine();
      transcript[counter++] = inputString + "\n";
      System.out.println(respond(inputString));
      transcript[counter++] = respond(inputString) + "\n";
      rounds-=1;
    }
    System.out.println(responses[responses.length-1]);
    transcript[counter++] = responses[responses.length-1] + "\n";

    reader.close();
  }

  /**
   * Prints header for transcript
   * Iterates through transcript array and prints each element
   * Checks if element exists, if so, prints element; else, does nothing
   */
  public void printTranscript() {
    System.out.println("\n" + "Transcript:");
    for (int i=0; i<transcript.length; i++) {
      if (transcript[i] != null) {
        System.out.print(transcript[i]);
      }
    }
  }

  /**
   * Gives appropriate response (mirrored or canned) to user input
   * @param inputString the users last line of input
   * @return mirrored or canned response to user input
   * Splits input into array of words, checks if each word is a keyString
   * If so, replaces word with mirrored word and adds to returnString; else, adds word to returnString
   * Capitalizes first letter of returnString and adds question mark if keyString is present
   * Else, returns random response from responses array  
   */
  public String respond(String inputString) {

    String returnString = "";
    String[] words = inputString.split(" "); 
    boolean containskeyString = false;

    for (String word1 : words) {
      boolean replaced = false;
      for (int i=0; i<keyStrings.length; i++) {
        if (word1.toLowerCase().matches(".*\\b" + keyStrings[i].toLowerCase() + "\\b.*")) {
          word1 = mirrorStrings[i];
          returnString = returnString + word1 + " ";
          containskeyString = true;
          replaced = true;
          break;
        }
      }
      if (!replaced) {
        returnString = returnString + word1 + " ";
      }
    }
      
    if (containskeyString) {
      returnString = returnString.substring(0, 1).toUpperCase() + returnString.substring(1,returnString.length()-1) + "?";
    }
     
    else {
      Random rand = new Random();
      int randomIndex = rand.nextInt(responses.length-2)+1;
      returnString = responses[randomIndex];
    } 
  
    return returnString; 
  }

  public static void main(String[] arguments) {

    Conversation myConversation = new Conversation();
    myConversation.chat();
    myConversation.printTranscript();
  }
}
