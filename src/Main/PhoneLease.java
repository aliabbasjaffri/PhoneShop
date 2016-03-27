package Main;

import javax.xml.bind.annotation.*;
import javax.xml.datatype.XMLGregorianCalendar;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PhoneLease", propOrder = {
        "receiptID",
        "customerID",
        "salesManID",
        "phoneID",
        "terms",
        "leaseRate",
        "pricePerTerm",
        "contractStartDate"
})
public class PhoneLease
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
    protected String terms;
    @XmlElement(required = true)
    protected String leaseRate;
    @XmlElement(required = true)
    protected String pricePerTerm;
    @XmlElement(required = true)
    @XmlSchemaType(name = "startdate")
    protected XMLGregorianCalendar contractStartDate;

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

    public String getTerms() {
        return terms;
    }

    public void setTerms(String terms) {
        this.terms = terms;
    }

    public String getLeaseRate() {
        return leaseRate;
    }

    public void setLeaseRate(String leaseRate) {
        this.leaseRate = leaseRate;
    }

    public String getPricePerTerm() {
        return pricePerTerm;
    }

    public void setPricePerTerm(String pricePerTerm) {
        this.pricePerTerm = pricePerTerm;
    }

    public XMLGregorianCalendar getContractStartDate() {
        return contractStartDate;
    }

    public void setContractStartDate(XMLGregorianCalendar contractStartDate) {
        this.contractStartDate = contractStartDate;
    }
}
