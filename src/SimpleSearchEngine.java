import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

public class SimpleSearchEngine {

	public final String huruf = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz ";
	public final int size = 300;
	public LinkedList[] list;
	
	public BufferedReader read(String linklagi) {
        URL url = null;
        try {
            url = new URL(linklagi);
        } catch (MalformedURLException e1) {
            e1.printStackTrace();
        }
        URLConnection conn = null;
        try {
            conn = url.openConnection();
        } catch (IOException e) {
            e.printStackTrace();
        }
        BufferedReader br = null;
        try {
            br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return br;
    }
	
	public String substrMundur(String kata,int mundurBerapa) {
		String ret = "";
		if(kata.length() > mundurBerapa) {
			for(int i = kata.length()-mundurBerapa;i < kata.length();i++) {
				ret+=kata.charAt(i);
			}
		}else {
			ret = kata;
		}
		return ret;
	}

	public String substrMaju(String kata,int majuBerapa) {
		String ret = "";
		if(kata.length() > majuBerapa) {
			for(int i = 0;i < majuBerapa;i++) {
				ret+=kata.charAt(i);
			}
		}else {
			ret = kata;
		}
		return ret;
	}

	public String[] findURL(String htmlString,String baseURL) {
		int countSize = 20;
		String[] ret = new String[countSize];
		int idx = 0;
		for(int i = 0;i < htmlString.length();i++) {
			char curr1 = htmlString.charAt(i);
			if(idx == countSize) {
				break;
			}
			if(i+22 < htmlString.length()) {
				char curr2 = htmlString.charAt(i + 1);
				char curr3 = htmlString.charAt(i + 2);
				char curr4 = htmlString.charAt(i + 3);
				char curr5 = htmlString.charAt(i + 4);
				char curr6 = htmlString.charAt(i + 5);
				char curr7 = htmlString.charAt(i + 6);
				char curr8 = htmlString.charAt(i + 7);
				char curr9 = htmlString.charAt(i + 8);
				char curr10 = htmlString.charAt(i + 9);
				char curr11 = htmlString.charAt(i + 10);
				char curr12 = htmlString.charAt(i + 11);
				char curr13 = htmlString.charAt(i + 12);
				if(curr1 == 'h' && curr2 == 'r' && curr3 == 'e' && curr4=='f' && curr5 =='=' && curr6 == '"' && 
						curr7 =='/' && curr8 =='i' && curr9 == 'd' && curr10 =='-' && curr11 =='i' && curr12 =='d' && 
						curr13 == '/') {
					String s = "";
					for(int j = i+12;j < htmlString.length();j++) {
						s+=htmlString.charAt(j);
						if(htmlString.charAt(j+1)=='"') {
							if(s.length() < 30) {
								break;
							}
							ret[idx++]=baseURL+s;
							break;
						}
					}
				}
			}
		}
		return ret;
	}
	
	public String removeJS(String htmlString) {
		String ret = "";
		boolean add = true;
		for(int i = 0;i < htmlString.length();i++) {
			char curr1 = htmlString.charAt(i);
			if(i+8 < htmlString.length()) {
				char curr2 = htmlString.charAt(i+1);
				char curr3 = htmlString.charAt(i+2);
				char curr4 = htmlString.charAt(i+3);
				char curr5 = htmlString.charAt(i+4);
				char curr6 = htmlString.charAt(i+5);
				char curr7 = htmlString.charAt(i+6);
				char curr8 = htmlString.charAt(i+7);
				if(curr1 == '<' && curr2 == 's' && curr3 == 'c' && curr4 == 'r' && curr5 == 'i' && curr6 == 'p' && curr7 =='t') {
					add = false;
				}else if(curr1 == '<' && curr2 == '/' && curr3 == 's' && curr4 == 'c' && curr5 == 'r' && curr6 == 'i' && curr7 =='p' && curr8 =='t') {
					add = true;
				}
			}
			if(add) {
				ret+=curr1;
			}
			
		}
		return ret;
	}

	public String removeCSS(String htmlString) {
		String ret = "";
		boolean add = true;
		for(int i = 0;i < htmlString.length();i++) {
			char curr1 = htmlString.charAt(i);
			if(i+8 < htmlString.length()) {
				char curr2 = htmlString.charAt(i+1);
				char curr3 = htmlString.charAt(i+2);
				char curr4 = htmlString.charAt(i+3);
				char curr5 = htmlString.charAt(i+4);
				char curr6 = htmlString.charAt(i+5);
				char curr7 = htmlString.charAt(i+6);
				if(curr1 == '<' && curr2 == 's' && curr3 == 't' && curr4 == 'y' && curr5 == 'l' && curr6 == 'e') {
					add = false;
				}else if(curr1 == '<' && curr2 == '/' && curr3 == 's' && curr4 == 't' && curr5 == 'y' && curr6 == 'l' && curr7 =='e') {
					add = true;
				}
			}
			if(add) {
				ret+=curr1;
			}
			
		}
		return ret;
	}

	public String removeHTML(String htmlString) {
		int pos1 = 0;
		int pos2 = 0;
		String ret = "";
		boolean add = true;
		for(int i = pos1;i < htmlString.length();i++) {
			char curr = htmlString.charAt(i);
			if(curr == '>') {
				String st = "";
				for(int j = i+1;j < htmlString.length();j++) {
					char curr2 = htmlString.charAt(j);
					if(curr2 != '<') {
						st += curr2;
					}
					if(curr2 == '<') {
						pos1 = j;
						break;
					}
				}
				ret += st;
			}
		}
		return ret;
	}

	public String removeCharacters(String htmlString) {
		String ret = "";
		for(int i = 0;i < htmlString.length();i++) {
			boolean add = false;
			for(int j = 0;j < huruf.length();j++) {
				if(htmlString.charAt(i) == huruf.charAt(j)) {
					add = true;
					break;
				}
			}
			if(add) {
				ret+=htmlString.charAt(i);
			}
		}
		return ret;
	}

	public int generateHash(String word) {
		int hash = 0;
		for(int i = 0;i < word.length();i++) {
			char curr = word.charAt(i);
			hash += curr;
		}
		return hash%size;
		
	}
	
	public String readerToString(BufferedReader br) {
		String ret = "";
		String inputLine = "";
		try {
			while ((inputLine = br.readLine()) != null) {
				ret+=inputLine;
			}
		} catch (IOException e) {
			e.printStackTrace();
			return "";
		}
		return ret;
	}
	
	public String[] generateWord(BufferedReader br) {
		String all = readerToString(br);
		all = removeJS(all);
		all = removeCSS(all);
		all = removeHTML(all);
		all = removeCharacters(all);
//		System.out.println(all);
		String[] temp = all.split(" ");
		int addIdx = 0;
		String[] hasil = new String[temp.length];
		for(int i = 0;i < temp.length;i++) {
			if(temp[i].length() > 1) {
				hasil[addIdx++] = temp[i];
			}
		}
		String[] has2 = new String[addIdx];
		for(int i = 0;i < addIdx;i++) {
//			System.out.print(hasil[i]+", ");
			has2[i] = hasil[i];
		}
		return has2;
	}
	
	public void search() {
		Scanner scanner = new Scanner(System.in);
		while(true) {
			System.out.print("Masukan search : ");
			String sr = scanner.nextLine();
			if(sr.equals("stop-search")) {
				break;
			}
			int sh = generateHash(sr);
			list[sh].print();
			System.out.println("type in ( stop-search ) if you want to stop searaching");
		}
		scanner.close();
	}
	
	public SimpleSearchEngine(String link) {
		list = new LinkedList[size];
		for(int i = 0;i < list.length;i++) {
			list[i] = new LinkedList();
		}
		try {
			BufferedReader br = read(link);
			String mainPage = readerToString(br);
			String[] findLinks = findURL(mainPage,link);
//			for(int i = 0 ;i < findLinks.length;i++) {
//				System.out.println(findLinks[i]);
//			}
			toFile(findLinks, "hasil.txt");
			for(int i = 0;i < findLinks.length;i++) {
				br = read(findLinks[i]);
				String[] words = generateWord(br);
//				for(int j = 0;j < words.length;j++) {
//					System.out.println(words[j]);
//					for(int k = 0;k < words[j].length();k++) {
//						System.out.print(k);
//					}
//					System.out.println();
//				}
				for(int j = 0;j < words.length;j++) {
					Node nw = new Node(words[j],findLinks[i]);
					int place = generateHash(words[j]);
//					System.out.println(nw.getKata());
					list[place].append(nw);
				}
//				break;
			}
			System.out.println();
			System.out.println();
			
			for(int i = 0;i < list.length;i++) {
				list[i].print();
			}
			System.out.println();
			System.out.println();
			search();
			System.out.println("Done");
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	public void toFile(String[] data,String fileName) {
		try {
			FileWriter fw = new FileWriter(fileName);
			for(int i = 0;i < data.length;i++) {
				fw.write(data[i]+"\n");
			}
			fw.close();
		}catch (Exception e) {
			// TODO: handle exception
		}
	}
	public static void main(String[] args) {
		SimpleSearchEngine app = new SimpleSearchEngine("https://www.msn.com/id-id");
    }
	public boolean isAda(String[] link,String current,int length) {
		for(int i = 0;i < length;i++) {
			if(link[i].equals(current)) {
				return true;
			}
		}
		return false;
	}
}
