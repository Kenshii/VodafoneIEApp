package pm.shane.mobileapi.providers.vodafone.response.login;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoginResponseData {

    @SerializedName("custname")
    @Expose
    private String custName;
    @SerializedName("custtypecode")
    @Expose
    private String custTypeCode;
    @SerializedName("insecurepassword")
    @Expose
    private Boolean insecurePassword;
    @SerializedName("LID")
    @Expose
    private String lID;
    @SerializedName("promowinner")
    @Expose
    private String promoWinner;

    public String getCustName() {
        return custName;
    }

    public String getCustTypeCode() {
        return custTypeCode;
    }

    public Boolean isInsecurePassword() {
        return insecurePassword;
    }

    public String getLID() {
        return lID;
    }

    public String getPromoWinner() {
        return promoWinner;
    }
}