package Main;

import javax.xml.bind.annotation.*;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Phone", propOrder = {
        "phoneID",
        "IMEI",
        "make",
        "model",
        "makeYear",
        "price",
        "available",
        "type"
})
public class Phone
{
    @XmlElement(required = true)
    protected String phoneID;
    @XmlElement(required = true)
    protected String IMEI;
    @XmlElement(required = true)
    protected String make;
    @XmlElement(required = true)
    protected String model;
    @XmlElement(required = true)
    protected String makeYear;
    @XmlElement(required = true)
    protected String price;
    @XmlElement(required = true)
    protected String available;
    @XmlElement(required = true)
    protected String type;

    public String getPhoneID() {
        return phoneID;
    }

    public void setPhoneID(String PhoneID) {
        this.phoneID = PhoneID;
    }

    public String getIMEI() {
        return IMEI;
    }

    public void setIMEI(String IMEI) {
        this.IMEI = IMEI;
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getMakeYear() {
        return makeYear;
    }

    public void setMakeYear(String makeYear) {
        this.makeYear = makeYear;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getAvailable() {
        return available;
    }

    public void setAvailable(String available) {
        this.available = available;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName()
    {
        return getMake() + " " + getModel();
    }
}
