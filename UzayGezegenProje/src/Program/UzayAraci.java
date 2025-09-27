/** 
* 
* @author Nargiza Zhyrgalbek Kyzy nargiza.kyzy@ogr.sakarya.edu.tr
* @since 26.04.2025
* <p> 
* 	Uzay Araci
* </p> 
*/
package Program;
import java.text.ParseException;
import java.util.List;

public class UzayAraci {
	private String ad;
	private String kalkisGezegeni;
	private String varisGezegeni;
	private String kalkisTarihi;
	private double mesafeSaatCinsinden;
	private double kalanSaat;
	private String durum;
	private String tahminiVarisTarihi;
	private boolean imhaEdildi;

	public UzayAraci(String ad, String kalkisGezegeni, String varisGezegeni, String kalkisTarihi,
			double mesafeSaatCinsinden) {
		this.ad = ad;
		this.kalkisGezegeni = kalkisGezegeni;
		this.varisGezegeni = varisGezegeni;
		this.kalkisTarihi = kalkisTarihi;
		this.mesafeSaatCinsinden = mesafeSaatCinsinden;
		this.kalanSaat = mesafeSaatCinsinden;
		this.durum = "Bekliyor";
		this.tahminiVarisTarihi = "Hesaplaniyor";
		this.imhaEdildi = false;
	}

	public void durumuGuncelle(Gezegen kalkisGezegen, Gezegen varisGezegen) throws ParseException {
		if (imhaEdildi) {
			durum = "IMHA";
			return;
		}

		if (tahminiVarisTarihi.equals("Hesaplaniyor")) {
			ilkTahminiVarisTarihiniHesapla(kalkisGezegen, varisGezegen);
		}

		if (durum.equals("Bekliyor") && this.kalkisGezegeni.equals(kalkisGezegen.getGezegenAdi())
				&& kalkisGezegen.tarihAyniMi(kalkisTarihi)) {
			durum = "Yolda";
		}
	}

	public void yolculukEt(int saat) {
		if (imhaEdildi)
			return;

		if (durum.equals("Yolda")) {
			kalanSaat -= saat;
			if (kalanSaat <= 0) {
				kalanSaat = 0;
				durum = "Vardi";
			}
		}
	}

	public void ekipDurumunuKontrolEt(List<Kisi> kisiler) {
		boolean biriHayatta = false;

		for (Kisi kisi : kisiler) {
			if (kisi.getUzayAraciAdi().equals(this.ad) && kisi.isHayattaMi()) {
				biriHayatta = true;
				break;
			}
		}

		if (!biriHayatta) {
			imhaEdildi = true;
			durum = "IMHA";
		}
	}

	public void ilkTahminiVarisTarihiniHesapla(Gezegen kalkisGezegen, Gezegen varisGezegen) throws ParseException {
		String varisTarihi = ZamanHesaplama.tamYolculukTarihiniHesapla(kalkisGezegen.mevcutTarihiYaziOlarakGetir(),
				this.kalkisTarihi, kalkisGezegen.getGunlukSaatSayisi(), this.mesafeSaatCinsinden,
				varisGezegen.mevcutTarihiYaziOlarakGetir(), varisGezegen.getGunlukSaatSayisi());

		this.tahminiVarisTarihi = varisTarihi;
	}

	public String getMevcutKonum() {
		switch (durum) {
		case "Bekliyor":
			return kalkisGezegeni;
		case "Vardi":
			return varisGezegeni;
		default:
			return "Yolda";
		}
	}

	// Getter metodları
	public String getAd() {
		return ad;
	}

	public String getKalkisGezegeni() {
		return kalkisGezegeni;
	}

	public String getVarisGezegeni() {
		return varisGezegeni;
	}

	public String getKalkisTarihi() {
		return kalkisTarihi;
	}

	public double getMesafeSaatCinsinden() {
		return mesafeSaatCinsinden;
	}

	public double getKalanSaat() {
		return kalanSaat;
	}

	public String getDurum() {
		return durum;
	}

	public boolean varisGerceklestiMi() {
		return durum.equals("Vardi") || imhaEdildi;
	}

	public String getTahminiVarisTarihi() {
		return imhaEdildi ? "IMHA" : tahminiVarisTarihi;
	}

	public boolean imhaDurumu() {
		return imhaEdildi;
	}
}
