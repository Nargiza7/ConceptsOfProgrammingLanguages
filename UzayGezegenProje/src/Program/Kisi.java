/** 
* 
* @author Nargiza Zhyrgalbek Kyzy nargiza.kyzy@ogr.sakarya.edu.tr
* @since 26.04.2025
* <p> 
* 	Kisi Nesnesi
* </p> 
*/
package Program;

public class Kisi {
	private String isim;
	private int yas;
	private int kalanOmur;
	private String uzayAraci;
	private boolean hayattaMi;

	public Kisi(String isim, int yas, int kalanOmur, String uzayAraci) {
		this.isim = isim;
		this.yas = yas;
		this.kalanOmur = kalanOmur;
		this.uzayAraci = uzayAraci;

		this.hayattaMi = true;
	}

	public void zamanGecir(int saat) {
		if (hayattaMi) {
			kalanOmur -= saat;
			if (kalanOmur <= 0) {
				hayattaMi = false;
				kalanOmur = 0;
			}
		}
	}

	public String getIsim() {
		return isim;
	}

	public int getYas() {
		return yas;
	}

	public int getKalanOmur() {
		return kalanOmur;
	}

	public String getUzayAraciAdi() {
		return uzayAraci;
	}

	public boolean isHayattaMi() {
		return hayattaMi;
	}
}
