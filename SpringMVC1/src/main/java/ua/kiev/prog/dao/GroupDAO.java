package ua.kiev.prog.dao;

import ua.kiev.prog.model.ContactGroup;

import java.util.List;

public interface GroupDAO {
    void add(ContactGroup contactGroup);
    void delete(ContactGroup contactGroup);
    ContactGroup findOne(long id);
    List<ContactGroup> list();
}
