package com.modeler.repositories;
import org.springframework.data.jpa.repository.JpaRepository;

import com.modeler.model.Version;
public interface VersionRepository extends JpaRepository<Version,Integer>{

}
