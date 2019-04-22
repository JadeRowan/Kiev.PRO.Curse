package ua.kiev.prog.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ua.kiev.prog.model.Contact;
import ua.kiev.prog.model.ContactGroup;
import ua.kiev.prog.services.ContactService;

import javax.annotation.PostConstruct;

@Component
public class TestDataBean {
    @Autowired
    private ContactService contactService;

    @PostConstruct
    public void fillData() {

        ContactGroup contactGroup = new ContactGroup("Test");
        Contact contact;
        System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        contactService.addGroup(contactGroup);
        System.out.println("---------------------------------_________________________________------------------------------");
        for (int i = 0; i < 25; i++) {
            contact = new Contact(null, "Name" + i, "Surname" + i, "1234567" + i, "user" + i + "@test.com");
            contactService.addContact(contact);
        }
        for (int i = 0; i < 12; i++) {
            contact = new Contact(contactGroup, "Other" + i, "OtherSurname" + i, "7654321" + i, "user" + i + "@other.com");
            contactService.addContact(contact);
        }
    }
}
