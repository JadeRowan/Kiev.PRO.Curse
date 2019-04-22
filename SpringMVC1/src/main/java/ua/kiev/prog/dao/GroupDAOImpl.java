package ua.kiev.prog.dao;

import org.springframework.stereotype.Repository;
import ua.kiev.prog.model.ContactGroup;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class GroupDAOImpl implements GroupDAO {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void add(ContactGroup contactGroup) {
        entityManager.persist(contactGroup);
    }

    @Override
    public void delete(ContactGroup contactGroup) {
        entityManager.remove(contactGroup);
    }

    @Override
    public ContactGroup findOne(long id) {
        return entityManager.getReference(ContactGroup.class, id);
    }

    @Override
    public List<ContactGroup> list() {
        TypedQuery<ContactGroup> query = entityManager.createQuery("SELECT g FROM Group g", ContactGroup.class);
        return query.getResultList();
    }
}
