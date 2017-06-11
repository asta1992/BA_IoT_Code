package ch.hsr.smartmanager.data.repositories;

import java.util.List;

import org.springframework.stereotype.Repository;

@Repository
public interface DeviceGroupRepositoryCustom {
	

	List<String> findAllAncestors(String name);
	List<String> findAllChildren(String name);

}
