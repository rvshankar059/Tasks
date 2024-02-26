package com.rightdata.task.services;

import com.rightdata.task.entities.Folder;
import com.rightdata.task.repositories.FolderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FolderService {
    @Autowired
    private FolderRepository folderRepository;

    public Folder createFolder(String name) {
        Folder folder = new Folder();
        folder.setName(name);
        return folderRepository.save(folder);
    }

    public void deleteFolder(Long folderId) {
        folderRepository.deleteById(folderId);

        // recursive logic required to delete all the underlying/ connected folders, in case of subfolders deletion
        // create another service method for it instead.
    }

    public Folder createSubfolder(Long parentId, String name) {
        Folder parentFolder = folderRepository.findById(parentId)
                .orElseThrow(()-> new RuntimeException("Parent folder not found"));

        Folder subfolder = new Folder();
        subfolder.setName(name);
        subfolder.setParent(parentFolder);
        return folderRepository.save(subfolder);
    }

    public Folder getExplorerStructure(Long folderId) {
        Folder rootFolder = folderRepository.findById(folderId)
                .orElseThrow(() -> new RuntimeException("Folder not found"));

        return buildExplorer(rootFolder);
    }

    private Folder buildExplorer(Folder folder) {
        if (Optional.ofNullable(folder).isPresent()){
            List<Folder> subfolders = folderRepository.findByParentFolder(folder);

            for (Folder subfolder : subfolders) {
                Folder explorer = buildExplorer(subfolder);
                folder.getSubfolders().add(explorer);
            }
        }

        return folder;
    }
}

