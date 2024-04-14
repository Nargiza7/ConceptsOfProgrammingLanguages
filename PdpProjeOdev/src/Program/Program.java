/**
*
* @author Nargiza Zhyrgalbek kyzy 
* nargiza.kyzy@ogr.sakarya.edu.tr
* @since 06.04.2024
* <p>
* Her bir sınıf dosyası için Javadoc ve yorum satırı sayıları, kod satırı sayısı,
*  LOC, sınıf içerisindeki fonksiyon sayısı ve yorum sapma yüzdesi hesaplanır..
* </p>
*/
package Program;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Program {

		    public static void main(String[] args) {
		    			// Kullanıcıdan GitHub deposu URL'sini al
		    	        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		    	        try {
		    	            System.out.print("GitHub Repository URL'sini giriniz: ");
		    	            String repoUrl = reader.readLine().trim();

		    	            // GitHub deposunu klonla
		    	            File clonedRepo = cloneRepository(repoUrl);

		    	            if (clonedRepo != null) {
		    	                // Klonlanmış depodaki *.java dosyalarını analiz et
		    	                analyzeJavaFiles(clonedRepo);
		    	            } else {
		    	                System.out.println("Failed to clone repository.");
		    	            }

		    	        } catch (IOException e) {
		    	            e.printStackTrace();
		    	        } finally {
		    	            try {
		    	                reader.close();
		    	            } catch (IOException e) {
		    	                e.printStackTrace();
		    	            }
		    	        }
		    	    }
		    		// Depoyu klonla ve klonlanmış dizini döndür
		    	    private static File cloneRepository(String repoUrl) {
		    	        try {
		    	        	// Geçici bir dizin oluştur
		    	            File tempDir = Files.createTempDirectory("tempRepo").toFile();

		    	            // Git deposunu klonla
		    	            Process process = Runtime.getRuntime().exec("git clone " + repoUrl + " " + tempDir.getAbsolutePath());
		    	            process.waitFor();

		    	            System.out.println("Repository cloned to: " + tempDir.getAbsolutePath());

		    	            return tempDir;
		    	        } catch (IOException | InterruptedException e) {
		    	            e.printStackTrace();
		    	            return null;
		    	        }
		    	    }
		    	    
		    	    // Java dosyalarını analiz et
		    	    private static void analyzeJavaFiles(File directory) {
		    	    	 
		    	        try {
		    	            Files.walk(Paths.get(directory.getAbsolutePath()))
		    	                    .filter(path -> path.toFile().isFile() && path.toString().endsWith(".java"))
		    	                    .forEach(path -> processJavaFile(path));
		    	        } catch (IOException e) {
		    	            e.printStackTrace();
		    	        }
		    	    }
		    	    // Java dosyasını işle
		    	    private static void processJavaFile(Path path) {
		    	        try {
		    	            String content = new String(Files.readAllBytes(path));
		    	            analyzeJavaFile(path.getFileName().toString(), content);
		    	        } catch (IOException e) {
		    	            e.printStackTrace();
		    	        }
		    	    }
		    	    
		    	    // Java dosyasını analiz et
		    	    private static void analyzeJavaFile(String fileName, String content) {
		    	      Pattern classPattern = Pattern.compile("(?s)/\\*\\*(.*?)\\*/.*\\s+([\\w]+)\\s*\\{(.*)\\}", Pattern.DOTALL);
		    	        Matcher classMatcher = classPattern.matcher(content);
		    	        
		    	        if (classMatcher.find()) {
		    	            String className = classMatcher.group(2);
		    	            String classContent = classMatcher.group(3);
		    	            int javadocLines = countLines(classMatcher.group(1));
		    	            int commentLines = countCommentLines(classContent);
		    	            int codeLines = countCodeLines(classContent);
		    	            int loc = countLines(classContent);
		    	            int functionCount = countFunctions(classContent);

		    	            double yorumSapmaYuzdesi = calculateYorumSapmaYuzdesi(javadocLines, commentLines, functionCount, loc);

		    	            //System.out.println("Analiz edilen dosya: " + fileName); 
		    	            System.out.println("Sinif: " + fileName );
		    	            System.out.println("Javadoc Satir Sayisi: " + javadocLines);
		    	            System.out.println("Yorum Satir Sayisi: " + commentLines);
		    	            System.out.println("Kod Satir Sayisi: " + codeLines);
		    	            System.out.println("LOC: " + loc);
		    	            System.out.println("Fonksiyon Sayisi: " + functionCount);
		    	            System.out.println("Yorum Sapma Yuzdesi: % " + yorumSapmaYuzdesi);
		    	            System.out.println("-----------------------------------------");
		    	        }
		    	    }

		    	    // Satır sayısını hesapla
		    	    private static int countLines(String content) {		    	 
		    	        return content.split("\n").length;
		    	    }  	    
		
		    	    // Yorum satırı sayısını hesapla
		    	    private static int countCommentLines(String content) {
		    	    	Pattern pattern = Pattern.compile("(?s)/\\*\\*.*?\\*/|//.*?$", Pattern.MULTILINE);
		    	        Matcher matcher = pattern.matcher(content);
		    	        int count = 0;
		    	        while (matcher.find()) {
		    	            count++;
		    	        }
		    	        return count;
		    	    }

		    	    // Kod satırı sayısını hesapla
		    	    private static int countCodeLines(String content) {
		    	        Pattern pattern = Pattern.compile("(?s)\\{([^\\}]*?)\\}");
		    	        Matcher matcher = pattern.matcher(content);
		    	        int count = 0;
		    	        while (matcher.find()) {
		    	            count += countLines(matcher.group(1));
		    	        }
		    	        return count;
		    	    }

		    	    // Fonksiyon sayısını hesapla
		    	    private static int countFunctions(String content) {
		    	        Pattern pattern = Pattern.compile("(?s)(?:(public|private|protected|static|)\\s+)?[\\w<>]+\\s+(\\w+)\\s*\\([^\\)]*\\)\\s*\\{");
		    	        Matcher matcher = pattern.matcher(content);
		    	        int count = 0;
		    	        while (matcher.find()) {
		    	            count++;
		    	        }
		    	        return count;
		    	    }

		    	    // Yorum sapma yüzdesini hesapla
		    	    private static double calculateYorumSapmaYuzdesi(int javadocLines, int commentLines, int functionCount, int loc) {
		    	        double YG = ((javadocLines + commentLines) * 0.8) / functionCount;
		    	        double YH = ((double) loc / functionCount) * 0.3;
		    	        return ((100 * YG) / YH) - 100;
		    	    }
		    	}

		    	
		    	
		    	
		    	
		    	
		    	
		    	
		    