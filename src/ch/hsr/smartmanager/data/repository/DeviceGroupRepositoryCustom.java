package ch.hsr.smartmanager.data.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

@Repository
public interface DeviceGroupRepositoryCustom {
	

	List<String> findAllAncestors(String name);

}
