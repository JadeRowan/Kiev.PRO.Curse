package ua.kiev.prog.services;

import ua.kiev.prog.model.Contact;
import ua.kiev.prog.model.ContactGroup;

import java.util.List;

public interface ContactService {
    void addContact(Contact contact);
    void addGroup(ContactGroup contactGroup);
    void deleteContact(long[] ids);
    List<ContactGroup> listGroups();
    List<Contact> listContacts(ContactGroup contactGroup, int start, int count);
    List<Contact> listContacts(ContactGroup contactGroup);
    long count();
    ContactGroup findGroup(long id);
    List<Contact> searchContacts(String pattern);
}
