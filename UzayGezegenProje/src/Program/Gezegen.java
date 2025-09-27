/** 
* 
* @author Nargiza Zhyrgalbek Kyzy nargiza.kyzy@ogr.sakarya.edu.tr 
* @since 26.04.2025
* <p> 
*  Gezegen
* </p> 
*/ 
package Program;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Gezegen {
	private String gezegenAdi;
	private int gunlukSaatSayisi;
	private LocalDate mevcutTarih;
	private static final DateTimeFormatter[] tarihFormatlari = new DateTimeFormatter[] {
			DateTimeFormatter.ofPattern("dd.MM.yyyy"), DateTimeFormatter.ofPattern("d.M.yyyy") };

	private static final DateTimeFormatter yazdirmaFormati = DateTimeFormatter.ofPattern("d.M.yyyy");

	private int birikmisSaat = 0;

	public Gezegen(String gezegenAdi, int gunlukSaatSayisi, String baslangicTarihi) {
		this.gezegenAdi = gezegenAdi;
		this.gunlukSaatSayisi = gunlukSaatSayisi;
		this.mevcutTarih = parseTarih(baslangicTarihi);
	}

	private static LocalDate parseTarih(String tarih) {
		for (DateTimeFormatter formatter : tarihFormatlari) {
			try {
				return LocalDate.parse(tarih, formatter);
			} catch (DateTimeParseException e) {
				// format uymazsa denemeye devam
			}
		}
		throw new IllegalArgumentException("Tarih formati gecersiz! Beklenen format: dd.MM.yyyy veya d.M.yyyy");
	}

	public void zamaniIleriAl(int saat) {
		birikmisSaat += saat;
		int eklenecekGun = birikmisSaat / gunlukSaatSayisi;
		birikmisSaat %= gunlukSaatSayisi;

		if (eklenecekGun > 0) {
			mevcutTarih = mevcutTarih.plusDays(eklenecekGun);
		}
	}

	public String getGezegenAdi() {
		return gezegenAdi;
	}

	public int getGunlukSaatSayisi() {
		return gunlukSaatSayisi;
	}

	public LocalDate getMevcutTarih() {
		return mevcutTarih;
	}

	public String mevcutTarihiYaziOlarakGetir() {
		return mevcutTarih.format(yazdirmaFormati);
	}

	@Override
	public String toString() {
		return "--- " + gezegenAdi + " ---";
	}

	public boolean tarihAyniMi(String digerTarihStr) {
		try {
			LocalDate digerTarih = LocalDate.parse(digerTarihStr, yazdirmaFormati);
			return mevcutTarih.isEqual(digerTarih);
		} catch (DateTimeParseException e) {
			throw new IllegalArgumentException("Karsilastirilacak tarih formati gecersiz!");
		}
	}

	public String suGunSonrasiTarih(int kacGunSonra) {
		LocalDate yeniTarih = mevcutTarih.plusDays(kacGunSonra);
		return yeniTarih.format(yazdirmaFormati);
	}

	public String suSaatSonrasiTarih(int kacSaatSonra) {
		int kacGunSonra = kacSaatSonra / gunlukSaatSayisi;
		return suGunSonrasiTarih(kacGunSonra);
	}
}
