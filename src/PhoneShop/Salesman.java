package PhoneShop;

import javax.xml.bind.annotation.*;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Salesman", propOrder = {
        "salesManID",
        "department"
})
public class Salesman extends Person
{
    @XmlElement(required = true)
    protected String salesManID;
    @XmlElement(required = true)
    protected String department;

    public String getSalesManID() {
        return salesManID;
    }

    public void setSalesManID(String salesManID) {
        this.salesManID = salesManID;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }
}
