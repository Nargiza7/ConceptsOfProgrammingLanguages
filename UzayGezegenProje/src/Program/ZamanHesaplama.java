/** 
* 
* @author Nargiza Zhyrgalbek Kyzy nargiza.kyzy@ogr.sakarya.edu.tr 
* @since 26.04.2025
* <p> 
* 	Bir uzay aracının X gezegeninden Y gezegenine yaptığı yolculuk sırasında, 
*   bu sınıfta uzay aracının Y gezegenine varış tarihi hesaplanmaktadır..
* </p> 
*/
package Program;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class ZamanHesaplama {
	private static final DateTimeFormatter[] tarihFormat1 = new DateTimeFormatter[] {
			DateTimeFormatter.ofPattern("d.M.yyyy"), DateTimeFormatter.ofPattern("dd.MM.yyyy") };

	private static final DateTimeFormatter gosterimFormat = DateTimeFormatter.ofPattern("dd.MM.yyyy");

	private static LocalDate parseTarih(String tarih) {
		for (DateTimeFormatter formatter : tarihFormat1) {
			try {
				return LocalDate.parse(tarih, formatter);
			} catch (Exception e) {
				// Format uymazsa sıradakine geç
			}
		}
		throw new IllegalArgumentException("Gecersiz tarih formati: " + tarih);
	}

	public static int tarihFarkiSaatOlarak(String baslangicTarihi, String bitisTarihi, int gunlukSaatSayisi) {
		LocalDate t1 = parseTarih(baslangicTarihi);
		LocalDate t2 = parseTarih(bitisTarihi);
		long farkGun = ChronoUnit.DAYS.between(t1, t2);
		return (int) Math.abs(farkGun * gunlukSaatSayisi);
	}

	// Mesafeye göre varış tarihini hesaplar
	public static String varisTarihiniHesapla(String kalkisTarihi, int kalkisGunlukSaat, double mesafeSaatCinsinden,
			int varisGunlukSaat, String varisMevcutTarih) {
		LocalDate varisTarih = parseTarih(varisMevcutTarih);
		int toplamSaat = (int) Math.floor(mesafeSaatCinsinden);
		int eklenecekGun = toplamSaat / varisGunlukSaat;
		return varisTarih.plusDays(eklenecekGun).format(gosterimFormat);

	}

	// Kalkış tarihi, yolculuk süresi ve varış tarihine göre tam yolculuk varış
	// tarihini hesaplar.
	public static String tamYolculukTarihiniHesapla(String kalkisMevcutTarih, String uzayAraciKalkisTarihi,
			int kalkisGunlukSaat, double mesafeSaatCinsinden, String varisMevcutTarih, int varisGunlukSaat) {

		int beklemeSuresiSaat = tarihFarkiSaatOlarak(kalkisMevcutTarih, uzayAraciKalkisTarihi, kalkisGunlukSaat);
		int toplamYolculukSaati = beklemeSuresiSaat + (int) Math.floor(mesafeSaatCinsinden);
		int varisGunSayisi = toplamYolculukSaati / varisGunlukSaat;

		LocalDate varisTarih = parseTarih(varisMevcutTarih);
		return varisTarih.plusDays(varisGunSayisi).format(gosterimFormat);
	}
}
