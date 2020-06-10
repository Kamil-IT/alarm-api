package com.clock.clockapi.repository.v1;

import com.clock.clockapi.api.v1.model.stopwatch.Stopwatch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StopwatchRepository extends JpaRepository<Stopwatch, String> {
}
