package PhoneShop;

import javax.xml.bind.annotation.*;
import java.util.*;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PhoneShop", propOrder = {
        "shopName",
        "address",
        "contactNumber",
        "phones",
        "customers",
        "salesmen",
        "phonesLeased",
        "phonesSold"
})
public class PhoneShop
{
    @XmlElement(required = true)
    protected String shopName;
    @XmlElement(required = true)
    protected String address;
    @XmlElement(required = true)
    protected String contactNumber;
    @XmlElement(name = "Phone", required = true)
    protected List<Phone> phones;
    @XmlElement(name = "Customer", required = true)
    protected List<Customer> customers;
    @XmlElement(name = "Salesman", required = true)
    protected List<Salesman> salesmen;
    @XmlElement(name = "PhoneLease", required = true)
    protected List<PhoneLease> phonesLeased;
    @XmlElement(name = "PhoneSale", required = true)
    protected List<PhoneSale> phonesSold;

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public List<Phone> getPhones() {
        if( phones == null )
            phones = new ArrayList<Phone>();
        return phones;
    }

    public void setPhones(List<Phone> phones) {
        this.phones = phones;
    }

    public List<Customer> getCustomers() {
        if( customers == null )
            customers = new ArrayList<Customer>();
        return customers;
    }

    public void setCustomers(List<Customer> customers) {
        this.customers = customers;
    }

    public List<Salesman> getSalesmen() {
        if( salesmen == null )
            salesmen = new ArrayList<Salesman>();
        return salesmen;
    }

    public void setSalesmen(List<Salesman> salesmen) {
        this.salesmen = salesmen;
    }

    public List<PhoneLease> getPhonesLeased() {
        if( phonesLeased == null )
            phonesLeased = new ArrayList<PhoneLease>();
        return phonesLeased;
    }

    public void setPhonesLeased(List<PhoneLease> phonesLeased) {
        this.phonesLeased = phonesLeased;
    }

    public List<PhoneSale> getPhonesSold() {
        if( phonesSold == null )
            phonesSold = new ArrayList<PhoneSale>();
        return phonesSold;
    }

    public void setPhonesSold(List<PhoneSale> phonesSold) {
        this.phonesSold = phonesSold;
    }
}
