package ch.hsr.smartmanager.data.repository;

import org.springframework.stereotype.Repository;

@Repository
public interface ManagementUserRepositoryCustom {
	void removeByUsername(String username);


}
