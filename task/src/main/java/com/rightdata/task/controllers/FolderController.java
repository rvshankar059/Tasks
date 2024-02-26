package com.rightdata.task.controllers;

import com.rightdata.task.entities.Folder;
import com.rightdata.task.services.FolderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/folders")
public class FolderController {
    @Autowired
    private FolderService folderService;

    @PostMapping
    public ResponseEntity<Folder> createFolder(@RequestParam String name) {
        Folder createdFolder = folderService.createFolder(name);
        return ResponseEntity.ok(createdFolder);
    }

    @PostMapping("/{parentId}/subfolders")
    public ResponseEntity<Folder> createSubfolder(@PathVariable Long parentId, @RequestParam String name) {
        Folder createdSubfolder = folderService.createSubfolder(parentId, name);
        return ResponseEntity.ok(createdSubfolder);
    }

    @DeleteMapping("/{folderId}")
    public ResponseEntity<Void> deleteFolder(@PathVariable Long folderId) {
        folderService.deleteFolder(folderId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{folderId}/explorer")
    public ResponseEntity<Folder> getExplorerStructure(@PathVariable Long folderId) {
        Folder explorerStructure = folderService.getExplorerStructure(folderId);
        return ResponseEntity.ok(explorerStructure);
    }
}

