package Main;

import javax.xml.bind.annotation.*;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Customer", propOrder = {
        "customerID",
        "city",
        "emailAddress",
        "mobileNumber",
        "receiptID"
})
public class Customer extends Person
{
    @XmlElement(required = true)
    protected String customerID;
    @XmlElement(required = true)
    protected String city;
    @XmlElement(required = true)
    protected String emailAddress;
    @XmlElement(required = true)
    protected String mobileNumber;
    @XmlElement(required = true)
    protected String receiptID;

    public String getCustomerID() {
        return customerID;
    }

    public void setCustomerID(String customerID) {
        this.customerID = customerID;
    }

    public String getCity() {
        return customerID;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getReceiptID() {
        return receiptID;
    }

    public void setReceiptID(String receiptID) {
        this.receiptID = receiptID;
    }
}
