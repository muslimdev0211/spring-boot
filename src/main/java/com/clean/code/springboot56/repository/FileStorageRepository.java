package com.clean.code.springboot56.repository;

import com.clean.code.springboot56.domain.Employee;
import com.clean.code.springboot56.domain.FileStorage;
import com.clean.code.springboot56.domain.FileStorageStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FileStorageRepository extends JpaRepository<FileStorage, Long> {


FileStorage findByHashId(String hashId);

List<FileStorage> findByFileStorageStatus(FileStorageStatus status);
}
