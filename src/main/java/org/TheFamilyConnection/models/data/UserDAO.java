package org.TheFamilyConnection.models.data;

import org.hibernate.annotations.OnDeleteAction;
import org.TheFamilyConnection.models.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface UserDAO extends CrudRepository<User, Integer> {
}
