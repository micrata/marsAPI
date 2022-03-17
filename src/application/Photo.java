package application;

/**
 * Collection of object-oriented aspects of java
 */
public class Photo {

    private String id;

    private String sol;

    private String cameraId;

    private String cameraName;

    private String roverId;

    private String imgSrc;

    private String date;

    private String roverName;

    private String roverLandingD;

    private String roverLaunchD;

    private String roverStatus;


    public Photo(){

    }

    /**
     * Sets instance variable "id" to @param id.
     */
    public void setId(String id) {
        this.id = id;
    }
    /**
     * Sets instance variable "sol" to @param sol.
     */
    public void setSol(String sol) {
        this.sol = sol;
    }
    /**
     * Sets instance variable "cameraID" to @param cameraID.
     */
    public void setCameraId(String cameraId) {
        this.cameraId = cameraId;
    }
    /**
     * Sets instance variable "cameraName" to @param cameraName.
     */
    public void setCameraName(String cameraName) {
        this.cameraName = cameraName;
    }
    /**
     * Sets instance variable "roverID" to @param roverID.
     */
    public void setRoverId(String roverId) {
        this.roverId = roverId;
    }
    /**
     * Sets instance variable "setDate" to @param setDate.
     */
    public void setDate(String date) {
        this.date = date;
    }
    /**
     * Sets instance variable "setLaunchD" to @param setLaunchD.
     */
    public void setLaunchD(String lD) {
        this.roverLaunchD = lD;
    }
    /**
     * Sets instance variable "setLandD" to @param setLandD.
     */
    public void setLandD(String ld) {
        this.roverLandingD = ld;
    }
    /**
     * Sets instance variable "setRoverStatus" to @param setRoverStatus.
     */
    public void setRoverStatus(String roverS) {
        this.roverStatus = roverS;
    }
    /**
     * Sets instance variable "setImgSrc" to @param setImgSrc.
     */
    public void setImgSrc(String imgSrc) {
        this.imgSrc = imgSrc;
    }
    /**
     * Sets instance variable "setRoverName" to @param setRoverName.
     */
    public void setRoverName(String roverName) {
        this.roverName = roverName;
    }
    /**
     * Returns instance variable "getId" to @return getId.
     */
    public String getId() {
        return id;
    }
    /**
     * Returns instance variable "getSol" to @return getSol.
     */
    public String getSol() {
        return sol;
    }
    /**
     * Returns instance variable "getCameraID" to @return getCameraID.
     */
    public String getCameraId() {
        return cameraId;
    }
    /**
     * Returns instance variable "getCameraName" to @return getCameraName.
     */
    public String getCameraName() {
        return cameraName;
    }
    /**
     * Returns instance variable "getRoverId" to @return getRoverId.
     */
    public String getRoverId() {
        return roverId;
    }
    /**
     * Returns instance variable "getDate" to @return getDate.
     */
    public String getDate() {
        return date;
    }
    /**
     * Returns instance variable "getLaunchD" to @return getLaunchD.
     */
    public String getLaunchD() {
        return roverLaunchD;
    }
    /**
     * Returns instance variable "getLandD" to @return getLandD.
     */
    public String getLandD() {
        return roverLandingD;
    }
    /**
     * Returns instance variable "getRoverStatus" to @return getRoverStatus.
     */
    public String getRoverStatus() {
        return roverStatus;
    }
    /**
     * Returns instance variable "getImgSrc" to @return getImgSrc.
     */
    public String getImgSrc() {
        return imgSrc;
    }
    /**
     * Returns instance variable "getRoverName" to @return getRoverName.
     */
    public String getRoverName() {
        return roverName;
    }

    public String toString() {

        return "ID: "+getId()+" Src: "+getImgSrc()+"\n";

    }
}