package com.rightdata.task.repositories;

import com.rightdata.task.entities.Folder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FolderRepository extends JpaRepository<Folder, Long> {

    List<Folder> findByParentFolder(Folder folder);
}
