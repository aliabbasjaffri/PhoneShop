package PhoneShop;

import javax.xml.bind.annotation.*;
import javax.xml.datatype.XMLGregorianCalendar;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PhoneSale", propOrder = {
        "receiptID",
        "customerID",
        "salesManID",
        "phoneID",
        "saleDate"
})
public class PhoneSale
{
    @XmlElement(required = true)
    protected String receiptID;
    @XmlElement(required = true)
    protected String customerID;
    @XmlElement(required = true)
    protected String salesManID;
    @XmlElement(required = true)
    protected String phoneID;
    @XmlElement(required = true)
    @XmlSchemaType(name = "saledate")
    protected XMLGregorianCalendar saleDate;

    public String getReceiptID() {
        return receiptID;
    }

    public void setReceiptID(String receiptID) {
        this.receiptID = receiptID;
    }

    public String getCustomerID() {
        return customerID;
    }

    public void setCustomerID(String customerID) {
        this.customerID = customerID;
    }

    public String getSalesManID() {
        return salesManID;
    }

    public void setSalesManID(String salesManID) {
        this.salesManID = salesManID;
    }

    public String getPhoneID() {
        return phoneID;
    }

    public void setPhoneID(String phoneID) {
        this.phoneID = phoneID;
    }

    public XMLGregorianCalendar getSaleDate() {
        return saleDate;
    }

    public void setSaleDate(XMLGregorianCalendar saleDate) {
        this.saleDate = saleDate;
    }
}
