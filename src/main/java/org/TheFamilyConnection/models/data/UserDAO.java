package org.TheFamilyConnection.models.data;

import org.TheFamilyConnection.models.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public interface UserDAO extends CrudRepository<User, Integer> {
    List<User> findByActiveIsTrue();
    List<User> findByActiveIsTrueAndDobNotNull();
    List<User> findByFatherOrMotherOrSpouse(User father, User mother, User spouse);
    User findFirstByActiveIsTrueAndPrimaryEmailAndPassword(String username, String password);
    User findFirstByPrimaryEmail(String username);
    List<User> findByActiveIsTrueAndSpouseNotNullAndAnniversaryNotNull();
    List<User> findBySpouse(User spouse);
    List<User> findByActiveIsTrueAndMotherOrFather(User mother, User father);
}
