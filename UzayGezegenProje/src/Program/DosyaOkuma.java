/** 
* 
* @author Nargiza Zhyrgalbek Kyzy nargiza.kyzy@ogr.sakarya.edu.tr 
* @since 26.04.2025
* <p> 
*  Dosya Okuma Sınıfı
* </p> 
*/
package Program;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class DosyaOkuma {
	public static Map<String, Gezegen> gezegenleriOku(String dosyaYolu) {
		Map<String, Gezegen> gezegenler = new TreeMap<>();
		List<String> hataliSatirlar = new ArrayList<>();

		try (BufferedReader okuyucu = Files.newBufferedReader(Paths.get(dosyaYolu), StandardCharsets.UTF_8)) {
			String satir;
			while ((satir = okuyucu.readLine()) != null) {
				String[] parcalar = satir.split("#");
				if (parcalar.length == 3) {
					try {
						String isim = parcalar[0].trim();
						int gunlukSaat = Integer.parseInt(parcalar[1].trim());
						String tarih = parcalar[2].trim();

						gezegenler.put(isim, new Gezegen(isim, gunlukSaat, tarih));
					} catch (DateTimeParseException | IllegalArgumentException e) {
						hataliSatirlar.add("Format hatasi: " + satir);
					}
				} else {
					hataliSatirlar.add("Eksik veri: " + satir);
				}
			}
		} catch (IOException e) {
			System.err.println("Gezegen dosyasi okunurken hata olustu: " + e.getMessage());
		}

		System.out.println("Toplam okunan gezegen sayisi: " + gezegenler.size());

		return gezegenler;
	}

	public static List<UzayAraci> uzayAraclariniOku(String dosyaYolu) {
		List<UzayAraci> uzayAraclari = new ArrayList<>();
		List<String> hataliSatirlar = new ArrayList<>();

		try (BufferedReader okuyucu = Files.newBufferedReader(Paths.get(dosyaYolu), StandardCharsets.UTF_8)) {
			String satir;
			while ((satir = okuyucu.readLine()) != null) {
				String[] parcalar = satir.split("#");
				if (parcalar.length == 5) {
					try {
						String isim = parcalar[0].trim();
						String cikisGezegeni = parcalar[1].trim();
						String varisGezegeni = parcalar[2].trim();
						String cikisTarihi = parcalar[3].trim();
						double mesafeSaat = Double.parseDouble(parcalar[4].trim());

						uzayAraclari.add(new UzayAraci(isim, cikisGezegeni, varisGezegeni, cikisTarihi, mesafeSaat));
					} catch (NumberFormatException e) {
						hataliSatirlar.add("Sayisal format hatasi: " + satir);
					}
				} else {
					hataliSatirlar.add("Eksik veri: " + satir);
				}
			}
		} catch (IOException e) {
			System.err.println("Uzay araclari dosyasi okunurken hata olustu: " + e.getMessage());
		}

		System.out.println("Toplam okunan uzay araci: " + uzayAraclari.size());

		return uzayAraclari;
	}

	public static List<Kisi> kisileriOku(String dosyaYolu) {
		List<Kisi> kisiler = new ArrayList<>();
		List<String> hataliSatirlar = new ArrayList<>();

		try (BufferedReader okuyucu = Files.newBufferedReader(Paths.get(dosyaYolu), StandardCharsets.UTF_8)) {
			String satir;
			while ((satir = okuyucu.readLine()) != null) {
				String[] parcalar = satir.split("#");
				if (parcalar.length == 4) {
					try {
						String isim = parcalar[0].trim();
						int yas = Integer.parseInt(parcalar[1].trim());
						int kalanOmur = Integer.parseInt(parcalar[2].trim());
						String uzayAraciIsmi = parcalar[3].trim();

						kisiler.add(new Kisi(isim, yas, kalanOmur, uzayAraciIsmi));
					} catch (NumberFormatException e) {
						hataliSatirlar.add("Sayisal donusum hatasi: " + satir);
					}
				} else {
					hataliSatirlar.add("Eksik ya da fazla veri: " + satir);
				}
			}
		} catch (IOException e) {
			System.err.println("Kisiler dosyasi okunurken hata olustu: " + e.getMessage());
		}

		System.out.println("Toplam okunan kisi sayisi: " + kisiler.size());

		return kisiler;
	}

}
