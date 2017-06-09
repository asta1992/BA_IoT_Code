package ch.hsr.smartmanager.data.repositories;

import org.springframework.stereotype.Repository;

@Repository
public interface ManagementUserRepositoryCustom {
	void removeByUsername(String username);


}
