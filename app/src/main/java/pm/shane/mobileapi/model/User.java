package pm.shane.mobileapi.model;

/**
 * Created by Shane on 12/05/2017.
 */

public class User {

    private CustomerType customerType;
    private String customerName;

    public enum CustomerType {
        PRE_PAY, POST_PAY
    }

    public User(CustomerType customerType, String customerName) {
        this.customerType = customerType;
        this.customerName = customerName;
    }

    public CustomerType getType() {
        return customerType;
    }

    public String getName() {
        return customerName;
    }

}
