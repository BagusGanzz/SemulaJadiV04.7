package semulajadi.v011;

/**
 * Created by Ryan on 18/11/2017.
 */

public class Word {
    private int idTourism, priceTourism;
    private String nameTourism, photoTourism, detailTourism, routeTourism, facilitiesTourism, locationTourism, operationHours, sumberTourism, routeGambar;
    private double longitude, latitude;

    public Word(int idTourism, int priceTourism, String nameTourism, String photoTourism, String detailTourism, String routeTourism, String facilitiesTourism, String locationTourism, String operationHours, String sumberTourism, String routeGambar, Double langitude, Double longitude) {
        this.idTourism = idTourism;
        this.priceTourism = priceTourism;
        this.nameTourism = nameTourism;
        this.photoTourism = photoTourism;
        this.detailTourism = detailTourism;
        this.routeTourism = routeTourism;
        this.facilitiesTourism = facilitiesTourism;
        this.locationTourism = locationTourism;
        this.operationHours = operationHours;
        this.sumberTourism = sumberTourism;
        this.routeGambar = routeGambar;

        this.latitude = langitude;
        this.longitude = longitude;
    }

    public int getIdTourism() {
        return idTourism;
    }

    public void setIdTourism(int idTourism) {
        this.idTourism = idTourism;
    }

    public int getPriceTourism() {
        return priceTourism;
    }

    public void setPriceTourism(int priceTourism) {
        this.priceTourism = priceTourism;
    }

    public String getNameTourism() {
        return nameTourism;
    }

    public void setNameTourism(String nameTourism) {
        this.nameTourism = nameTourism;
    }

    public String getPhotoTourism() {return photoTourism; }

    public void setPhotoTourism(String photoTourism) {
        this.photoTourism = photoTourism;
    }

    public String getDetailTourism() { return detailTourism; }

    public void getDetailTourism(String detailTourism) { this.detailTourism = detailTourism; }

    public String getRouteTourism() { return routeTourism; }

    public void getRouteTourism(String routeTourism) { this.routeTourism = routeTourism; }

    public String getFacilitiesTourism() { return facilitiesTourism; }

    public void getFacilitiesTourism(String facilitiesTourism) { this.facilitiesTourism = facilitiesTourism; }

    public String getOperationHours() { return operationHours; }

    public void getOperationHours(String operationHours) { this.operationHours = operationHours; }

    public String getLocationTourism() { return locationTourism; }

    public void getLocationTourism(String locationTourism) { this.locationTourism = locationTourism; }

    public String getSumberTourism() { return sumberTourism; }

    public void getSumberTourism(String sumberTourism) { this.sumberTourism = sumberTourism; }

    public String getRouteGambar() { return routeGambar; }

    public void getRouteGambar(String routeGambar) { this.routeGambar = routeGambar; }

    public Double getLangitude() { return latitude; }

    public void getLangitude(Double latitude) {this.latitude = latitude; }

    public Double getLongitude() { return longitude; }

    public void getLongitude(Double longitude) {this.longitude = longitude; }

}
