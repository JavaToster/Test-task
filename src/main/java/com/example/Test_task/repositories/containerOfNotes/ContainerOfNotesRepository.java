package com.example.Test_task.repositories.containerOfNotes;

import com.example.Test_task.models.container.ContainerOfNotes;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ContainerOfNotesRepository extends JpaRepository<ContainerOfNotes, Long> {
    @Query(value = "SELECT id FROM Container_of_notes ORDER BY id DESC LIMIT 1", nativeQuery = true)
    Optional<ContainerOfNotes> findLast();
}
