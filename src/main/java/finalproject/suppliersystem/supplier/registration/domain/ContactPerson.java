package finalproject.suppliersystem.supplier.registration.domain;

import finalproject.suppliersystem.core.auditing.Audition;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class ContactPerson extends Audition
{
    /**
     * ContactPerson has to have it's one id, because the supplier_id is unique,
     * but we need to be able to store two contact persons. So the supplier_id can't be PK in this table.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long contactPersonId;

    @Column(length = 45)
    @Size(max = 45, message = "Name must be between 0 and 45 characters")
    private String nameContactPerson;

    @Column(length = 30)
    @Size(max = 30, message = "Phone number must be between 0 and 30 characters")
    private String phoneNumberContactPerson;

    @Column(length = 100)
    @Size(max = 100, message = "Email must be between 0 and 100 characters")
    @Email(message = "Email should be valid")
    private String emailContactPerson;

    /**
     * Child (owner)
     * We have chosen to use an unidirectional relationship with ContactInformation
     * https://vladmihalcea.com/manytoone-jpa-hibernate/
     * https://discourse.hibernate.org/t/unidirectional-vs-bidirectional-manytoone/951
     */
    @ManyToOne (fetch = FetchType.LAZY)
    private ContactInformation contactInformation;

    /**
     * Can't rely on a natural identifier for equality checks, so instead we use the entity identifier.
     * The equality has to be consistent across all entity state transitions.
     *
     * To understand why to use equals and hashCode:
     * https://docs.jboss.org/hibernate/orm/5.4/userguide/html_single/Hibernate_User_Guide.html#mapping-model-pojo-equalshashcode
     * https://medium.com/@rajibrath20/the-best-way-to-map-a-onetomany-relationship-with-jpa-and-hibernate-dbbf6dba00d3
     * https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
     * https://vladmihalcea.com/the-best-way-to-map-a-onetomany-association-with-jpa-and-hibernate/
     * https://stackoverflow.com/questions/9560522/hibernate-how-to-properly-organize-relation-a-one-to-many-with-annotations
     *
     * @param object - the object to be compared
     * @return boolean
     */
    @Override
    public boolean equals(Object object)
    {
        if (this == object)
        {
            return true;
        }

        if (!(object instanceof ContactPerson))
        {
            return false;
        }

        return contactPersonId != null && contactPersonId.equals(((ContactPerson)object).getContactPersonId());
    }

    @Override
    public int hashCode()
    {
        return getClass().hashCode();
    }
}
