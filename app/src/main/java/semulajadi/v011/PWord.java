package semulajadi.v011;

/**
 * Created by Ryan on 03/04/2018.
 */

public class PWord {
    int pricePackage;
    String namePackage, photoPackage, idPackage, detailPackage;

    public PWord(String idPackage, int pricePackage, String namePackage, String photoPackage, String detailPackage) {
        this.idPackage = idPackage;
        this.pricePackage = pricePackage;
        this.namePackage = namePackage;
        this.photoPackage = photoPackage;
        this.detailPackage = detailPackage;
    }

    public String getIdPackage() {
        return idPackage;
    }

    public void setIdPackage(String idPackage) {
        this.idPackage = idPackage;
    }

    public int getPricePackage() {
        return pricePackage;
    }

    public void setPricePackage(int pricePackage) {
        this.pricePackage = pricePackage;
    }

    public String getNamePackage() {
        return namePackage;
    }

    public void setNamePackage(String namePackage) {
        this.namePackage = namePackage;
    }

    public String getPhotoPackage() { return photoPackage;}

    public void setPhotoPackage(String photoPackage) {
        this.photoPackage = photoPackage;
    }

    public String getDetailPackage() { return detailPackage;}

    public void setDetailPackage(String detailPackage) {this.detailPackage = detailPackage; }
}
