package com.example.Test_task.repositories.containerOfNotes;

import com.example.Test_task.models.container.ContainerOfNotes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContainerOfNotesRepository extends JpaRepository<ContainerOfNotes, Long> {
}
