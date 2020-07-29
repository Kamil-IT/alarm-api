package com.clock.clockapi.repository.v1;


import com.clock.clockapi.api.v1.model.alarm.Alarm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AlarmRepository extends JpaRepository<Alarm, String> {

    List<Alarm> findAllByUserApp_Id(String userId);

    Optional<Alarm> findFirstByIdAndUserApp_Id(String id, String userApp_id);
}
