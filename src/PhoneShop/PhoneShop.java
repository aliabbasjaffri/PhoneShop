package PhoneShop;

import javax.xml.bind.annotation.*;
import java.util.List;

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
    @XmlElement(required = true)
    protected List<Phone> phones;
    @XmlElement(required = true)
    protected List<Customer> customers;
    @XmlElement(required = true)
    protected List<Salesman> salesmen;
    @XmlElement(required = true)
    protected List<PhoneLease> phonesLeased;
    @XmlElement(required = true)
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
        return phones;
    }

    public void setPhones(List<Phone> phones) {
        this.phones = phones;
    }

    public List<Customer> getCustomers() {
        return customers;
    }

    public void setCustomers(List<Customer> customers) {
        this.customers = customers;
    }

    public List<Salesman> getSalesmen() {
        return salesmen;
    }

    public void setSalesmen(List<Salesman> salesmen) {
        this.salesmen = salesmen;
    }

    public List<PhoneLease> getPhonesLeased() {
        return phonesLeased;
    }

    public void setPhonesLeased(List<PhoneLease> phonesLeased) {
        this.phonesLeased = phonesLeased;
    }

    public List<PhoneSale> getPhonesSold() {
        return phonesSold;
    }

    public void setPhonesSold(List<PhoneSale> phonesSold) {
        this.phonesSold = phonesSold;
    }
}
