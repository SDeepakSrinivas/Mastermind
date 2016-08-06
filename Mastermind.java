import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Mastermind {
	
	public static Scanner s;
	
	public Mastermind() {
		s = new Scanner(System.in);
	}
	
	public static void main(String[] args) {
		Mastermind m = new Mastermind();
		m.playMastermind();
	}

	private  void playMastermind() {
		String path = "C:\\Users\\desriram\\workspace\\Mastermind\\src\\sowpods.txt";
		int difficulty = 5;
		ArrayList<String> validWords = getWordsFromFile(path,difficulty);
		ArrayList<Integer> repeatFlag = getRepeatFlag(validWords);
		startPlaying(validWords,repeatFlag);
		
	}

	private void startPlaying(ArrayList<String> validWords,ArrayList<Integer> repeatFlag) {
		p("I have thought of a word.. Its ur turn...\n");
		startGuessing(validWords, repeatFlag);
		
	}

	private void startGuessing( ArrayList<String> validWords,ArrayList<Integer> repeatFlag) {

		String pcWord = validWords.get(findRandom(validWords));
		guessUtil(pcWord,validWords,repeatFlag);
		
	}

	private void guessUtil( String pcWord, ArrayList<String> validWords, ArrayList<Integer> repeatFlag) {
		p(validWords.toString()+"\n");
		p(validWords.indexOf("abord")+"\n");
		int r = 0,index = 0;
		for(int i = 0; i<validWords.size();i++)
			if(repeatFlag.get(i) == 1)
				{ r++; index = i; }
		String pcGuess = "";
		if(r==1) pcGuess = validWords.get(index);
		if(r == 1)
			p("I guess " + pcGuess +"\n Enter the number of common letters or press 1111 for if I have won\n");
		else {
			int ind = findRandom(validWords);
			pcGuess = validWords.get(ind);
			validWords.remove(ind);
			repeatFlag.remove(ind);
			p("I guess " + pcGuess+"\n Enter the number of common letters or press 1111 for if I have won\n");
		}
		int common = s.nextInt();
		if(common!=1111) {
			p("here"+common+"  ");
			removeNonCommon(pcGuess,validWords,repeatFlag,common);
			boolean res = userGuess(pcWord);
			if(res)
				return;
			guessUtil(pcWord,validWords,repeatFlag);
		}
		else
			p("I HAVE WON !!\n");
		
	}

	private boolean userGuess(String pcWord) {
		p("Enter ur guess \n");
		String inp = s.next();
		if(inp.equals(pcWord)) {
			p("U HAVE WON !!\n");
			return true;
		}
		else {
			p("No of common Letters : " +isCommonWith(pcWord,inp) + "\n");
			return false;
		}
			
		
		
	}

	private void removeNonCommon(String pcGuess,ArrayList<String> validWords, ArrayList<Integer> repeatFlag, int common) {
		for(int i = 0; i < validWords.size(); i++)
			if(!(isCommonWith(pcGuess,validWords.get(i))==common)) {
				validWords.remove(i);
				repeatFlag.remove(i);
			}
	}

	private int isCommonWith(String pcGuess, String string) {
		
		HashMap<String,Integer> hash1 = new HashMap<String,Integer>();
		HashMap<String,Integer> hash2 = new HashMap<String,Integer>();
		for(int i = 0; i < pcGuess.length(); i++)
			if(!hash1.containsKey(""+pcGuess.charAt(i)))
				hash1.put(""+pcGuess.charAt(i), 1);
		for(int i = 0; i < string.length(); i++)
			if(!hash2.containsKey(""+string.charAt(i)))
				hash2.put(""+string.charAt(i), 1);
		int cnt = 0;
		for(String k : hash1.keySet())
			if(hash2.containsKey(k))
				cnt++;

		return cnt;
	}

	private void p(Object o) {
		System.out.print(o);
	}

	private ArrayList<Integer> getRepeatFlag(ArrayList<String> validWords) {
		ArrayList<Integer> repeat = new ArrayList<Integer>();
		for(int i=0;i<validWords.size();i++)
			repeat.add(0);
		for(int i = 0; i < validWords.size(); i++) 
			if(!isRepeat(validWords.get(i)))
				repeat.set(i, 1);
		return repeat;
	}

	private boolean isRepeat(String string) {
		HashMap<String,Integer> hash = new HashMap<String,Integer>();
		for(int i = 0; i < string.length(); i++)
			if(hash.containsKey(""+string.charAt(i)))
				return true;
			else
				hash.put(""+string.charAt(i), 1);
		return false;
	}

	private int findRandom(ArrayList<String> validWords) {
		return (int)(Math.random() * (validWords.size()-1));
	}

	// Read all the words from the file
	private static ArrayList<String> getWordsFromFile(String path,int difficulty) {

		ArrayList<String> words = new ArrayList<String>();
		FileInputStream inputStream = null;
		Scanner sc = null;
		try {
		    inputStream = new FileInputStream(path);
		    sc = new Scanner(inputStream, "UTF-8");
		    while (sc.hasNextLine()) {
		        String line = sc.nextLine();
		        if(line.length() == difficulty)
		        words.add(line);
		    }
		    // note that Scanner suppresses exceptions
		    if (sc.ioException() != null) {
		        throw sc.ioException();
		    }
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
		    if (inputStream != null) {
		        try {
					inputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		    }
		    if (sc != null) {
		        sc.close();
		    }
		}
		
		return words;
	}
}
