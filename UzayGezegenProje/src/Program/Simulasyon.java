/** 
* 
* @author Nargiza Zhyrgalbek Kyzy nargiza.kyzy@ogr.sakarya.edu.tr 
* @since 26.04.2025
* <p> 
*  Main Class diğer sınıfları burada kullanıyoruz.
* </p> 
*/ 
package Program;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

public class Simulasyon {
	public static void main(String[] args) throws ParseException, InterruptedException {

		// Dosyaları oku
		Map<String, Gezegen> gezegenler = DosyaOkuma.gezegenleriOku("Gezegenler.txt");
		List<Kisi> kisiler = DosyaOkuma.kisileriOku("Kisiler.txt");
		List<UzayAraci> araclar = DosyaOkuma.uzayAraclariniOku("Araclar.txt");
		System.out.println("Dosyalar okundu.");

		int iterasyon = 0;
		boolean hepsiVardiMi = false;

		while (!hepsiVardiMi) {
			iterasyon++;

			ekranTemizle();

			// Gezegen zamanını ilerlet
			for (Gezegen gezegen : gezegenler.values()) {
				gezegen.zamaniIleriAl(1); // Her iterasyonda 1 saat ilerle
			}

			// Kişi zamanlarını ilerlet
			for (Kisi kisi : kisiler) {
				kisi.zamanGecir(1);
			}

			for (UzayAraci arac : araclar) {

				// Mürettebat durumu kontrol et
				arac.ekipDurumunuKontrolEt(kisiler);

				// Çıkış ve varış gezegenlerini al
				Gezegen cikis = gezegenler.get(arac.getKalkisGezegeni());
				Gezegen varis = gezegenler.get(arac.getVarisGezegeni());

				// Arac durumunu güncelle
				arac.durumuGuncelle(cikis, varis);

				// Yolculuk süresi ilerlet
				arac.yolculukEt(1);
			}

			// Bilgileri göster
			bilgileriGoster(iterasyon, gezegenler, araclar, kisiler);

			// Tüm araçlar hedefe ulaştı mı?
			hepsiVardiMi = hepsiVardiMi(araclar);

			Thread.sleep(1);
		}

		// Simülasyon bittiğinde son durumu göster
		bilgileriGoster(iterasyon, gezegenler, araclar, kisiler);
		System.out.println("\n---------------------------------------------------------");
		System.out.println("Tum uzay araclari hedeflerine ulasti. Simulasyon sona erdi.");
	}

	private static void bilgileriGoster(int iterasyon, Map<String, Gezegen> gezegenler, List<UzayAraci> araclar,
			List<Kisi> kisiler) {

		System.out.println("\nToplam Gecen Saat: " + iterasyon);

		int gezegenAlanGenisligi = 16;
		int tarihAlanGenisligi = 16;

		System.out.print("\nGezegenler:\t");
		for (Gezegen gezegen : gezegenler.values()) {
			String ad = gezegen.toString();
			String formatli = "" + merkezle(ad, 3) + "";
			System.out.printf("%-" + gezegenAlanGenisligi + "s", formatli);
		}
		System.out.println();

		System.out.print("Tarih: \t\t");
		for (Gezegen gezegen : gezegenler.values()) {
			String tarih = gezegen.mevcutTarihiYaziOlarakGetir();
			System.out.printf("%-" + tarihAlanGenisligi + "s", tarih);
		}
		System.out.println();

		System.out.print("Nufus: \t\t");
		for (Gezegen gezegen : gezegenler.values()) {
			int nufus = gezegenNufusHesapla(gezegen.getGezegenAdi(), araclar, kisiler);
			System.out.printf("%-" + tarihAlanGenisligi + "d", nufus);
		}

		System.out.println("\n\n");

		System.out.println("Uzay Araclari:");
		System.out.println("Arac Adi \tDurum\t\tCikis\t\tVaris\t\tHedefe Kalan Saat\t\tHedefe Varacagi Tarih");
		for (UzayAraci arac : araclar) {
			StringBuilder sb = new StringBuilder();
			sb.append(String.format("%-15s\t", arac.getAd()));
			sb.append(String.format("%-10s\t", arac.getDurum()));
			sb.append(String.format("%-15s\t", arac.getKalkisGezegeni()));
			sb.append(String.format("%-15s\t", arac.getVarisGezegeni()));
			sb.append(String.format("%-25s\t", arac.imhaDurumu() ? "--" : String.format("%.0f", arac.getKalanSaat())));
			sb.append(String.format("%-50s\t", arac.imhaDurumu() ? "--" : arac.getTahminiVarisTarihi()));

			System.out.println(sb.toString());
		}
	}

	private static int gezegenNufusHesapla(String gezegenAdi, List<UzayAraci> araclar, List<Kisi> kisiler) {
		int nufus = 0;
		for (Kisi kisi : kisiler) {
			if (!kisi.isHayattaMi()) {
				continue;
			}

			String aracAdi = kisi.getUzayAraciAdi();
			UzayAraci arac = araciBul(aracAdi, araclar);

			if (arac != null && !arac.imhaDurumu()) {
				String konum = arac.getMevcutKonum();
				if (konum.equals(gezegenAdi)) {
					nufus++;
				}
			}
		}
		return nufus;
	}

	private static UzayAraci araciBul(String ad, List<UzayAraci> araclar) {
		for (UzayAraci arac : araclar) {
			if (arac.getAd().equals(ad)) {
				return arac;
			}
		}
		return null;
	}

	private static boolean hepsiVardiMi(List<UzayAraci> araclar) {
		for (UzayAraci arac : araclar) {
			if (!arac.varisGerceklestiMi()) {
				return false;
			}
		}
		return true;
	}

	private static void ekranTemizle() {
		try {
			String os = System.getProperty("os.name").toLowerCase();
			ProcessBuilder processBuilder;
			if (os.contains("win")) {
				processBuilder = new ProcessBuilder("cmd", "/c", "cls");
			} else {
				processBuilder = new ProcessBuilder("clear");
			}
			processBuilder.inheritIO().start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Verilen metni, belirtilen genişlikte ortalar.
	public static String merkezle(String s, int genislik) {
		if (s == null || s.length() >= genislik)
			return s;
		int sol = (genislik - s.length()) / 2;
		int sag = genislik - s.length() - sol;

		StringBuilder sb = new StringBuilder();
		sb.append(String.format("%" + sol + "s", "")); // Sol boşluk
		sb.append(s);
		sb.append(String.format("%" + sag + "s", "")); // Sağ boşluk

		return sb.toString();
	}
}
