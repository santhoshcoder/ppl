/*
  This is a stand-alone Java program that reads the standard input
  file then prints the number of newline characters, the number
  of white-space separated character sequences, and the number
  of bytes read.
*/
public class LProg0
{
  static int lines = 0;
  static int words = 0;
  static int characters = -1;

  // `characters' is initialized to -1 because it is incremented with
  // each read, including the final read executed at end-of-file and
  // that final read does not actually add a character.


  // whiteSpace returns true iff its argument is a
  // space, newline, formfeed, tab, or carriage return.

  public static boolean whiteSpace(char c)
  {
    switch (c)
    {
      case ' ':
      case '\n':
      case '\f':
      case '\t':
      case '\r':
        return true;
      default:
        return false;
    }
  }
  public static int getNext() throws java.io.IOException
  {
    characters++;
    return System.in.read();
  }
  // The main method is invoked when this program is interpreted with
  // the java interpreter.

  public static void main(String argv[]) throws java.io.IOException
  {
    int maxLength = 0;
    int i = getNext();
    while (i != -1)
    {
      // Repeat until end-of-file is reached.
      if ( !whiteSpace((char)i))
        {
          //
          // Word state
          //
          words++;    // We've seen another word.
          int wordLength = 0;
          do
           {
               // Skip to the next white space character.
               wordLength++;
               i = getNext();
           } while (i != -1 && !whiteSpace((char)i));
           if(maxLength < wordLength)
           {
              maxLength = wordLength;
              wordLength = 0;
           }
        }
      else
        {
           //
           // whiteSpace state
           //
          do
            {
               if ((char)i == '\n')
               {
                 lines++;   // We've seen another line;

               }
               i = getNext();
            } while (whiteSpace((char)i));
        }
    }
    System.out.println(" " + lines + " " + words + " " + characters);
    System.out.println("Length of the longest word is: " + maxLength);
    System.out.println("The average length of lines is: " + (characters - lines)/(1.0*lines));
  }
}
