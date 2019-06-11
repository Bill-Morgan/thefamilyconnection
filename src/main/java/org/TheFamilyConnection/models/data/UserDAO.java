package org.TheFamilyConnection.models.data;

import com.sun.org.apache.xpath.internal.operations.Bool;
import org.TheFamilyConnection.models.User;
import org.springframework.cache.support.NullValue;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

@Repository
@Transactional
public interface UserDAO extends CrudRepository<User, Integer> {
    List<User> findByActive(Boolean isActive);
    List<User> findByFatherOrMotherOrSpouse(User father, User mother, User spouse);
    User findFirstByPrimaryEmailAndPassword(String username, String password);
}
