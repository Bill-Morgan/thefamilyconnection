package org.TheFamilyConnection.models.data;

import org.TheFamilyConnection.models.EmailAddress;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface EmailAddressDao extends CrudRepository<EmailAddress, Integer> {
}
